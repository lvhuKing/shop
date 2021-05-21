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
public class TMqTransactionLog implements Serializable {
    /**
    * 事务id
    */
    private String transactionId;

    /**
    * 日志
    */
    private String log;

    private static final long serialVersionUID = 1L;
}