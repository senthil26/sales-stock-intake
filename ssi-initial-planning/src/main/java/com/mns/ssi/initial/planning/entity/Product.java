package com.mns.ssi.initial.planning.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mns.ssi.initial.planning.controller.serialize.ProductSerializer;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@JsonSerialize(using = ProductSerializer.class)
public final class Product {
    private static final String EMPTY = "";

    private final Map<Attribute, String> attributes;
    private final Brand brand;
    private final Colour colour;
    private final SellingPriceStructure sellingPriceStructure;

    private enum SellingPriceStructure {GOOD, BETTER, BEST}

    private Product(Builder builder) {
        this.attributes = builder.details;
        this.brand = builder.brand;
        this.colour = builder.colour;
        this.sellingPriceStructure = builder.sellingPriceStructure;
    }

    public static class Builder {
        private Map<Attribute, String> details;
        private Brand brand;
        private Colour colour;
        private SellingPriceStructure sellingPriceStructure;

        public Builder() {
            this.details = new HashMap<>();
        }

        public Builder attribute(Attribute name, String value) {
            this.details.put(name, value);
            return this;
        }

        public Builder colour(String id) {
            this.colour = Colour.from(id);
            return this;
        }

        public Builder brand(String id) {
            this.brand = Brand.from(id);
            return this;
        }

        public Builder sellingPriceStructure(String sellingPriceStructure) {
            this.sellingPriceStructure = SellingPriceStructure.valueOf(sellingPriceStructure);
            return this;
        }

        public Product build() {
            return new Product(this);
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

    public enum Brand {
        INDIGO_COLLECTION("1");

        private static final Map<String, Brand> brands = new HashMap<>();
        static {
            Arrays.stream(values()).forEach(brand -> brands.put(brand.id, brand));
        }

        private String id;

        Brand(String id) {
            this.id = id;
        }

        @JsonValue
        @Override
        public String toString() {
            return name();
        }

        public static Brand from(String id) {
            return brands.get(id);
        }

    }

    public enum Colour {
        ROYAL_BLUE("RA"),
        SILVER("XX"),
        BRIGHT_RED("B5"),
        AIR_FORCE_BLUE("BE"),
        BERRY("F8"),
        BLACK("Y0"),
        BLACK_HIGH_SHINE("BC"),
        BLACK_MIX("Y4"),
        BLUE("E0"),
        BLUE_MARL("Z7"),
        BLUE_MIX("E4"),
        BLUE_WHITE("EZ"),
        BLUE_YELLOW("RJ"),
        BROWN("N0"),
        BROWN_MIX("N4"),
        BURGUNDY("XM"),
        BURGUNDY_MIX("U8"),
        CHAMBRAY("SA"),
        CHARCOAL_MIX("PK"),
        CHOCOLATE("SH"),
        COBALT("CB"),
        DARK_BROWN("N3"),
        DARK_GREY("T3"),
        DARK_TAN("V8"),
        DENIM_MIX("PD"),
        GRANITE("AE"),
        GREEN("J0"),
        GREEN_MIX("J4"),
        GREY("T0"),
        GREY_MARL("UT"),
        GREY_MIX("T4"),
        KHAKI("KH"),
        LIGHT_GREY("T1"),
        LIGHT_TAN("TK"),
        MAGENTA_MIX("X3"),
        MID_BLUE("O4"),
        MINT_MIX("KQ"),
        MULTI("ZZ"),
        MUSHROOM("MS"),
        NATURAL("V0"),
        NAVY("F0"),
        NAVY_MARL("CA"),
        NAVY_MIX("F4"),
        NAVY_RED("FB"),
        NEUTRAL("NU"),
        NO_COLOUR("NC"),
        ORANGE("P0"),
        PALE_BLUE_MIX("IL"),
        PALE_YELLOW("QR"),
        PURPLE("D0"),
        PURPLE_MIX("D4"),
        RED("B0"),
        RED_MIX("B4"),
        STEEL_BLUE("KE"),
        STONE("SS"),
        TAN("VS"),
        TEAL("NT"),
        TEAL_MIX("XT"),
        TURQUOISE("H0"),
        WHITE("Z0"),
        WHITE_MIX("Z4"),
        WHITE_GREEN("YU"),
        WHITE_RED("ZM"),
        WINE("C0"),
        YELLOW("R0");

        private static final Map<String, Colour> colours = new HashMap<>();
        static {
            Arrays.stream(values()).forEach(colour -> colours.put(colour.id, colour));
        }

        private String id;

        Colour(String id) {
            this.id = id;
        }

        @JsonValue
        @Override
        public String toString() {
            return name();
        }

        public static Colour from(String id) {
            return colours.get(id);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<Attribute, String> getAttributes() {
        return new HashMap<>(attributes);
    }

    public void setAttribute(Attribute attribute, String value) {
        attributes.put(attribute, value);
    }

    public String getAttribute(Attribute attribute) {
        return attributes.getOrDefault(attribute, EMPTY);
    }

    public Brand getBrand() {
        return brand;
    }

    public Colour getColour() {
        return colour;
    }

    public SellingPriceStructure getSellingPriceStructure() {
        return sellingPriceStructure;
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
