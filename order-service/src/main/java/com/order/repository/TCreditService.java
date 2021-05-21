package com.order.repository;

import com.order.entity.TCredit;
public interface TCreditService{


    int deleteByPrimaryKey(Integer id);

    int insert(TCredit record);

    int insertSelective(TCredit record);

    TCredit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCredit record);

    int updateByPrimaryKey(TCredit record);

}
