package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.entity.DefaultParameter;
import com.mns.ssi.initial.planning.exception.ProductDefaultsServiceException;
import com.mns.ssi.initial.planning.model.*;
import com.mns.ssi.initial.planning.repository.DefaultsParameterRepository;
import com.mns.ssi.initial.planning.service.ProductDefaultsService;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import com.mns.ssi.initial.planning.service.ProductsService;
import com.mns.ssi.initial.planning.util.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mns.ssi.initial.planning.util.Converters.toDefaultsHierarchy;
import static com.mns.ssi.initial.planning.util.Converters.toInteger;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ProductDefaultsServiceImpl implements ProductDefaultsService {
    private static final String DEPARTMENT_HEAD = "DEPARTMENT_HEAD";
    private static final int DEEPEST_DEPTH = 4;

    private final DefaultsParameterRepository parameterRepository;
    private final ProductHierarchyService<String> productHierarchyService;
    private final ProductsService productsService;

    @Autowired
    public ProductDefaultsServiceImpl(DefaultsParameterRepository parameterRepository,
                                      ProductHierarchyService<String> productHierarchyService,
                                      ProductsService productService) {
        this.parameterRepository = parameterRepository;
        this.productHierarchyService = productHierarchyService;
        this.productsService = productService;
    }

    @Override
    public DefaultsHierarchy getHierarchyDefaults(List<String> hierarchyIds, int pageIndex, int pageSize) {
        Assert.notEmpty(hierarchyIds, "Empty Hierarchy Ids!");

        List<DefaultParameter> defaultParameters = parameterRepository.findByHierarchyIdIn(hierarchyIds,
                new PageRequest(pageIndex, pageSize));

        Map<String, DefaultParameter> parametersById = defaultParameters.stream()
                .collect(Collectors.toMap(DefaultParameter::getHierarchyId, Function.identity()));

        ProductHierarchy productHierarchy = productHierarchyService.getParentHierarchy(hierarchyIds, Level.DEPARTMENT);
        DefaultsHierarchy defaultsHierarchy = toDefaultsHierarchy(productHierarchy, parametersById);

        return defaultsHierarchy.section(DEPARTMENT_HEAD, DEEPEST_DEPTH);
    }

    @Override
    public List<DefaultsNode> getStrokeColourDefaults(List<String> hierarchyIds, int pageIndex, int pageSize) {
        try {
            List<Product> products = productsService.getProductsById(hierarchyIds, pageIndex, pageSize);
            Map<String, List<Product>> productsByHierarchyId = products.stream()
                    .collect(groupingBy(Product::getHierarchyId));

            // Enrich with article number and line description
            List<DefaultsNode> defaultsNodes = hierarchyIds.stream()
                    .map(hierarchyId -> productsByHierarchyId.get(hierarchyId))
                    .filter(p -> p != null)
                    .map(p -> p.stream()
                            .map(s -> DefaultsNode.builder()
                                    .id(s.getArticleNumber())
                                    .value(s.getLineDescription())
                                    .build())
                            .collect(Collectors.toList()))
                    .flatMap(s -> s.stream())
                    .collect(Collectors.toList());

            // Query and collect
            List<String> articleNumbers = defaultsNodes.stream()
                    .map(n -> n.getId())
                    .collect(Collectors.toList());

            List<DefaultParameter> defaultParameters = parameterRepository.findByHierarchyIdIn(articleNumbers,
                    new PageRequest(pageIndex, pageSize));

            Map<String, DefaultParameter> parametersById = defaultParameters.stream()
                    .collect(Collectors.toMap(DefaultParameter::getHierarchyId, Function.identity()));

            // Enrich nodes
            List<DefaultsNode> enrichedNodes = defaultsNodes.stream()
                    .map(enrichDefaults(parametersById))
                    .filter(n -> n.isPresent())
                    .map(n -> n.get())
                    .collect(Collectors.toList());

            return enrichedNodes;
        } catch (Exception anyError) {
            throw new ProductDefaultsServiceException(anyError.getMessage(), anyError);
        }
    }

    @Override
    public List<DefaultsNode> editDefaults(List<DefaultsNode> defaults) {
        try {
            List<DefaultParameter> defaultParameters = defaults.stream()
                    .map(Converters::toDefaultParameter)
                    .collect(Collectors.toList());

            Iterable<DefaultParameter> updatedDefaultParameters = parameterRepository.save(defaultParameters);
            return StreamSupport
                    .stream(updatedDefaultParameters.spliterator(), false)
                    .map(Converters::toDefaultsNode)
                    .collect(Collectors.toList());

        } catch (Exception anyError) {
            throw new ProductDefaultsServiceException("Error while updating defaults", anyError);
        }
    }

    private static Function<DefaultsNode, Optional<DefaultsNode>> enrichDefaults(
            Map<String, DefaultParameter> parametersById) {
        return n -> {
            DefaultParameter defaultParameter = parametersById.get(n.getId());
            if (defaultParameter != null) {
                DefaultsNode defaults = DefaultsNode.builder()
                        .id(n.getId())
                        .value(n.getValue())
                        .dcCover(Converters.toString(defaultParameter.getDcCover()))
                        .breakingStock(Converters.toString(defaultParameter.getBreakingStock()))
                        .eire(Converters.toString(defaultParameter.getEire()))
                        .build();

                return Optional.of(defaults);
            }

            return Optional.empty();
        };
    }
}
