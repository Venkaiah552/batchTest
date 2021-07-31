package com.cvs.cdc.exception;

public class CdcRuntimeException extends RuntimeException {

    public CdcRuntimeException(String message)
    {
        super(message);
    }
    public CdcRuntimeException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
