package com.mns.ssi.initial.planning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Node implements Comparable<Node> {
    private static final int DEEPEST_DEPTH = 6;

    private final String id;
    private final String value;
    private final Level levelId;
    private final Set<Node> children;

    @JsonIgnore
    private Node parent;

    private Node(Builder builder) {
        this.id = builder.id;
        this.value = builder.value;
        this.levelId = builder.levelId;
        this.children = builder.children;
        this.parent = builder.parent;
    }

    public static class Builder {
        private String id;
        private String value;
        private Level levelId;
        private Node parent;
        private Set<Node> children;

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

        public Builder parent(Node parent) {
            this.parent = parent;
            return this;
        }

        public Builder child(Node child) {
            if(child != null) {
                this.children.add(child);
            }

            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }

    public static Builder builder() {
        return new Builder();
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

    public Set<Node> getChildren() {
        return new HashSet<>(children);
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Node)) {
            return false;
        }

        Node other = (Node) o;
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
    public int compareTo(Node other) {
        return ComparisonChain.start()
                .compare(this.levelId, other.levelId, Ordering.natural().nullsLast())
                .compare(this.id, other.id)
                .result();
    }
}
