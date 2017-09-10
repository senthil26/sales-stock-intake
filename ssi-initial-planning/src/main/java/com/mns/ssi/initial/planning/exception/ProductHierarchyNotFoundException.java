package com.mns.ssi.initial.planning.exception;

public class ProductHierarchyNotFoundException extends RuntimeException {
    public ProductHierarchyNotFoundException(String message) {
        super(message);
    }

    public ProductHierarchyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
