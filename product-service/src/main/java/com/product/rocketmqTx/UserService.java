package com.product.rocketmqTx;

import java.math.BigDecimal;

public interface UserService {

    Boolean transferMoney(Long userId, BigDecimal money, String msgId);
}
