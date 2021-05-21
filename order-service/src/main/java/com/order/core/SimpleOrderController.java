package com.order.core;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.common.model.Const;
import com.common.model.JsonResult;
import com.common.util.IdWorker;
import com.order.repository.TOrderService;
import org.springframework.web.bind.annotation.RestController;
import shop.account.SimpleAccountAPI;
import shop.account.entity.TAccount;
import shop.order.SimpleOrderAPI;
import com.order.entity.SellRequestDTO;
import com.order.entity.TOrder;
import shop.product.SimpleProductAPI;
import com.product.entity.TProduct;

import javax.annotation.Resource;

@RestController
public class SimpleOrderController implements SimpleOrderAPI {
    
    @Resource
    private SimpleProductAPI simpleProductAPI;
    @Resource
    private SimpleAccountAPI simpleAccountAPI;
    @Resource
    private TOrderService tOrderServiceImpl;
    
    @Override
    @SentinelResource(value = "testFeignProduct1", blockHandler = "handleException")
    public JsonResult testFeignProduct1(String param) {
        if("yyy".equals(param)){
            throw new RuntimeException("调用者异常");
        }
        return simpleProductAPI.testProduct1(param);
    }

    /**限流方法*/
    public JsonResult handleException(String param, BlockException blockException){
        System.out.println("flow exception: "+blockException.getClass().getCanonicalName());
        return new JsonResult(Const.Context.ERROR,"",Const.Context.LIMITMSG);
    }

    @Override
    public JsonResult getAll() {
        return new JsonResult(tOrderServiceImpl.getAll());
    }

    @Override
    public JsonResult addOrder(TOrder tOrder) {
        tOrder.setId(IdWorker.getId());
        tOrderServiceImpl.insertSelective(tOrder);
        return new JsonResult("新增订单成功");
    }

    @Override
    public JsonResult editOrder(TOrder tOrder) {
        tOrderServiceImpl.updateByPrimaryKeySelective(tOrder);
        return new JsonResult("修改订单成功");
    }

    @Override
    public JsonResult deleteOrder(Long toId) {
        tOrderServiceImpl.deleteByPrimaryKey(toId);
        return new JsonResult("删除订单成功");
    }

    /**下单服务*/
    @Override
//    @GlobalTransactional(rollbackFor = Exception.class)
    public JsonResult sellProduct(SellRequestDTO dto) {
        try {
            TAccount tAccount = simpleAccountAPI.getOne(dto.getTaId());
            TProduct tProduct = simpleProductAPI.getOne(dto.getTpId());
            /**1、生成订单*/
            TOrder tOrder = TOrder.builder().id(IdWorker.getId()).orderNo(10101L)
                    .orderInfo("账户【"+dto.getTaId()+"】购买商品【"+tProduct.getProductName()+"】，总计花费￥"+dto.getPrice()).build();
            tOrderServiceImpl.insertSelective(tOrder);
            /**2、扣除余额*/
            tAccount.setAccountValue(tAccount.getAccountValue() - dto.getPrice());
            simpleAccountAPI.editAccount(tAccount);
            /**3、库存扣除购买数量*/
            tProduct.setProductNo(tProduct.getProductNo() - dto.getProductNo());
            simpleProductAPI.editProduct(tProduct);
            return new JsonResult("下单成功");
        } catch (Exception e) {
            return new JsonResult(Const.Context.ERROR,"",e.getMessage());
        }
    }
}
