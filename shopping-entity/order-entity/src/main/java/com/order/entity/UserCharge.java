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
public class UserCharge implements Serializable {
    private static final long serialVersionUID = 8732781227333189985L;
    private Long id;
    private Long userId;
    private Float price;
}
