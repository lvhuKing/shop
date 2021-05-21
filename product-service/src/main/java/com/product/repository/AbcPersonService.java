package com.product.repository;

import com.product.entity.AbcPerson;
public interface AbcPersonService{


    int deleteByPrimaryKey(Long userId);

    int insert(AbcPerson record);

    int insertSelective(AbcPerson record);

    AbcPerson selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(AbcPerson record);

    int updateByPrimaryKey(AbcPerson record);

}
