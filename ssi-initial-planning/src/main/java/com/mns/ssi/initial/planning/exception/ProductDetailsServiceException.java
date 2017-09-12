package com.mns.ssi.initial.planning.exception;

public class ProductDetailsServiceException extends RuntimeException {
    public ProductDetailsServiceException(String message) {
        super(message);
    }

    public ProductDetailsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
