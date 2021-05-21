package com.order.mapper;

import com.order.entity.TOrder;

import java.util.List;

public interface TOrderMapper {
    List<TOrder> getAll();
    
    void deleteByPrimaryKey(Long id);

    void insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(TOrder record);
}