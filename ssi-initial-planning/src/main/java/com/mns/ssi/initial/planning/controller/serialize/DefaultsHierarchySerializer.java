package com.mns.ssi.initial.planning.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mns.ssi.initial.planning.model.DefaultsHierarchy;
import com.mns.ssi.initial.planning.model.DefaultsNode;
import com.mns.ssi.initial.planning.model.Product;

import java.io.IOException;
import java.util.Set;

public class DefaultsHierarchySerializer extends StdSerializer<DefaultsHierarchy> {
    public DefaultsHierarchySerializer() {
        this(null);
    }

    public DefaultsHierarchySerializer(Class<DefaultsHierarchy> t) {
        super(t);
    }

    @Override
    public void serialize(DefaultsHierarchy defaultsHierarchy,
                          JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        Set<DefaultsNode> nodes = defaultsHierarchy.head().stream().findFirst().get().getChildren();
        jsonGenerator.writeObjectField("defaults", nodes);
        jsonGenerator.writeEndObject();
    }
}
