package com.mns.ssi.initial.planning.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public final class HierarchySeason {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private List<String> hierarchyIds;
    private List<String> superSeasons;
    private int page;
    private int size;

    private HierarchySeason(Builder builder) {
        this.hierarchyIds = new ArrayList(builder.hierarchyIds);
        this.superSeasons = new ArrayList(builder.superSeasons);
        this.page = builder.page;
        this.size = builder.size;
    }

    HierarchySeason() {
        hierarchyIds = new ArrayList<>();
        superSeasons = new ArrayList<>();
    }

    public static class Builder {
        private List<String> hierarchyIds;
        private List<String> superSeasons;
        private int page;
        private int size;

        Builder() {
            hierarchyIds = new ArrayList<>();
            superSeasons = new ArrayList<>();
        }

        public Builder hierarchyIds(List<String> hierarchyIds) {
            this.hierarchyIds.addAll(hierarchyIds);
            return this;
        }

        public Builder superSeasons(List<String> superSeasons) {
            this.superSeasons.addAll(superSeasons);
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public HierarchySeason build() {
            return new HierarchySeason(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getHierarchyIds() {
        return hierarchyIds;
    }

    public List<String> getSuperSeasons() {
        return superSeasons;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof HierarchySeason)) {
            return false;
        }

        HierarchySeason other = (HierarchySeason) o;
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

}
