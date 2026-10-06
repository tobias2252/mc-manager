package dev.tobiasbieber.mcmanager.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// manager class for resolving and handling paths
public class PathManager {

    private static final Logger log = LoggerFactory.getLogger(PathManager.class);

    private Path minecraftDir;

    public PathManager() {
        // use default minecraft installation directory
        minecraftDir = Paths.get(System.getenv("appdata"), ".minecraft");
    }

    public Path findWorldDir() {
        Path worldDir = minecraftDir.resolve("saves");
        validateDir(minecraftDir, false);
        validateDir(worldDir, false);
        return worldDir;
    }

    public Path findResourcePackDir() {
        Path resourcePackDir = minecraftDir.resolve("resourcepacks");
        validateDir(minecraftDir, false);
        validateDir(resourcePackDir, false);
        return resourcePackDir;
    }

    public Path findShaderPackDir() {
        Path shaderPackDir = minecraftDir.resolve("shaderpacks");
        validateDir(minecraftDir, false);
        validateDir(shaderPackDir, false);
        return shaderPackDir;
    }

    // checks if the given directory exists and creates it if necessary
    private void validateDir(Path path, boolean create) {
        if (!Files.exists(path)) {
            if (create) {
                try {
                    log.info("Creating directory: {}", path);
                    Files.createDirectory(path);
                } catch (IOException e) {
                    throw new PathManagerException("Failed to create directory: " + path, e);
                }
            } else {
                throw new PathManagerException("Directory not found: " + path);
            }
        } else {
            if (!Files.isDirectory(path)) {
                throw new PathManagerException("Path is not a directory: " + path);
            }
        }
    }

    // checks if the given file exists and creates it if necessary
    private void validateFile(Path path, boolean create) {
        if (!Files.exists(path)) {
            if (create) {
                try {
                    log.info("Creating file: {}", path);
                    Files.createFile(path);
                } catch (IOException e) {
                    throw new PathManagerException("Failed to create file: " + path, e);
                }
            } else {
                throw new PathManagerException("File not found: " + path);
            }
        } else {
            if (!Files.isRegularFile(path)) {
                throw new PathManagerException("Path is not a file: " + path);
            }
        }
    }

    public Path getMinecraftDir() {
        return minecraftDir;
    }

    public void setMinecraftDir(Path minecraftDir) {
        this.minecraftDir = minecraftDir;
    }
}
