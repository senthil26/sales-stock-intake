package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.model.ProductHierarchy;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductHierarchyServiceImplTest {

    @Autowired
    private ProductHierarchyService<String> productHierarchyService;

    @Test
    public void whenGetHierarchy_ValidURL_ShouldReturnNodes() {
        ProductHierarchy productHierarchy = productHierarchyService.getHierarchy(asList("T03-210875"));
        assertThat(productHierarchy.getNodes()).isNotEmpty();
    }
}
