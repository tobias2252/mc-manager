package dev.tobiasbieber.mcmanager.io.entity.impl;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.io.entity.EntityStorage;
import dev.tobiasbieber.mcmanager.io.entity.EntityStorageException;
import javafx.scene.image.Image;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.tag.CompoundTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

public class WorldStorage extends EntityStorage<World> {

    private static final Logger log = LoggerFactory.getLogger(WorldStorage.class);

    @Override
    public Path getStorageDir() {
        return AppContext.getInstance().getPathManager().findWorldDir();
    }

    @Override
    protected boolean isEntity(Path path) {
        if (!Files.isDirectory(path)) {
            return false;
        }
        return Files.exists(path.resolve("level.dat")) ||
                Files.exists(path.resolve("level.dat_old"));
    }

    @Override
    protected World loadEntity(Path path) {
        World world = new World(path);
        loadIcon(world);
        loadLevelData(world);
        return world;
    }

    private void loadIcon(World world) {
        Path iconPath = world.getPath().resolve("icon.png");
        if (Files.exists(iconPath)) {
            try (FileInputStream stream = new FileInputStream(iconPath.toFile())) {
                world.setIcon(new Image(stream));
                return;
            } catch (IOException e) {
                // ignore
            }
        }
        log.warn("No icon found for world: {}", world.getPath());

        // use default icon if none was found
        String defaultIcon = "/img/default_icon.png";
        world.setIcon(new Image(Objects.requireNonNull(getClass().getResourceAsStream(defaultIcon))));
    }

    private void loadLevelData(World world) {
        Path worldPath = world.getPath();
        Path levelPath = worldPath.resolve("level.dat");

        // check for level.dat or level.dat_old
        if (!Files.exists(levelPath)) {
            levelPath = worldPath.resolve("level.dat_old");
        }
        if (!Files.exists(levelPath)) {
            throw new EntityStorageException("Failed to locate level data file in: " + worldPath);
        }

        // load level data
        try {
            CompoundTag rootTag = (CompoundTag) NBTUtil.read(levelPath.toFile()).getTag();
            CompoundTag dataTag = rootTag.getCompoundTag("Data");

            world.setName(dataTag.getString("LevelName"));
            world.setVersion(dataTag.getCompoundTag("Version").getString("Name"));
            world.setLastPlayed(Instant.ofEpochMilli(dataTag.getLong("LastPlayed")));
            world.setAllowCommands(dataTag.getByte("allowCommands") != 0);

            // set game type
            switch (dataTag.getInt("GameType")) {
                case 0 -> world.setGameType(World.GameType.SURVIVAL);
                case 1 -> world.setGameType(World.GameType.CREATIVE);
                case 2 -> world.setGameType(World.GameType.ADVENTURE);
                case 3 -> world.setGameType(World.GameType.SPECTATOR);
            }

            // set difficulty
            if (dataTag.containsKey("difficulty_settings")) {
                // after version 26.x
                CompoundTag settings = dataTag.getCompoundTag("difficulty_settings");
                switch (settings.getString("difficulty")) {
                    case "peaceful" -> world.setDifficulty(World.Difficulty.PEACEFUL);
                    case "easy" -> world.setDifficulty(World.Difficulty.EASY);
                    case "normal" -> world.setDifficulty(World.Difficulty.NORMAL);
                    case "hard" -> world.setDifficulty(World.Difficulty.HARD);
                }
                world.setHardcore(settings.getByte("hardcore") != 0);
            } else {
                // old format
                switch (dataTag.getByte("Difficulty")) {
                    case 0 -> world.setDifficulty(World.Difficulty.PEACEFUL);
                    case 1 -> world.setDifficulty(World.Difficulty.EASY);
                    case 2 -> world.setDifficulty(World.Difficulty.NORMAL);
                    case 3 -> world.setDifficulty(World.Difficulty.HARD);
                }
                world.setHardcore(dataTag.getByte("hardcore") != 0);
            }

            // set seed
            if (dataTag.containsKey("WorldGenSettings")) {
                // new format
                world.setSeed(dataTag.getCompoundTag("WorldGenSettings").getLong("seed"));
            } else if (dataTag.containsKey("RandomSeed")) {
                // old format
                world.setSeed(dataTag.getLong("RandomSeed"));
            } else {
                // after version 26.x (new location)
                Path wgsPath = worldPath.resolve("data/minecraft/world_gen_settings.dat");

                // load seed from world_gen_settings.dat
                if (Files.exists(wgsPath)) {
                    CompoundTag wgsRootTag = (CompoundTag) NBTUtil.read(wgsPath.toFile()).getTag();
                    CompoundTag wgsDataTag = wgsRootTag.getCompoundTag("data");
                    world.setSeed(wgsDataTag.getLong("seed"));
                }
            }
        } catch (IOException e) {
            throw new EntityStorageException("Failed to load level data from: " + levelPath, e);
        }
    }
}
