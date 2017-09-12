package com.mns.ssi.initial.planning.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.ssi.initial.planning.controller.dto.HierarchyIdsAndLevel;
import com.mns.ssi.initial.planning.entity.DefaultParameter;
import com.mns.ssi.initial.planning.model.*;
import com.mns.ssi.initial.planning.model.ProductDetail;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.mns.ssi.initial.planning.model.Criteria.Attribute.*;

public final class Converters {
    private static final String DASH = "-";
    private static final String DEPARTMENT_HEAD = "DEPARTMENT_HEAD";
    private static final String ROOT_HEAD = "ROOT_HEAD";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ProductHierarchy toProductHierarchy(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, ProductHierarchy.class);
    }

    public static List<ProductDetail> toProductDetails(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, new TypeReference<List<ProductDetail>>() {});
    }

    public static String toString(Integer value) {
        if (value == null || value.compareTo(Integer.valueOf(0)) == 0) {
            return DASH;
        }

        return String.valueOf(value);
    }

    public static Integer toInteger(String value) {
        if(StringUtils.isEmpty(value)) {
            return null;
        }

        if(value.equals(DASH)) {
            return null;
        }

        return Integer.valueOf(value);
    }

    public static HierarchyIdsAndLevel toHierarchyIdsAndLevel(List<String> ids, Level level) {
        HierarchyIdsAndLevel hierarchyIdsAndLevel = new HierarchyIdsAndLevel();
        hierarchyIdsAndLevel.setHierarchyIds(ids);
        hierarchyIdsAndLevel.setLevelId(level.toString());

        return hierarchyIdsAndLevel;
    }

    public static String toHiearchySeasonString(Criteria criteria) {
        HierarchySeason hierarchySeason = HierarchySeason.builder()
                .hierarchyIds(criteria.getFilter(HIERARCHY_ID))
                .superSeasons(criteria.getFilter(SUPER_SEASON))
                .page(criteria.getPageIndex())
                .size(criteria.getPageSize())
                .build();

        return hierarchySeason.toString();
    }

    public static DefaultsHierarchy toDefaultsHierarchy(ProductHierarchy productHierarchy,
                                                              Map<String, DefaultParameter> parametersById) {
        DefaultsHierarchy defaultsHierarchy = DefaultsHierarchy.newInstance();
        productHierarchy.getNodes()
                .forEach(h -> defaultsHierarchy.put(DEPARTMENT_HEAD,
                        build(h, parametersById, defaultsHierarchy, h.getId())));

        defaultsHierarchy.put(ROOT_HEAD, DefaultsNode.builder()
                .id(DEPARTMENT_HEAD)
                .level(Level.DEPARTMENT.name())
                .build());

        return defaultsHierarchy;
    }

    public static DefaultParameter toDefaultParameter(DefaultsNode defaultsNode) {
        return DefaultParameter.builder()
                .hierarchyId(defaultsNode.getId())
                .dcCover(toInteger(defaultsNode.getDcCover()))
                .breakingStock(toInteger(defaultsNode.getBreakingStock()))
                .eire(toInteger(defaultsNode.getEire()))
                .build();
    }

    public static DefaultsNode toDefaultsNode(DefaultParameter defaultParameter) {
        return DefaultsNode.builder()
                .id(defaultParameter.getHierarchyId())
                .dcCover(toString(defaultParameter.getDcCover()))
                .breakingStock(toString(defaultParameter.getBreakingStock()))
                .eire(toString(defaultParameter.getEire()))
                .build();
    }



    private static DefaultsNode build(HierarchyNode hierarchyNode,
                                      Map<String, DefaultParameter> parametersById,
                                      DefaultsHierarchy defaultsHierarchy,
                                      String key) {
        DefaultsNode defaultsNode = DefaultsNode.using(hierarchyNode, parametersById);

        if (hierarchyNode.hasChildren()) {
            hierarchyNode.getChildren().stream()
                    .forEach(h -> defaultsHierarchy.put(key,
                            build(h, parametersById, defaultsHierarchy, h.getId())));
        }

        return defaultsNode;
    }




}
