package com.mns.ssi.initial.planning.controller.dto;

public class ProductsServiceError {
    private String message;

    public ProductsServiceError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
