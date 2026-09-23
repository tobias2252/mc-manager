package dev.tobiasbieber.mcmanager.service.entity;

import dev.tobiasbieber.mcmanager.domain.entity.impl.World;
import dev.tobiasbieber.mcmanager.io.entity.impl.WorldStorage;
import dev.tobiasbieber.mcmanager.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WorldService implements Service {

    private static final Logger log = LoggerFactory.getLogger(WorldService.class);

    private final WorldStorage worldStorage;

    private List<World> worlds;

    public WorldService() {
        worldStorage = new WorldStorage();
        worlds = new ArrayList<>();
    }

    // loads all worlds from the installation directory
    public void loadWorlds() {
        log.info("Loading worlds...");
        worlds = worldStorage.load();
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public void deleteWorld(World world) {
        log.info("Deleting world: {}", world.getPath());
        worldStorage.delete(world);
        worlds.remove(world);
    }
}
