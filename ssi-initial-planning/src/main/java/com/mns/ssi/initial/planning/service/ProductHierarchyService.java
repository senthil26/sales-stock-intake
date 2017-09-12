package com.mns.ssi.initial.planning.service;


import com.mns.ssi.initial.planning.model.Level;
import com.mns.ssi.initial.planning.model.ProductHierarchy;

import java.util.List;

public interface ProductHierarchyService<T> {
    ProductHierarchy getChildrenHierarchy(T id, Integer depth);
    ProductHierarchy getHierarchy(List<T> ids);
    ProductHierarchy getParentHierarchy(List<T> ids, Level level);
}
