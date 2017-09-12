package com.mns.ssi.initial.planning.service;

import com.mns.ssi.initial.planning.model.Product;

import java.util.List;

public interface ProductsService {
    List<Product> getProductsBySeason(List<String> hierarchyIds, List<String> seasons, int pageIndex, int pageSize);
    List<Product> getProductsById(List<String> hierarchyIds, int pageIndex, int pageSize);
}
