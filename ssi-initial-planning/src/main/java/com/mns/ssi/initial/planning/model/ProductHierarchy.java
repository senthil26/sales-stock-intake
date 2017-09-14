package com.mns.ssi.initial.planning.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mns.ssi.initial.planning.controller.serialize.ProductHierarchySerializer;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@JsonSerialize(using = ProductHierarchySerializer.class)
public class ProductHierarchy {
    private Set<HierarchyNode> nodes;

    private ProductHierarchy(Builder builder) {
        this.nodes = builder.hierarchyNodes;
    }

    public static class Builder {
        private Set<HierarchyNode> hierarchyNodes;

        Builder() {
            hierarchyNodes = new HashSet<>();
        }

        public Builder nodes(Set<HierarchyNode> hierarchyNodes) {
            this.hierarchyNodes.addAll(hierarchyNodes);
            return this;
        }

        public ProductHierarchy build() {
            return new ProductHierarchy(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    ProductHierarchy() {
        nodes = new HashSet<>();
    }

    public static Set<HierarchyNode> find(Set<HierarchyNode> hierarchyNodes, Level level) {
        if(hierarchyNodes.isEmpty()) {
            return hierarchyNodes;
        }

        if(hierarchyNodes.stream().anyMatch(hierarchyNode -> hierarchyNode.getLevelId() == level)) {
            return hierarchyNodes;
        }

        Set<HierarchyNode> hNodes = new HashSet<>();
        for(HierarchyNode hierarchyNode : hierarchyNodes) {
            hNodes.addAll(find(hierarchyNode.getChildren(), level));
        }

        return hNodes;
    }




    public Set<HierarchyNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ProductHierarchy)) {
            return false;
        }

        ProductHierarchy other = (ProductHierarchy) o;
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
