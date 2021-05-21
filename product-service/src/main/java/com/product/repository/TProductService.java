package com.product.repository;

import com.product.entity.TProduct;

import java.util.List;

public interface TProductService{

    List<TProduct> getAll();
    
    void deleteByPrimaryKey(Long id);

    void insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(TProduct record);

}
