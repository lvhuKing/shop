package com.product.mapper;

import com.product.entity.TProduct;

import java.util.List;

public interface TProductMapper {
    
    List<TProduct> getAll();
    
    void deleteByPrimaryKey(Long id);

    void insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(TProduct record);
    
}