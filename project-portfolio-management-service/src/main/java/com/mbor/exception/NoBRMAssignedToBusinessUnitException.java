package com.mbor.exception;

public class NoBRMAssignedToBusinessUnitException extends RuntimeException {

    public NoBRMAssignedToBusinessUnitException() {
    }

    public NoBRMAssignedToBusinessUnitException(String message) {
        super(message);
    }

    public NoBRMAssignedToBusinessUnitException(String message, Throwable cause) {
        super(message, cause);
    }
}
