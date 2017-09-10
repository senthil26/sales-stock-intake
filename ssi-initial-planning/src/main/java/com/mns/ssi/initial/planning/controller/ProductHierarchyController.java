package com.mns.ssi.initial.planning.controller;

import com.mns.ssi.initial.planning.controller.dto.HierarchyError;
import com.mns.ssi.initial.planning.controller.dto.ProductHierarchyIds;
import com.mns.ssi.initial.planning.entity.Level;
import com.mns.ssi.initial.planning.entity.Node;
import com.mns.ssi.initial.planning.exception.ProductHierarchyNotFoundException;
import com.mns.ssi.initial.planning.exception.ProductHierarchyServiceException;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

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
    public Set<Node> childrenHierarchy(@PathVariable String id, @PathVariable String depth) {
        return productHierarchyService.getChildrenHierarchy(id, Integer.valueOf(depth));
    }

    @RequestMapping(value = "/parent", method = RequestMethod.POST)
    public Set<Node> parentHierarchy(@RequestBody ProductHierarchyIds productHierarchyIds) {
        Level level = Level.from(productHierarchyIds.getLevelId());
        return productHierarchyService.getParentHierarchy(productHierarchyIds.getHierarchyIds(), level);
    }

    @ExceptionHandler(ProductHierarchyServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HierarchyError hierarchyError(ProductHierarchyServiceException hierarchyError)
            throws IOException {
        return new HierarchyError(hierarchyError.getMessage());
    }

    @ExceptionHandler(ProductHierarchyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HierarchyError hierarchyNotFoundError(ProductHierarchyNotFoundException hierarchyNotFound)
            throws IOException {
        return new HierarchyError(hierarchyNotFound.getMessage());
    }
}