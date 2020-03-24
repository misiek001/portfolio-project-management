package com.mbor.exception;

public class NoSetProjectManagerException extends RuntimeException {

    public NoSetProjectManagerException() {
        super();
    }

    public NoSetProjectManagerException(String message) {
        super(message);
    }

    public NoSetProjectManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
