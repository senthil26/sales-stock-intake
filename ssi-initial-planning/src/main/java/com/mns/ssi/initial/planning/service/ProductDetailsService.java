package com.mns.ssi.initial.planning.service;

import com.mns.ssi.initial.planning.model.Criteria;
import com.mns.ssi.initial.planning.model.ProductDetail;

import java.util.List;

public interface ProductDetailsService {
    List<ProductDetail> getProducts(Criteria criteria);
}
