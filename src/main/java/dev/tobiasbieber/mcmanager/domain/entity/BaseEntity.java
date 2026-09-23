package dev.tobiasbieber.mcmanager.domain.entity;

import java.nio.file.Path;

// base class for all minecraft-related entities
// all minecraft resources are referred to as entities (worlds, mods, etc.)
public abstract class BaseEntity {

    private Path path;

    public BaseEntity(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
