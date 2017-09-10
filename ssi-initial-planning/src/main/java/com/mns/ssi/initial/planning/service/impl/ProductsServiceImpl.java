package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.entity.Criteria;
import com.mns.ssi.initial.planning.entity.Node;
import com.mns.ssi.initial.planning.entity.Level;
import com.mns.ssi.initial.planning.entity.Product;
import com.mns.ssi.initial.planning.exception.ProductsServiceException;
import com.mns.ssi.initial.planning.service.ProductDetailsService;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import com.mns.ssi.initial.planning.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static com.mns.ssi.initial.planning.entity.Product.Attribute.*;
import static java.util.Collections.emptyList;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final String DASH = "-";
    private static final String EMPTY = "";
    private static final String SLASH = "/";

    private final ProductHierarchyService<String> productHierarchyService;
    private final ProductDetailsService productDetailsService;

    @Autowired
    public ProductsServiceImpl(ProductHierarchyService<String> productHierarchyService,
                                   ProductDetailsService productDetailsService) {
        this.productHierarchyService = productHierarchyService;
        this.productDetailsService = productDetailsService;
    }

    @Override
    public List<Product> getProducts(Criteria criteria) {
        List<String> hierarchyIds = criteria.getFilter(HIERARCHY_ID);
        if (hierarchyIds.isEmpty()) {
            throw new ProductsServiceException("Cannot find Products based on an empty Node Ids");
        }

        final Set<Node> nodes =
                productHierarchyService.getHierarchy(hierarchyIds);

        final Set<String> itemIds = nodes.stream()
                .filter(n -> n.getLevelId() == Level.ITEM)
                .map(n -> n.getId())
                .collect(Collectors.toSet());

        List<String> superSeasons = criteria.getFilter(SUPER_SEASON);
        if (superSeasons == null) {
            superSeasons = emptyList();
        }

        Criteria coreCriteria = Criteria.builder()
                .filter(HIERARCHY_ID, new ArrayList<>(itemIds))
                .filter(SUPER_SEASON, superSeasons)
                .build();

        List<Product> products = productDetailsService.getProducts(coreCriteria);
        products.stream()
                .forEach(product -> {
                        String department = getDepartment(product.getAttribute(HIERARCHY_ID));
                        String lineDescriptionWithColor = lineDescription(department, product);
                        product.setAttribute(LINE_DESCRIPTION, lineDescriptionWithColor);
                    });

        return products;
    }

    private String getDepartment(String hierarchyId) {
        StringTokenizer tokenizer = new StringTokenizer(hierarchyId, DASH);
        if(tokenizer.hasMoreTokens()) {
            return tokenizer.nextToken();
        }

        return EMPTY;
    }


    private String lineDescription(String department, Product product) {
        StringBuilder builder = new StringBuilder();
        builder.append(department);
        builder.append(SLASH);
        builder.append(product.getAttribute(STROKE_NUMBER)).append(SLASH);
        builder.append(product.getAttribute(LONG_DESCRIPTION)).append(SLASH);
        builder.append((product.getColour() == null) ? EMPTY : product.getColour().toString());

        return builder.toString();
    }
}
