package com.mns.ssi.initial.planning.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

@Entity
@Table(name = "DefaultParameter", schema = "dbo")
public final class DefaultParameter  {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;

    @Column(name = "HierarchyId")
    private String hierarchyId;

    @Column(name = "DcCover")
    private Integer dcCover;

    @Column(name = "BreakingStock")
    private Integer breakingStock;

    @Column(name = "Eire")
    private Integer eire;

    @Column(name = "Level")
    private String level;

    DefaultParameter() {}

    private DefaultParameter(Builder builder) {
        this.hierarchyId = builder.hierarchyId;
        this.dcCover = builder.dcCover;
        this.breakingStock = builder.breakingStock;
        this.eire = builder.eire;
        this.level = builder.level;
    }

    public static class Builder {
        private String hierarchyId;
        private Integer dcCover;
        private Integer breakingStock;
        private Integer eire;
        private String level;

        public Builder hierarchyId(String hierarchyId) {
            this.hierarchyId = hierarchyId;
            return this;
        }

        public Builder dcCover(Integer dcCover) {
            this.dcCover = dcCover;
            return this;
        }

        public Builder breakingStock(Integer breakingStock) {
            this.breakingStock = breakingStock;
            return this;
        }

        public Builder eire(Integer eire) {
            this.eire = eire;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public DefaultParameter build() {
            return new DefaultParameter(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public Integer getDcCover() {
        return dcCover;
    }

    public Integer getBreakingStock() {
        return breakingStock;
    }

    public Integer getEire() {
        return eire;
    }

    public String getLevel() {
        return level;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultParameter)) {
            return false;
        }

        DefaultParameter flatProduct = (DefaultParameter) o;
        return reflectionEquals(this, flatProduct);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
