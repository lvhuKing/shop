package com.order.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TCredit implements Serializable {
    /**
    * 积分表
    */
    private Integer id;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 用户姓名
    */
    private String username;

    /**
    * 积分
    */
    private Integer integration;

    private static final long serialVersionUID = 1L;
}