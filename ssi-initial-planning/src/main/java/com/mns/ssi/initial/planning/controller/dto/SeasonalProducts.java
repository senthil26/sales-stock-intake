package com.mns.ssi.initial.planning.controller.dto;


import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class SeasonalProducts {
    private List<String> hierarchyIds;

    private List<String> superSeasons;

    private int page;

    private int size;

    public SeasonalProducts() {
        hierarchyIds = new ArrayList<>();
        superSeasons = new ArrayList<>();
    }

    public List<String> getHierarchyIds() {
        return hierarchyIds;
    }

    public void setHierarchyIds(List<String> hierarchyIds) {
        this.hierarchyIds = hierarchyIds;
    }

    public List<String> getSuperSeasons() {
        return superSeasons;
    }

    public void setSuperSeasons(List<String> superSeasons) {
        this.superSeasons = superSeasons;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
