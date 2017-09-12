package com.mns.ssi.initial.planning.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mns.ssi.initial.planning.model.HierarchyNode;
import com.mns.ssi.initial.planning.model.Level;
import com.mns.ssi.initial.planning.model.ProductHierarchy;

import java.io.IOException;
import java.util.Set;

public class ProductHierarchySerializer extends StdSerializer<ProductHierarchy> {
    public ProductHierarchySerializer() {
        this(null);
    }

    public ProductHierarchySerializer(Class<ProductHierarchy> t) {
        super(t);
    }

    @Override
    public void serialize(ProductHierarchy hierarchy,
                          JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        Set<HierarchyNode> nodes = hierarchy.getNodes();
        if(nodes.stream().anyMatch(n -> n.getLevelId() == Level.GM)) {
            jsonGenerator.writeObjectField("node", nodes.stream().findFirst().get());
        } else {
            jsonGenerator.writeObjectField("nodes", nodes);
        }

        jsonGenerator.writeEndObject();
    }
}
