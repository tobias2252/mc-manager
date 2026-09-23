package dev.tobiasbieber.mcmanager.io.entity;

public class EntityStorageException extends RuntimeException {

    public EntityStorageException(String message) {
        super(message);
    }

    public EntityStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
