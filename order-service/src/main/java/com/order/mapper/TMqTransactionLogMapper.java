package com.order.mapper;

import com.order.entity.TMqTransactionLog;

public interface TMqTransactionLogMapper {
    int deleteByPrimaryKey(String transactionId);

    int insert(TMqTransactionLog record);

    int insertSelective(TMqTransactionLog record);

    TMqTransactionLog selectByPrimaryKey(String transactionId);

    int updateByPrimaryKeySelective(TMqTransactionLog record);

    int updateByPrimaryKey(TMqTransactionLog record);
}