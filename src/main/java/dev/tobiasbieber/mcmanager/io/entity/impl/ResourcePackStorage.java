package dev.tobiasbieber.mcmanager.io.entity.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ResourcePack;
import dev.tobiasbieber.mcmanager.io.entity.EntityStorage;
import dev.tobiasbieber.mcmanager.io.entity.EntityStorageException;
import dev.tobiasbieber.mcmanager.util.Formatter;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ResourcePackStorage extends EntityStorage<ResourcePack> {

    private static final Logger log = LoggerFactory.getLogger(ResourcePackStorage.class);

    @Override
    public Path getStorageDir() {
        return AppContext.getInstance().getPathManager().findResourcePackDir();
    }

    @Override
    protected boolean isEntity(Path path) {
        if (Files.isDirectory(path)) {
            return Files.isRegularFile(path.resolve("pack.mcmeta"));
        }

        if (!Files.isRegularFile(path) ||
                !path.getFileName().toString().toLowerCase().endsWith(".zip")) {
            return false;
        }

        try (FileSystem zip = FileSystems.newFileSystem(path)) {
            return Files.isRegularFile(zip.getPath("/pack.mcmeta"));
        } catch (IOException e) {
            log.warn("Failed to locate pack.mcmeta in: {}", path);
            return false;
        }
    }

    @Override
    protected ResourcePack loadEntity(Path path) {
        ResourcePack pack = new ResourcePack(path);

        // set name and cut the .zip if necessary
        String name = path.getFileName().toString();
        if (name.toLowerCase().endsWith(".zip")) {
            name = name.substring(0, name.length() - 4);
        }
        pack.setName(name);

        // check if directory or .zip
        if (Files.isDirectory(path)) {
            loadPack(pack, path);
        } else {
            loadZipPack(pack);
        }
        return pack;
    }

    private void loadIcon(ResourcePack pack, Path path) {
        try (InputStream stream = Files.newInputStream(path)) {
            pack.setIcon(new Image(stream));
            return;
        } catch (IOException e) {
            // ignore
        }
        log.warn("Failed to load resource pack icon: {}", path);

        // use default icon
        String defaultIcon = "/img/default_icon.png";
        pack.setIcon(new Image(Objects.requireNonNull(getClass().getResourceAsStream(defaultIcon))));
    }

    private void loadDescription(ResourcePack pack, Path path) {
        try (Reader reader = Files.newBufferedReader(path)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject packObject = root.getAsJsonObject("pack");

            if (packObject == null) {
                pack.setDescription("-");
                return;
            }

            JsonElement description = packObject.get("description");
            if (description == null) {
                pack.setDescription("-");
            } else if (description.isJsonPrimitive()) {
                pack.setDescription(Formatter.stripMinecraftFormatting(description.getAsString()));
            } else {
                pack.setDescription(Formatter.stripMinecraftFormatting(description.toString()));
            }
        } catch (IOException e) {
            throw new EntityStorageException("Failed to load metadata: " + path, e);
        }
    }

    private void loadPack(ResourcePack pack, Path root) {
        loadDescription(pack, root.resolve("pack.mcmeta"));
        loadIcon(pack, root.resolve("pack.png"));
    }

    private void loadZipPack(ResourcePack pack) {
        try (FileSystem zip = FileSystems.newFileSystem(pack.getPath())) {
            loadPack(pack, zip.getPath("/"));
        } catch (IOException e) {
            throw new EntityStorageException("Failed to load resource pack: " + pack.getPath(), e);
        }
    }
}
