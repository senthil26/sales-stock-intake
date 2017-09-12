package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.model.DefaultsHierarchy;
import com.mns.ssi.initial.planning.service.ProductDefaultsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDefaultsServiceImplTest {

    @Autowired
    private ProductDefaultsService productDefaultsService;

    @Test
    public void whenGetHierarchyDefaults_ValidURL_ShouldReturnDefaultHierarchy() {

        DefaultsHierarchy defaultsHierarchy =
                productDefaultsService.getHierarchyDefaults(asList("T07-310903","T03-210875"), 0, 10);
        assertThat(defaultsHierarchy.defaultNodes()).isNotEmpty();

        System.out.println(defaultsHierarchy.head().stream().findFirst().get().getChildren());
    }
}
