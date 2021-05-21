package com.order.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TUser implements Serializable {
    /**
    * 用户表
    */
    private Integer id;

    /**
    * 姓名
    */
    private String name;

    /**
    * 身份证号
    */
    private String idCard;

    /**
    * 余额
    */
    private Integer balance;

    /**
    * 状态（1在线，0离线）
    */
    private Boolean state;

    /**
    * VIP用户标识（1是，0否）
    */
    private Boolean vipFlag;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 最后一次登录时间
    */
    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;
}