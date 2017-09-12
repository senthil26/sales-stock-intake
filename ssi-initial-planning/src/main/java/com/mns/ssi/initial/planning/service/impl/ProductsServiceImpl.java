package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.exception.ProductDefaultsServiceException;
import com.mns.ssi.initial.planning.model.*;
import com.mns.ssi.initial.planning.exception.ProductsServiceException;
import com.mns.ssi.initial.planning.service.ProductDetailsService;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import com.mns.ssi.initial.planning.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mns.ssi.initial.planning.model.Criteria.Attribute.*;
import static java.util.Collections.emptyList;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductHierarchyService<String> productHierarchyService;
    private final ProductDetailsService productDetailsService;

    @Autowired
    public ProductsServiceImpl(ProductHierarchyService<String> productHierarchyService,
                               ProductDetailsService productDetailsService) {
        this.productHierarchyService = productHierarchyService;
        this.productDetailsService = productDetailsService;
    }

    @Override
    public List<Product> getProductsBySeason(List<String> hierarchyIds,
                                             List<String> seasons,
                                             int pageIndex,
                                             int pageSize) {
        if (hierarchyIds.isEmpty()) {
            throw new ProductsServiceException("Cannot find Products based on an empty hierarchy ids");
        }

        if (seasons.isEmpty()) {
            throw new ProductsServiceException("Cannot find Products based on an empty seasons");
        }

        return findProducts(hierarchyIds, seasons, pageIndex, pageSize);
    }

    @Override
    public List<Product> getProductsById(List<String> hierarchyIds, int pageIndex, int pageSize) {
        return findProducts(hierarchyIds, emptyList(), pageIndex, pageSize);
    }

    private List<Product> findProducts(List<String> hierarchyIds, List<String> seasons,
                                       int pageIndex, int pageSize) {
        try {
            ProductHierarchy productHierarchy =
                    productHierarchyService.getHierarchy(hierarchyIds);

            Set<HierarchyNode> itemHierarchyNodes = ProductHierarchy.find(productHierarchy.getNodes(), Level.ITEM);

            Set<String> itemIds = itemHierarchyNodes.stream()
                    .map(hierarchyNode -> hierarchyNode.getId())
                    .collect(Collectors.toSet());

            Criteria productCriteria = Criteria.builder()
                    .filter(HIERARCHY_ID, new ArrayList<>(itemIds))
                    .filter(SUPER_SEASON, seasons)
                    .pageIndex(pageIndex)
                    .pageSize(pageSize)
                    .build();

            List<ProductDetail> productDetails = productDetailsService.getProducts(productCriteria);
            List<Product> products = productDetails.stream()
                    .map(productDetail -> Product.builder()
                            .hierarchyId(productDetail.getHierarchyId())
                            .articleNumber(productDetail.getArticleNumber())
                            .lineDescription(productDetail.getHierarchyId(),
                                    productDetail.getStrokeNumber(),
                                    productDetail.getLongDescription(),
                                    productDetail.getColour())
                            .build())
                    .collect(Collectors.toList());

            return products;
        } catch(Exception anyError) {
            throw new ProductDefaultsServiceException("Error while retrieving products", anyError);
        }

    }
}
