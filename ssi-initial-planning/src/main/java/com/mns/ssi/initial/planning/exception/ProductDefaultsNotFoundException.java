package com.mns.ssi.initial.planning.exception;

public class ProductDefaultsNotFoundException extends RuntimeException {
    public ProductDefaultsNotFoundException(String message) {
        super(message);
    }

    public ProductDefaultsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
