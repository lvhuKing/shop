package com.order.repository.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.order.entity.HxPerson;
import com.order.mapper.HxPersonMapper;
import com.order.repository.HxPersonService;
@Service
public class HxPersonServiceImpl implements HxPersonService{

    @Resource
    private HxPersonMapper hxPersonMapper;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return hxPersonMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(HxPerson record) {
        return hxPersonMapper.insert(record);
    }

    @Override
    public int insertSelective(HxPerson record) {
        return hxPersonMapper.insertSelective(record);
    }

    @Override
    public HxPerson selectByPrimaryKey(Integer userId) {
        return hxPersonMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(HxPerson record) {
        return hxPersonMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HxPerson record) {
        return hxPersonMapper.updateByPrimaryKey(record);
    }

}
