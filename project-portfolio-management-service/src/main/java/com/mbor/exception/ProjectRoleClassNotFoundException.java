package com.mbor.exception;

public class ProjectRoleClassNotFoundException extends RuntimeException {
    public ProjectRoleClassNotFoundException() {
    }

    public ProjectRoleClassNotFoundException(String message) {
        super(message);
    }

    public ProjectRoleClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
