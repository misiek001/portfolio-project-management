package com.mbor.exception;

public class WrongProjectManagerException extends RuntimeException {

    public WrongProjectManagerException() {
        super();
    }

    public WrongProjectManagerException(String message) {
        super(message);
    }

    public WrongProjectManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
