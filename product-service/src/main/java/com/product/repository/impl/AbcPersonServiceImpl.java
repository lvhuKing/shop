package com.product.repository.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.product.entity.AbcPerson;
import com.product.mapper.AbcPersonMapper;
import com.product.repository.AbcPersonService;
@Service
public class AbcPersonServiceImpl implements AbcPersonService{

    @Resource
    private AbcPersonMapper abcPersonMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return abcPersonMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(AbcPerson record) {
        return abcPersonMapper.insert(record);
    }

    @Override
    public int insertSelective(AbcPerson record) {
        return abcPersonMapper.insertSelective(record);
    }

    @Override
    public AbcPerson selectByPrimaryKey(Long userId) {
        return abcPersonMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(AbcPerson record) {
        return abcPersonMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AbcPerson record) {
        return abcPersonMapper.updateByPrimaryKey(record);
    }

}
