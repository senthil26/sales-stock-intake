package com.mns.ssi.initial.planning.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Level {
    GM("1"),
    BUSINESS_UNIT("2"),
    CATEGORY("3"),
    DEPARTMENT("4"),
    SUB_DEPARTMENT("5"),
    RANGE("6"),
    ITEM("7");

    private static final Map<String, Level> levels = new HashMap<>();
    static {
        Arrays.stream(values()).forEach(level -> levels.put(level.id, level));
    }

    private String id;

    Level(String id) {
        this.id = id;
    }

    public Level atDepth(int depth) {
        int currentLevel = this.ordinal();
        int depthLevel = currentLevel + depth;

        return (depthLevel > this.values().length) ? Level.ITEM : Level.from(String.valueOf(depthLevel));
    }

    public Level next() {
        int currentLevel = this.ordinal();
        int depthLevel = currentLevel + 1;

        return (depthLevel > this.values().length) ? Level.ITEM : Level.from(String.valueOf(depthLevel));
    }


    @JsonValue
    @Override
    public String toString() {
        return id;
    }

    public static Level from(String id) {
        return levels.get(id);
    }

}
