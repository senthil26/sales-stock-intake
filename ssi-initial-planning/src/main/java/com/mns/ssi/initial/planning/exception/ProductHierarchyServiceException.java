package com.mns.ssi.initial.planning.exception;

public class ProductHierarchyServiceException extends RuntimeException {
    public ProductHierarchyServiceException(Throwable cause) {
        super(cause);
    }

    public ProductHierarchyServiceException(String message) {
        super(message);
    }

    public ProductHierarchyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
