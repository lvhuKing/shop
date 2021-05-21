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
public class TOrder implements Serializable {
    private Long id;

    /**
    * 编号
    */
    private Long orderNo;

    /**
    * 详情
    */
    private String orderInfo;

    private static final long serialVersionUID = 1L;
}