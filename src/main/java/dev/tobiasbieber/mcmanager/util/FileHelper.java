package dev.tobiasbieber.mcmanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

    private static final Logger log = LoggerFactory.getLogger(FileHelper.class);

    public static void openFolder(Path path) {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path is not a directory: " + path);
        }

        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop operations are not supported");
        }

        try {
            log.info("Opening folder: {}", path);
            Desktop.getDesktop().open(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to open folder: " + path, e);
        }
    }
}
