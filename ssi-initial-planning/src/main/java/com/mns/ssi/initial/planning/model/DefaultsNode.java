package com.mns.ssi.initial.planning.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mns.ssi.initial.planning.entity.DefaultParameter;
import com.mns.ssi.initial.planning.util.Converters;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultsNode implements Comparable<DefaultsNode> {
    private static final String DASH = "-";

    private String id;
    private String dcCover;
    private String breakingStock;
    private String eire;
    private String level;
    private String value;
    private Set<DefaultsNode> children;

    private DefaultsNode(Builder builder) {
        this.id = builder.id;
        this.dcCover = builder.dcCover;
        this.breakingStock = builder.breakingStock;
        this.eire = builder.eire;
        this.level = builder.level;
        this.children = builder.children;
        this.value = builder.value;
    }

    DefaultsNode() { }

    public static class Builder {
        private String id;
        private String dcCover;
        private String breakingStock;
        private String eire;
        private String level;
        private String value;
        private Set<DefaultsNode> children;

        Builder() {
            children = new HashSet<>();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder dcCover(String dcCover) {
            this.dcCover = dcCover;
            return this;
        }

        public Builder breakingStock(String breakingStock) {
            this.breakingStock = breakingStock;
            return this;
        }

        public Builder eire(String eire) {
            this.eire = eire;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }


        public DefaultsNode build() {
            return new DefaultsNode(this);
        }
    }

    public static DefaultsNode using(HierarchyNode hierarchyNode, Map<String, DefaultParameter> parametersById) {
        DefaultParameter defaultParameter = parametersById.get(hierarchyNode.getId());
        if(defaultParameter == null) {
            return DefaultsNode.builder()
                    .id(hierarchyNode.getId())
                    .value(hierarchyNode.getValue())
                    .dcCover(DASH)
                    .breakingStock(DASH)
                    .eire(DASH)
                    .level(hierarchyNode.getLevelId().name())
                    .build();
        }

        return DefaultsNode.builder()
                .id(hierarchyNode.getId())
                .value(hierarchyNode.getValue())
                .dcCover(Converters.toString(defaultParameter.getDcCover()))
                .breakingStock(Converters.toString(defaultParameter.getBreakingStock()))
                .eire(Converters.toString(defaultParameter.getEire()))
                .level(hierarchyNode.getLevelId().name())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }


    public void addChild(DefaultsNode child) {
        this.children.add(child);
    }

    public void addChildren(Set<DefaultsNode> children) {
        this.children.addAll(children);
    }

    public Set<DefaultsNode> getChildren() {
        return new HashSet<>(children);
    }

    public String getId() {
        return id;
    }

    public String getDcCover() {
        return dcCover;
    }

    public String getBreakingStock() {
        return breakingStock;
    }

    public String getEire() {
        return eire;
    }

    public String getLevel() {
        return level;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultsNode)) {
            return false;
        }

        DefaultsNode other = (DefaultsNode) o;
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
    public int compareTo(DefaultsNode other) {
        return ComparisonChain.start()
                .compare(this.level, other.level, Ordering.natural().nullsLast())
                .compare(this.id, other.id)
                .result();
    }

}
