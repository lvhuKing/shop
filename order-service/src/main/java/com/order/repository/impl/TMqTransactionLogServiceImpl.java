package com.order.repository.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.order.entity.TMqTransactionLog;
import com.order.mapper.TMqTransactionLogMapper;
import com.order.repository.TMqTransactionLogService;
@Service
public class TMqTransactionLogServiceImpl implements TMqTransactionLogService{

    @Resource
    private TMqTransactionLogMapper tMqTransactionLogMapper;

    @Override
    public int deleteByPrimaryKey(String transactionId) {
        return tMqTransactionLogMapper.deleteByPrimaryKey(transactionId);
    }

    @Override
    public int insert(TMqTransactionLog record) {
        return tMqTransactionLogMapper.insert(record);
    }

    @Override
    public int insertSelective(TMqTransactionLog record) {
        return tMqTransactionLogMapper.insertSelective(record);
    }

    @Override
    public TMqTransactionLog selectByPrimaryKey(String transactionId) {
        return tMqTransactionLogMapper.selectByPrimaryKey(transactionId);
    }

    @Override
    public int updateByPrimaryKeySelective(TMqTransactionLog record) {
        return tMqTransactionLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TMqTransactionLog record) {
        return tMqTransactionLogMapper.updateByPrimaryKey(record);
    }

}
