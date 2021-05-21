package com.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Description 作用: 转账明细类
* @Author ccl
* @CreateDate 2021/3/22 14:10
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetail implements Serializable {
    /**
    * 明细ID
    */
    private Long id;

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 转账金额
    */
    private BigDecimal money;

    /**
    * 消息ID
    */
    private String msgId;

    /**
    * 是否删除状态
    */
    private String deleteFlg;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}