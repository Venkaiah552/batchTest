package com.cvs.cdc.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String errorMessage) {

        super(errorMessage);

    }

    public BadRequestException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
