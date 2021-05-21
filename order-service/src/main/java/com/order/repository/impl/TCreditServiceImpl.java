package com.order.repository.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.order.mapper.TCreditMapper;
import com.order.entity.TCredit;
import com.order.repository.TCreditService;
@Service
public class TCreditServiceImpl implements TCreditService{

    @Resource
    private TCreditMapper tCreditMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tCreditMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TCredit record) {
        return tCreditMapper.insert(record);
    }

    @Override
    public int insertSelective(TCredit record) {
        return tCreditMapper.insertSelective(record);
    }

    @Override
    public TCredit selectByPrimaryKey(Integer id) {
        return tCreditMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TCredit record) {
        return tCreditMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TCredit record) {
        return tCreditMapper.updateByPrimaryKey(record);
    }

}
