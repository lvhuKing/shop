package com.order.rocketmqTx;

import java.math.BigDecimal;

public interface UserService {

    Boolean transferMoney(String mobile, BigDecimal money);
    
}
