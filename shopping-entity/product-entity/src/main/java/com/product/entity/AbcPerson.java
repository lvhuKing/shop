package com.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 用户账户类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbcPerson implements Serializable {
    /**
    * 用户编号
    */
    private Long userId;

    /**
    * 用户名称
    */
    private String name;

    /**
    * 身份证号
    */
    private String idCard;

    /**
    * 余额
    */
    private BigDecimal banlance;

    /**
    * 手机号
    */
    private String mobile;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 删除状态
    */
    private String deleteFlg;

    private static final long serialVersionUID = 1L;
}