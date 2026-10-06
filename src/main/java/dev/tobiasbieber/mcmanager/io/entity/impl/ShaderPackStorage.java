package dev.tobiasbieber.mcmanager.io.entity.impl;

import dev.tobiasbieber.mcmanager.core.AppContext;
import dev.tobiasbieber.mcmanager.domain.entity.impl.ShaderPack;
import dev.tobiasbieber.mcmanager.io.entity.EntityStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShaderPackStorage extends EntityStorage<ShaderPack> {

    private static final Logger log = LoggerFactory.getLogger(ShaderPackStorage.class);

    @Override
    public Path getStorageDir() {
        return AppContext.getInstance().getPathManager().findShaderPackDir();
    }

    @Override
    protected boolean isEntity(Path path) {
        if (Files.isDirectory(path)) {
            return Files.isDirectory(path.resolve("shaders"));
        }

        // check if it is a zip file
        if (!(Files.isRegularFile(path)
                && path.getFileName().toString().toLowerCase().endsWith(".zip"))) {
            return false;
        }

        // check if it contains a shaders folder
        try (FileSystem zip = FileSystems.newFileSystem(path)) {
            return Files.isDirectory(zip.getPath("/shaders"));
        } catch (IOException e) {
            log.warn("Failed to inspect shader pack: {}", path, e);
            return false;
        }
    }

    @Override
    protected ShaderPack loadEntity(Path path) {
        ShaderPack pack = new ShaderPack(path);

        // set name and cut the .zip extension
        String name = path.getFileName().toString();
        if (name.toLowerCase().endsWith(".zip")) {
            pack.setName(name.substring(0, name.length() - 4));
        }
        return pack;
    }
}
