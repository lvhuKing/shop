package com.basic.repository.impl;

import com.basic.repository.TUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.basic.mapper.TUserMapper;
import shop.basic.entity.TUser;

@Service
public class TUserServiceImpl implements TUserService {

    @Resource
    private TUserMapper tUserMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TUser record) {
        return tUserMapper.insert(record);
    }

    @Override
    public int insertSelective(TUser record) {
        return tUserMapper.insertSelective(record);
    }

    @Override
    public TUser selectByPrimaryKey(Long id) {
        return tUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public TUser selectByPhone(String phone) {
        return tUserMapper.selectByPhone(phone);
    }

    @Override
    public int updateByPrimaryKeySelective(TUser record) {
        return tUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TUser record) {
        return tUserMapper.updateByPrimaryKey(record);
    }

}
