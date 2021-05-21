package com.product.repository.impl;

import com.product.repository.TProductService;
//import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.product.entity.TProduct;
import com.product.mapper.TProductMapper;

import java.util.List;

@Service
public class TProductServiceImpl implements TProductService {

    @Resource
    private TProductMapper tProductMapper;

    @Override
    public List<TProduct> getAll() {
        return tProductMapper.getAll();
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        tProductMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(TProduct record) {
        tProductMapper.insertSelective(record);
    }

    @Override
    public TProduct selectByPrimaryKey(Long id) {
        return tProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(TProduct record) {
        tProductMapper.updateByPrimaryKeySelective(record);
    }

}
