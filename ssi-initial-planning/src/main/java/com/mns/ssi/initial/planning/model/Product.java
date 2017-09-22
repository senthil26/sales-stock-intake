package com.mns.ssi.initial.planning.model;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.StringTokenizer;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class Product {
    private static final String SLASH = "/";
    private static final String DASH = "-";
    private static final String EMPTY = "";

    private String lineDescription;
    private String articleNumber;
    private String hierarchyId;

    private Product(Builder builder) {
        this.hierarchyId = builder.hierarchyId;
        this.lineDescription = builder.lineDescription;
        this.articleNumber = builder.articleNumber;
    }

    public static class Builder {
        private String lineDescription;
        private String hierarchyId;
        private String articleNumber;

        public Builder lineDescription(String lineDescription) {
            this.lineDescription = lineDescription;
            return this;
        }

        public Builder lineDescription(String hierarchyId, String strokeNumber, String longDescription,
                                       String colour) {
            StringBuilder builder = new StringBuilder();
            builder.append(getDepartment(hierarchyId)).append(SLASH);
            builder.append(strokeNumber).append(SLASH);
            builder.append(longDescription).append(SLASH);
            builder.append(colour);

            this.lineDescription = builder.toString();
            return this;
        }

        public Builder hierarchyId(String hierarchyId) {
            this.hierarchyId = hierarchyId;
            return this;
        }

        public Builder articleNumber(String articleNumber) {
            this.articleNumber = articleNumber;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

     private String getDepartment(String hierarchyId) {
    	hierarchyId=hierarchyId.substring(hierarchyId.indexOf(DASH)+1);
    	hierarchyId=hierarchyId.substring(0,3);
	
      return EMPTY;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Product)) {
            return false;
        }

        Product other = (Product) o;
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
