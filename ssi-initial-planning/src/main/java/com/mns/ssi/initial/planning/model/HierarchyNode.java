package com.mns.ssi.initial.planning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class HierarchyNode implements Comparable<HierarchyNode>, Serializable {
    private String id;
    private String value;
    private Level levelId;
    private Set<HierarchyNode> children;

    @JsonIgnore
    private HierarchyNode parent;

    private HierarchyNode(Builder builder) {
        this.id = builder.id;
        this.value = builder.value;
        this.levelId = builder.levelId;
        this.children = builder.children;
        this.parent = builder.parent;
    }

    HierarchyNode() {}

    public static class Builder {
        private String id;
        private String value;
        private Level levelId;
        private HierarchyNode parent;
        private Set<HierarchyNode> children;

        Builder() {
            children = new HashSet<>();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder levelId(Level levelId) {
            this.levelId = levelId;
            return this;
        }

        public Builder parent(HierarchyNode parent) {
            this.parent = parent;
            return this;
        }

        public Builder child(HierarchyNode child) {
            if(child != null) {
                this.children.add(child);
            }

            return this;
        }

        public HierarchyNode build() {
            return new HierarchyNode(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }
    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Level getLevelId() {
        return levelId;
    }

    public Set<HierarchyNode> getChildren() {
        return new HashSet<>(children);
    }

    public HierarchyNode getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof HierarchyNode)) {
            return false;
        }

        HierarchyNode other = (HierarchyNode) o;
        return new EqualsBuilder()
                .append(other.getId(), this.getId())
                .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getId())
                .build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


    @Override
    public int compareTo(HierarchyNode other) {
        return ComparisonChain.start()
                .compare(this.levelId, other.levelId, Ordering.natural().nullsLast())
                .compare(this.id, other.id)
                .result();
    }
}
