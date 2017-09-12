package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.model.Criteria;
import com.mns.ssi.initial.planning.model.ProductDetail;
import com.mns.ssi.initial.planning.service.ProductDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.mns.ssi.initial.planning.model.Criteria.Attribute.HIERARCHY_ID;
import static com.mns.ssi.initial.planning.model.Criteria.Attribute.SUPER_SEASON;
import static java.util.Arrays.asList;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDetailsServiceImplTest {

    @Autowired
    private ProductDetailsService productDetailsService;

    @Test
    public void whenGetProducts_ValidURL_ShouldReturnProducts() {
        Criteria productsCriteria = Criteria.builder()
                .filter(HIERARCHY_ID, asList("T03-210903"))
                .filter(SUPER_SEASON, asList("AU17"))
                .pageIndex(0)
                .pageSize(10)
                .build();

        List<ProductDetail> productDetails = productDetailsService.getProducts(productsCriteria);
        assertThat(productDetails).isNotEmpty();
    }
}
