package com.mns.ssi.initial.planning.controller;

import com.mns.ssi.initial.planning.exception.ProductDefaultsNotFoundException;
import com.mns.ssi.initial.planning.exception.ProductDefaultsServiceException;
import com.mns.ssi.initial.planning.model.DefaultsHierarchy;
import com.mns.ssi.initial.planning.model.DefaultsNode;
import com.mns.ssi.initial.planning.model.PageableHierarchyId;
import com.mns.ssi.initial.planning.service.ProductDefaultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/defaults")
public class ProductDefaultsController {
    private final ProductDefaultsService productDefaultsService;

    @Autowired
    public ProductDefaultsController(ProductDefaultsService productDefaultsService) {
        this.productDefaultsService = productDefaultsService;
    }

    @RequestMapping(value = "/hierarchy-ids", method = RequestMethod.POST)
    public DefaultsHierarchy hierarchyDefaults(@RequestBody PageableHierarchyId hierarchyIds) {
        return productDefaultsService.getHierarchyDefaults(hierarchyIds.getHierarchyIds(),
                hierarchyIds.getPage(), hierarchyIds.getSize());
    }

    @RequestMapping(value = "/stroke-colours", method = RequestMethod.POST)
    public List<DefaultsNode> strokeColourDefaults(@RequestBody PageableHierarchyId hierarchyIds) {
        return productDefaultsService.getStrokeColourDefaults(hierarchyIds.getHierarchyIds(),
                hierarchyIds.getPage(), hierarchyIds.getSize());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public List<DefaultsNode> editDefaults(@RequestBody List<DefaultsNode> defaults) {
        return productDefaultsService.editDefaults(defaults);
    }


    @ExceptionHandler(ProductDefaultsServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error hierarchyError(ProductDefaultsServiceException hierarchyError)
            throws IOException {
        return new Error(hierarchyError.getMessage());
    }

    @ExceptionHandler(ProductDefaultsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error hierarchyError(ProductDefaultsNotFoundException hierarchyError)
            throws IOException {
        return new Error(hierarchyError.getMessage());
    }

}
