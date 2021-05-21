package com.order.repository;

import com.order.entity.HxPerson;
public interface HxPersonService{


    int deleteByPrimaryKey(Integer userId);

    int insert(HxPerson record);

    int insertSelective(HxPerson record);

    HxPerson selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(HxPerson record);

    int updateByPrimaryKey(HxPerson record);

}
