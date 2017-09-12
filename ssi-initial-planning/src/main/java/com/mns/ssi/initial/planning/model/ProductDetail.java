package com.mns.ssi.initial.planning.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mns.ssi.initial.planning.controller.serialize.ProductSerializer;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@JsonSerialize(using = ProductSerializer.class)
public final class ProductDetail {
    private String strokeNumber;
    private String superSeason;
    private String longDescription;
    private String articleNumber;
    private String hierarchyId;
    private String brand;
    private String colour;
    private String sellingPriceStructure;

    private ProductDetail(Builder builder) {
        this.strokeNumber = builder.strokeNumber;
        this.superSeason = builder.superSeason;
        this.longDescription = builder.longDescription;
        this.articleNumber = builder.articleNumber;
        this.hierarchyId = builder.hierarchyId;
        this.brand = builder.brand;
        this.colour = builder.colour;
        this.sellingPriceStructure = builder.sellingPriceStructure;
    }

    ProductDetail() {}

    public static class Builder {
        private String strokeNumber;
        private String superSeason;
        private String longDescription;
        private String articleNumber;
        private String hierarchyId;
        private String brand;
        private String colour;
        private String sellingPriceStructure;

        public Builder strokeNumber(String strokeNumber) {
            this.strokeNumber = strokeNumber;
            return this;
        }

        public Builder superSeason(String superSeason) {
            this.superSeason = superSeason;
            return this;
        }

        public Builder longDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        public Builder articleNumber(String articleNumber) {
            this.articleNumber = articleNumber;
            return this;
        }

        public Builder hierarchyId(String hierarchyId) {
            this.hierarchyId = hierarchyId;
            return this;
        }

        public Builder colour(String colour) {
            this.colour = colour;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder sellingPriceStructure(String sellingPriceStructure) {
            this.sellingPriceStructure = sellingPriceStructure;
            return this;
        }

        public ProductDetail build() {
            return new ProductDetail(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getStrokeNumber() {
        return strokeNumber;
    }

    public String getSuperSeason() {
        return superSeason;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public String getBrand() {
        return brand;
    }

    public String getColour() {
        return colour;
    }

    public String getSellingPriceStructure() {
        return sellingPriceStructure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ProductDetail)) {
            return false;
        }

        ProductDetail other = (ProductDetail) o;
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
