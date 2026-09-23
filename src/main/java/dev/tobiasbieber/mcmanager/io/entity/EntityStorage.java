package dev.tobiasbieber.mcmanager.io.entity;

import dev.tobiasbieber.mcmanager.domain.entity.BaseEntity;
import org.apache.commons.io.file.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// is responsible for file system operations related to entities
public abstract class EntityStorage<T extends BaseEntity> {

    // should return the working directory for this storage
    public abstract Path getStorageDir();

    // should check if a path is a valid entity
    protected abstract boolean isEntity(Path path);

    // should load and convert a single entity
    protected abstract T loadEntity(Path path);

    // loads and returns a list of entities
    public List<T> load() {
        List<T> entities = new ArrayList<>();
        try (Stream<Path> stream = Files.list(getStorageDir())) {
            stream.forEach(path -> {
                if (isEntity(path)) {
                    entities.add(loadEntity(path));
                }
            });
        } catch (IOException e) {
            throw new EntityStorageException("Failed to load entities from: " + getStorageDir(), e);
        }
        return entities;
    }

    // deletes the given entity
    public void delete(T entity) {
        Path path = entity.getPath();
        if (!isEntity(path)) {
            throw new EntityStorageException("Invalid entity path: " + path);
        }

        try {
            if (Files.isDirectory(path)) {
                PathUtils.deleteDirectory(path);
            } else {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new EntityStorageException("Failed to delete entity: " + path, e);
        }
    }
}
