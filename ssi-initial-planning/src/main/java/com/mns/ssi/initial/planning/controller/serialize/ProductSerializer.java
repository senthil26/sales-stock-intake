package com.mns.ssi.initial.planning.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mns.ssi.initial.planning.model.Product;
import com.mns.ssi.initial.planning.model.ProductDetail;

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
        jsonGenerator.writeObjectField("lineDescription", product.getLineDescription());
        jsonGenerator.writeObjectField("hierarchyId", product.getHierarchyId());
        jsonGenerator.writeEndObject();
    }
}
