package com.mbor.exception;

public class WrongEmployeeTypeException extends RuntimeException {

    public WrongEmployeeTypeException() {
        super();
    }

    public WrongEmployeeTypeException(String message) {
        super(message);
    }

    public WrongEmployeeTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
