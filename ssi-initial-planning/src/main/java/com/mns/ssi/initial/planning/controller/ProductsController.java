package com.mns.ssi.initial.planning.controller;

import com.mns.ssi.initial.planning.controller.dto.ProductsServiceError;
import com.mns.ssi.initial.planning.controller.dto.SeasonalProducts;
import com.mns.ssi.initial.planning.entity.Criteria;
import com.mns.ssi.initial.planning.entity.Product;
import com.mns.ssi.initial.planning.exception.ProductsServiceException;
import com.mns.ssi.initial.planning.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.mns.ssi.initial.planning.entity.Product.Attribute.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductsController {
    private final ProductDetailsService productService;

    @Autowired
    public ProductsController(ProductDetailsService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Product> products(@RequestBody SeasonalProducts seasonalProducts) {
        Criteria productsCriteria = Criteria.builder()
                .filter(HIERARCHY_ID, seasonalProducts.getHierarchyIds())
                .filter(SUPER_SEASON, seasonalProducts.getSuperSeasons())
                .pageIndex(seasonalProducts.getPage())
                .pageSize(seasonalProducts.getSize())
                .build();

        return productService.getProducts(productsCriteria);
    }

    @ExceptionHandler(ProductsServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProductsServiceError hierarchyError(ProductsServiceException hierarchyError)
            throws IOException {
        return new ProductsServiceError(hierarchyError.getMessage());
    }

}
