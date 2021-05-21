package com.order.mapper;

import com.order.entity.HxPerson;

public interface HxPersonMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(HxPerson record);

    int insertSelective(HxPerson record);

    HxPerson selectByMobile(String mobile);
    
    HxPerson selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(HxPerson record);

    int updateByPrimaryKey(HxPerson record);
}