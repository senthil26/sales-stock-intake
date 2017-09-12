package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.model.Level;
import com.mns.ssi.initial.planning.exception.ProductHierarchyServiceException;
import com.mns.ssi.initial.planning.model.ProductHierarchy;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.mns.ssi.initial.planning.util.Converters.*;

@Service
public class ProductHierarchyServiceImpl implements ProductHierarchyService<String> {
    private static final String CHILDREN_HIERARCHY = "/children/";
    private static final String HIERARCHY_RESOURCE = "/hierarchy/";
    private static final String PARENT = "parent";
    private static final String IDS = "ids";

    private final RestTemplate restTemplate;
    private final String server;

    private ProductHierarchyServiceImpl(@Value("${product.hierarchy.service.server}") String server) {
        this.server = server;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ProductHierarchy getChildrenHierarchy(String id, Integer depth) {
        try {
            String url = new StringBuilder()
                    .append(server)
                    .append(HIERARCHY_RESOURCE)
                    .append(id)
                    .append(CHILDREN_HIERARCHY)
                    .append(depth)
                    .toString();
            String response = restTemplate.getForObject(url, String.class);
            return toProductHierarchy(response);
        } catch(Exception anyError) {
            throw new ProductHierarchyServiceException("Error consuming a response from ProductHierarchy Service", anyError);
        }
    }

    @Override
    public ProductHierarchy getHierarchy(List<String> ids) {
        Assert.notEmpty(ids, "Empty hierarchy ids!");

        try {
            String url = new StringBuilder()
                    .append(server)
                    .append(HIERARCHY_RESOURCE)
                    .append(IDS)
                    .toString();

            String response = restTemplate.postForObject(url, ids, String.class);
            return toProductHierarchy(response);
        } catch(Exception anyError) {
            throw new ProductHierarchyServiceException("Error consuming a response from ProductHierarchy Service", anyError);
        }
    }

    @Override
    public ProductHierarchy getParentHierarchy(List<String> ids, Level level) {
        try {
            String url = new StringBuilder()
                    .append(server)
                    .append(HIERARCHY_RESOURCE)
                    .append(PARENT)
                    .toString();

            String response = restTemplate.postForObject(url, toHierarchyIdsAndLevel(ids, level), String.class);
            return toProductHierarchy(response);
        } catch(Exception anyError) {
            throw new ProductHierarchyServiceException("Error consuming a response from ProductHierarchy Service", anyError);
        }
    }
}
