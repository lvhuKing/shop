package com.product.mapper;

import com.product.entity.AbcPerson;

public interface AbcPersonMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(AbcPerson record);

    int insertSelective(AbcPerson record);

    AbcPerson getByMobile(String mobile);
    
    AbcPerson selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(AbcPerson record);

    int updateByPrimaryKey(AbcPerson record);
}