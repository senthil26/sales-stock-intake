package com.mns.ssi.initial.planning.model;

import java.util.List;

public class PageableHierarchyId {
    private List<String> hierarchyIds;
    private int page;
    private int size;

    public List<String> getHierarchyIds() {
        return hierarchyIds;
    }

    public void setHierarchyIds(List<String> hierarchyIds) {
        this.hierarchyIds = hierarchyIds;
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
}
