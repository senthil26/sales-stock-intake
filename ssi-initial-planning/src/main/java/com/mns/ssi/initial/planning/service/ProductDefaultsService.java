package com.mns.ssi.initial.planning.service;

import com.mns.ssi.initial.planning.model.DefaultsHierarchy;
import com.mns.ssi.initial.planning.model.DefaultsNode;
import com.mns.ssi.initial.planning.model.DefaultsStrokeColour;

import java.util.List;

public interface ProductDefaultsService {
    DefaultsHierarchy getHierarchyDefaults(List<String> hierarchyIds, int pageIndex, int pageSize);
    List<DefaultsNode> getStrokeColourDefaults(List<String> hierarchyIds, int pageIndex, int pageSize);
    List<DefaultsNode> editDefaults(List<DefaultsNode> defaults);
}
