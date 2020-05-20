package com.mbor.exception;

public class ProjectAlreadyAssignedAsPrimaryException extends RuntimeException {

    public ProjectAlreadyAssignedAsPrimaryException() {
    }

    public ProjectAlreadyAssignedAsPrimaryException(String message) {
        super(message);
    }

    public ProjectAlreadyAssignedAsPrimaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
