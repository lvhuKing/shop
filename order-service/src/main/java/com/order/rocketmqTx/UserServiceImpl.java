package com.order.rocketmqTx;

import com.order.entity.HxPerson;
import com.order.mapper.HxPersonMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private HxPersonMapper hxPersonMapper;
    
    @Transactional
    @Override
    public Boolean transferMoney(String mobile, BigDecimal money) {
        boolean flag = false;
        // 加悲观锁
        HxPerson hxPerson = hxPersonMapper.selectByMobile(mobile);
        if (ObjectUtils.isNotEmpty(hxPerson)) {
            hxPerson.setBanlance(new BigDecimal(hxPerson.getBanlance().doubleValue() + money.doubleValue()));
            flag = hxPersonMapper.updateByPrimaryKeySelective(hxPerson) > 0 ? true : false;
        }
        return flag;
    }

}
