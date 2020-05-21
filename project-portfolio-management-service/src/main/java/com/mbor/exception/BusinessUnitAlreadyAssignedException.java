package com.mbor.exception;

public class BusinessUnitAlreadyAssignedException extends RuntimeException {

    public BusinessUnitAlreadyAssignedException() {
    }

    public BusinessUnitAlreadyAssignedException(String message) {
        super(message);
    }

    public BusinessUnitAlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
