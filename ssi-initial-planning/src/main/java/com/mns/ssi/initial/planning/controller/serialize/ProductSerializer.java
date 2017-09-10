package com.mns.ssi.initial.planning.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mns.ssi.initial.planning.entity.Product;

import java.io.IOException;
import java.util.Map;

public class ProductSerializer extends StdSerializer<Product> {
    public ProductSerializer() {
        this(null);
    }

    public ProductSerializer(Class<Product> t) {
        super(t);
    }

    @Override
    public void serialize(Product product,
                          JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        for(Map.Entry<Product.Attribute, String> entry : product.getAttributes().entrySet()) {
            jsonGenerator.writeStringField(entry.getKey().toString(), entry.getValue());
        }

        jsonGenerator.writeObjectField("colour", product.getColour());
        jsonGenerator.writeObjectField("brand", product.getBrand());
        jsonGenerator.writeObjectField("sellingPriceStructure", product.getSellingPriceStructure());

        jsonGenerator.writeEndObject();
    }
}
