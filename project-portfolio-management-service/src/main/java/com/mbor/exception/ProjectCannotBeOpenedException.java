package com.mbor.exception;

public class ProjectCannotBeOpenedException extends RuntimeException {
    public ProjectCannotBeOpenedException() {
    }

    public ProjectCannotBeOpenedException(String message) {
        super(message);
    }

    public ProjectCannotBeOpenedException(String message, Throwable cause) {
        super(message, cause);
    }
}
