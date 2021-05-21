package com.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TProduct implements Serializable {
    private Long id;

    private String productName;

    /**
    * 数量*/
    
    private Integer productNo;

    private static final long serialVersionUID = 1L;
}