package com.basic.mapper;

import org.apache.ibatis.annotations.Param;
import shop.basic.entity.TUser;

public interface TUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);
    
    TUser selectByPhone(@Param("phone") String phone);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}