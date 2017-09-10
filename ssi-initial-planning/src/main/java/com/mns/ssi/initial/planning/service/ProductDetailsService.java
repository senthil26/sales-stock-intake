package com.mns.ssi.initial.planning.service;

import com.mns.ssi.initial.planning.entity.Criteria;
import com.mns.ssi.initial.planning.entity.Product;

import java.util.List;

public interface ProductDetailsService {
    List<Product> getProducts(Criteria criteria);
}
