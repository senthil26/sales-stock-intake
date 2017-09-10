package com.mns.ssi.enterprise.adapter;

import org.junit.Test;

public class ProductHierarchyServiceAdapterTest {

    @Test
    public void getChildrenHierarchy_WhenValidURL_ShouldReturnNodes() {
        ProductHierarchyServiceAdapter adapter = new ProductHierarchyServiceAdapter();
        adapter.getChildrenHierarchy("3", 6);
    }
}
