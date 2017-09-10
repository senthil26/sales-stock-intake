package com.mns.ssi.initial.planning.service;


import com.mns.ssi.initial.planning.entity.Node;
import com.mns.ssi.initial.planning.entity.Level;

import java.util.List;
import java.util.Set;

public interface ProductHierarchyService<T> {
    Set<Node> getChildrenHierarchy(T id, Integer depth);
    Set<Node> getHierarchy(List<T> id);
    Set<Node> getParentHierarchy(List<T> ids, Level level);
}
