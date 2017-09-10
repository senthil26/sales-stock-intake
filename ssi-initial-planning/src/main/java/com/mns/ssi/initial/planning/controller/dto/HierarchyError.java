package com.mns.ssi.initial.planning.controller.dto;

public class HierarchyError {
    private String message;

    public HierarchyError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
