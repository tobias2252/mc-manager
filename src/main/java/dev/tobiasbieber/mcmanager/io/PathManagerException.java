package dev.tobiasbieber.mcmanager.io;

public class PathManagerException extends RuntimeException {

    public PathManagerException(String message) {
        super(message);
    }

    public PathManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
