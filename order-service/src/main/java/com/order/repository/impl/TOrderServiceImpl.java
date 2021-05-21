package com.order.repository.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import com.order.entity.TOrder;
import com.order.mapper.TOrderMapper;
import com.order.repository.TOrderService;

import java.util.List;

@Service
public class TOrderServiceImpl implements TOrderService{

    @Resource
    private TOrderMapper tOrderMapper;

    @Override
    public List<TOrder> getAll() {
        return tOrderMapper.getAll();
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        tOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(TOrder record) {
        tOrderMapper.insertSelective(record);
    }

    @Override
    public TOrder selectByPrimaryKey(Long id) {
        return tOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(TOrder record) {
        tOrderMapper.updateByPrimaryKeySelective(record);
    }

}
