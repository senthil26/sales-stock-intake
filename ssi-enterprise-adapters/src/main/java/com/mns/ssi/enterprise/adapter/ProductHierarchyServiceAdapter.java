package com.mns.ssi.enterprise.adapter;

import com.mns.ssi.initial.planning.entity.Level;
import com.mns.ssi.initial.planning.entity.Node;
import com.mns.ssi.initial.planning.exception.ProductHierarchyServiceException;
import com.mns.ssi.initial.planning.service.ProductHierarchyService;
import com.mns.ssi.tech.core.rest.dto.RESTResponse;
import com.mns.ssi.tech.core.util.JSONUtil;
import com.mns.ssi.tech.core.util.RestClientUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Component
public class ProductHierarchyServiceAdapter implements ProductHierarchyService<String> {

    public Set<Node> getChildrenHierarchy(String id, Integer depth) {
        RESTResponse restResponse = RestClientUtil
                .callServiceGet("http://localhost:8081/hierarchy/3/children/6", null, null);
        if(!StringUtils.isEmpty(restResponse.getErrorMsg())) {
            throw new ProductHierarchyServiceException(restResponse.getErrorMsg());
        }

        Set<Node> nodes = JSONUtil.jsonToJavaObject(restResponse.getResult(), Node.class);
        return nodes;
    }

    public Set<Node> getHierarchy(List<String> id) {
        return null;
    }

    public Set<Node> getParentHierarchy(List<String> ids, Level level) {
        return null;
    }
}
