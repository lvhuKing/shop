package com.order.repository;

import com.order.entity.TMqTransactionLog;
public interface TMqTransactionLogService{


    int deleteByPrimaryKey(String transactionId);

    int insert(TMqTransactionLog record);

    int insertSelective(TMqTransactionLog record);

    TMqTransactionLog selectByPrimaryKey(String transactionId);

    int updateByPrimaryKeySelective(TMqTransactionLog record);

    int updateByPrimaryKey(TMqTransactionLog record);

}
