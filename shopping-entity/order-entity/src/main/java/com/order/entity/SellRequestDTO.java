package com.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellRequestDTO implements Serializable {
    private static final long serialVersionUID = -6672153057383786622L;
    /**产品ID*/
    private Long tpId;
    /**购买产品数量*/
    private Integer productNo;
    /**产品总价*/
    private Double price;
    /**购买者账户ID*/
    private Long taId;
}
