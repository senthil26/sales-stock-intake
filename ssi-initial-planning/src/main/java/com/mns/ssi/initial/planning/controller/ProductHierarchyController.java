package com.mns.ssi.initial.planning.controller;

import com.mns.ssi.initial.planning.controller.dto.Error;
import com.mns.ssi.initial.planning.controller.dto.HierarchyIdsAndLevel;
import com.mns.ssi.initial.planning.model.Level;
import com.mns.ssi.initial.planning.exception.ProductHierarchyNotFoundException;
import com.mns.ssi.initial.planning.exception.ProductHierarchyServiceException;
import com.mns.ssi.initial.planning.model.ProductHierarchy;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hierarchy")
public class ProductHierarchyController {

    private ProductHierarchyService<String> productHierarchyService;

    @Autowired
    public ProductHierarchyController(ProductHierarchyService<String> productHierarchyService) {
        this.productHierarchyService = productHierarchyService;
    }

    @RequestMapping(value = "/{id}/children/{depth}", method = RequestMethod.GET)
    public ProductHierarchy childrenHierarchy(@PathVariable String id, @PathVariable String depth) {
        return productHierarchyService.getChildrenHierarchy(id, Integer.valueOf(depth));
    }

    @RequestMapping(value = "/parent", method = RequestMethod.POST)
    public ProductHierarchy parentHierarchy(@RequestBody HierarchyIdsAndLevel hierarchyIdsAndLevel) {
        Level level = Level.from(hierarchyIdsAndLevel.getLevelId());
        return productHierarchyService.getParentHierarchy(hierarchyIdsAndLevel.getHierarchyIds(), level);
    }

    @ExceptionHandler(ProductHierarchyServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error hierarchyError(ProductHierarchyServiceException hierarchyError)
            throws IOException {
        return new Error(hierarchyError.getMessage());
    }

    @ExceptionHandler(ProductHierarchyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error hierarchyNotFoundError(ProductHierarchyNotFoundException hierarchyNotFound)
            throws IOException {
        return new Error(hierarchyNotFound.getMessage());
    }
}