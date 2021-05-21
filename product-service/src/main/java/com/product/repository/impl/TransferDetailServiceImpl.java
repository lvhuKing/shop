package com.product.repository.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.product.mapper.TransferDetailMapper;
import com.product.entity.TransferDetail;
import com.product.repository.TransferDetailService;
@Service
public class TransferDetailServiceImpl implements TransferDetailService{

    @Resource
    private TransferDetailMapper transferDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return transferDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TransferDetail record) {
        return transferDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(TransferDetail record) {
        return transferDetailMapper.insertSelective(record);
    }

    @Override
    public TransferDetail selectByPrimaryKey(Long id) {
        return transferDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TransferDetail record) {
        return transferDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TransferDetail record) {
        return transferDetailMapper.updateByPrimaryKey(record);
    }

}
