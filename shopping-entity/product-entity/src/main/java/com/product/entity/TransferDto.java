package com.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto implements Serializable {
    private String mobile;
    private String tarMobile = "17777777777";
    private BigDecimal money;
    private Long userId;
    /**对应转账明细消息ID: msgId*/
    private Long distributedId;
}
