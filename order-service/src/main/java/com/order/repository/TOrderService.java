package com.order.repository;

import com.order.entity.TOrder;

import java.util.List;

public interface TOrderService{

    List<TOrder> getAll();
    
    void deleteByPrimaryKey(Long id);

    void insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(TOrder record);

}
