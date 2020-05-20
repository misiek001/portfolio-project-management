package com.mbor.exception;

public class ProjectAlreadyAssignedAsSecondaryException extends RuntimeException {
    public ProjectAlreadyAssignedAsSecondaryException() {
        super();
    }

    public ProjectAlreadyAssignedAsSecondaryException(String message) {
        super(message);
    }

    public ProjectAlreadyAssignedAsSecondaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
