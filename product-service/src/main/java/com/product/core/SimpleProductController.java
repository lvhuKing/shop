package com.product.core;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.common.model.Const;
import com.common.model.JsonResult;
import com.common.util.IdWorker;
import com.product.repository.TProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import shop.product.SimpleProductAPI;
import com.product.entity.TProduct;

import javax.annotation.Resource;

@RestController
public class SimpleProductController implements SimpleProductAPI {
    
    @Resource
    private TProductService tProductServiceImpl;

    @Override
    @SentinelResource(value = "testFeignProduct1", fallback = "fallbackHandler")
    public JsonResult testProduct1(String param) {
        if("xxx".equals(param)){
            throw new RuntimeException("人造异常");
        }
        return new JsonResult("你好，"+param);
    }

    /**Sentinel和feign同时存在降级方法时，Sentinel生效*/
    public JsonResult fallbackHandler(String param){
        return new JsonResult(Const.Context.ERROR,"product熔断测试",Const.Context.FALLBACKMSG);
    }

    @Override
    public JsonResult getAll() {
        return new JsonResult(tProductServiceImpl.getAll());
    }

    @Override
    public JsonResult addProduct(TProduct tProduct) {
        tProduct.setId(IdWorker.getId());
        tProductServiceImpl.insertSelective(tProduct);
        return new JsonResult("新增产品信息成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult editProduct(TProduct tProduct) {
        if(tProduct.getProductNo() < 0){
            throw new RuntimeException("【"+tProduct.getProductName()+"】库存不足");
        }
        tProductServiceImpl.updateByPrimaryKeySelective(tProduct);
        return new JsonResult("修改产品信息成功");
    }

    @Override
    public JsonResult deleteProduct(Long tpId) { 
        tProductServiceImpl.deleteByPrimaryKey(tpId);
        return new JsonResult("删除产品信息成功");
    }

    @Override
    public TProduct getOne(Long tpId) {
        TProduct tProduct = tProductServiceImpl.selectByPrimaryKey(tpId);
        System.out.println("产品Id"+tpId);
        System.out.println("产品信息"+tProduct.toString());
        return tProductServiceImpl.selectByPrimaryKey(tpId);
    }
}
