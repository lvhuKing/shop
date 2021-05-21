package com.account.repository.impl;

import com.account.repository.TAccountService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import shop.account.entity.TAccount;
import com.account.mapper.TAccountMapper;

import java.util.List;

@Service
public class TAccountServiceImpl implements TAccountService {

    @Resource
    private TAccountMapper tAccountMapper;

    @Override
    public List<TAccount> getAll() {
        return tAccountMapper.getAll();
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        tAccountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(TAccount record) {
        tAccountMapper.insertSelective(record);
    }

    @Override
    public TAccount selectByPrimaryKey(Long id) {
        return tAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(TAccount record) {
        tAccountMapper.updateByPrimaryKeySelective(record);
    }

}
