package com.mns.ssi.initial.planning.model;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public final class Criteria {
    private final Map<Criteria.Attribute, Set<String>> filters;
    private final int pageSize;
    private final int pageIndex;

    private Criteria(Builder builder) {
        this.filters = builder.filters;
        this.pageSize = builder.pageSize;
        this.pageIndex = builder.pageIndex;
    }

    public static class Builder {
        private Map<Criteria.Attribute, Set<String>> filters;
        private int pageSize;
        private int pageIndex;

        public Builder() {
            this.filters = new HashMap<>();
        }

        public Builder filter(Criteria.Attribute name, String value) {
            if(value == null) {
                return this;
            }

            filters.computeIfAbsent(name, n -> new HashSet<>());
            filters.get(name).add(value);
            return this;
        }

        public Builder filter(Criteria.Attribute name, List<String> values) {
            filters.computeIfAbsent(name, n -> new HashSet<>());
            filters.get(name).addAll(values);
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder pageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
            return this;
        }

        public Criteria build() {
            return new Criteria(this);
        }
    }

    public enum Attribute {
        ARTICLE_NUMBER("articleNumber"),
        LONG_DESCRIPTION("longDescription"),
        LINE_DESCRIPTION("lineDescription"),
        STROKE_NUMBER("strokeNumber"),
        SUPER_SEASON("superSeason"),
        HIERARCHY_ID("hierarchyId");

        private static final Map<String, Attribute> attributes = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(attribute -> attributes.put(attribute.name, attribute));
        }

        private String name;

        Attribute(String name) {
            this.name = name;
        }

        @JsonValue
        @Override
        public String toString() {
            return name;
        }

        public static Attribute from(String name) {
            return attributes.get(name);
        }

    }


    public static Builder builder() {
        return new Builder();
    }

    public List<String> getFilter(Criteria.Attribute attribute) {
        return filters.getOrDefault(attribute, Collections.emptySet()).stream()
                .collect(Collectors.toList());
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Criteria)) {
            return false;
        }

        Criteria other = (Criteria) o;
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
