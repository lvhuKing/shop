package com.account.mapper;

import shop.account.entity.TAccount;

import java.util.List;

public interface TAccountMapper {
    
    List<TAccount> getAll();
    
    void deleteByPrimaryKey(Long id);

    void insertSelective(TAccount record);

    TAccount selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(TAccount record);
    
}