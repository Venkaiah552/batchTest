package com.cvs.cdc.exception;


public class ThirdPartyRestServiceException extends RuntimeException {

    private static final long serialVersionUID = -3741093390661064689L;

    public ThirdPartyRestServiceException(String message) {
        super(message);
    }

    public ThirdPartyRestServiceException(String message, Exception e) {
        super(message, e);
    }

}