package com.mns.ssi.initial.planning.controller.dto;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class ProductHierarchyIds {
    private static final String DEPARTMENT_LEVEL = "3";
    private List<String> hierarchyIds;
    private String levelId;

    public ProductHierarchyIds() {
        this.hierarchyIds = new ArrayList<>();
        this.levelId = DEPARTMENT_LEVEL;
    }

    public List<String> getHierarchyIds() {
        return hierarchyIds;
    }

    public void setHierarchyIds(List<String> hierarchyIds) {
        this.hierarchyIds = hierarchyIds;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
