package com.account.core;

import com.account.repository.TAccountService;
import com.common.model.JsonResult;
import com.common.util.IdWorker;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import shop.account.SimpleAccountAPI;
import shop.account.entity.TAccount;

import javax.annotation.Resource;

@RestController
public class SimpleAccountController implements SimpleAccountAPI {
    
    @Resource
    private TAccountService tAccountServiceImpl;

    @Override
    public JsonResult getAll() {
        return new JsonResult(tAccountServiceImpl.getAll());
    }

    @Override
    public TAccount getOne(Long taId) {
        TAccount tAccount = tAccountServiceImpl.selectByPrimaryKey(taId);
        System.out.println("账户Id:"+taId);
        System.out.println("账户信息"+tAccount.toString());
        return tAccount;
    }

    @Override
    public JsonResult addAccount(TAccount tAccount) {
        tAccount.setId(IdWorker.getId());
        tAccountServiceImpl.insertSelective(tAccount);
        return new JsonResult("账户新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult editAccount(TAccount tAccount) {
        if(tAccount.getAccountValue() < 0){
            throw new RuntimeException("账户【"+tAccount.getId()+"】余额不足");
        }
        tAccountServiceImpl.updateByPrimaryKeySelective(tAccount);
        return new JsonResult("账户变更成功");
    }

    @Override
    public JsonResult deleteAccount(Long taId) {
        tAccountServiceImpl.deleteByPrimaryKey(taId);
        return new JsonResult("账户注销成功");
    }
}
