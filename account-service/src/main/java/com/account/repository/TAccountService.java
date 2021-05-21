package com.account.repository;

import shop.account.entity.TAccount;

import java.util.List;

public interface TAccountService{

    List<TAccount> getAll();
    
    void deleteByPrimaryKey(Long id);

    void insertSelective(TAccount record);

    TAccount selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(TAccount record);

}
