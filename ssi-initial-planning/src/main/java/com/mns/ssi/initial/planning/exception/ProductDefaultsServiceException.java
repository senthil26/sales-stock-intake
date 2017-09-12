package com.mns.ssi.initial.planning.exception;

public class ProductDefaultsServiceException extends RuntimeException {
    public ProductDefaultsServiceException(String message) {
        super(message);
    }

    public ProductDefaultsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
