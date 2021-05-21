package com.basic.repository;

import shop.basic.entity.TUser;
public interface TUserService{


    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    TUser selectByPhone(String phone);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

}
