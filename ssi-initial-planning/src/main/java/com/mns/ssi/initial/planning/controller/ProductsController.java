package com.mns.ssi.initial.planning.controller;

import com.mns.ssi.initial.planning.controller.dto.ProductsServiceError;
import com.mns.ssi.initial.planning.model.Product;
import com.mns.ssi.initial.planning.model.HierarchySeason;
import com.mns.ssi.initial.planning.exception.ProductsServiceException;
import com.mns.ssi.initial.planning.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Product> products(@RequestBody HierarchySeason hierarchySeason) {
        return productsService.getProductsBySeason(hierarchySeason.getHierarchyIds(),
                hierarchySeason.getSuperSeasons(),
                hierarchySeason.getPage(),
                hierarchySeason.getSize());
    }

    @RequestMapping(value = "/{hierarchyId}/{page}/{size}", method = RequestMethod.GET)
    public List<Product> products(@PathVariable String hierarchyId, int page, int size) {
        return productsService.getProductsById(hierarchyId, page, size);
    }


    @ExceptionHandler(ProductsServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProductsServiceError hierarchyError(ProductsServiceException hierarchyError)
            throws IOException {
        return new ProductsServiceError(hierarchyError.getMessage());
    }

}
