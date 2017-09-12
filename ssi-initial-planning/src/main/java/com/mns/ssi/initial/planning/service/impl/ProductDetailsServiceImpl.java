package com.mns.ssi.initial.planning.service.impl;

import com.mns.ssi.initial.planning.exception.ProductDetailsServiceException;
import com.mns.ssi.initial.planning.model.Criteria;
import com.mns.ssi.initial.planning.model.ProductDetail;
import com.mns.ssi.initial.planning.service.ProductDetailsService;
import com.mns.ssi.tech.core.rest.dto.RESTResponse;
import com.mns.ssi.tech.core.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mns.ssi.initial.planning.util.Converters.*;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    private static final String PRODUCT_RESOURCE = "/product/";
    private static final String SEARCH = "search";

    private final String server;

    public ProductDetailsServiceImpl(@Value("${product.details.service.server}") String server) {
        this.server = server;
    }

    @Override
    public List<ProductDetail> getProducts(Criteria criteria) {
        try {
            String url = new StringBuilder()
                    .append(server)
                    .append(PRODUCT_RESOURCE)
                    .append(SEARCH)
                    .toString();

            String json = toHiearchySeasonString(criteria);
            RESTResponse restResponse = RestClientUtil.callServicePost(url, json, null, null);
            return toProductDetails(restResponse.getResult());
        } catch(Exception anyError) {
            throw new ProductDetailsServiceException("Error consuming a response from ProductDetail Service", anyError);
        }
    }

}
