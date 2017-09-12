package com.mns.ssi.initial.planning.model;

import org.apache.commons.lang3.builder.ToStringStyle;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public final class DefaultsStrokeColour {
    private String id;
    private String dcCover;
    private String breakingStock;
    private String eire;

    private DefaultsStrokeColour(Builder builder) {
        this.id = builder.id;
        this.dcCover = builder.dcCover;
        this.breakingStock = builder.breakingStock;
        this.eire = builder.eire;
    }

    public static class Builder {
        private String id;
        private String dcCover;
        private String breakingStock;
        private String eire;

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

        public DefaultsStrokeColour build() {
            return new DefaultsStrokeColour(this);
        }
    }

    public static Builder builder() {
        return new Builder();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultsStrokeColour)) {
            return false;
        }

        DefaultsStrokeColour other = (DefaultsStrokeColour) o;
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
