package com.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable {
    private static final long serialVersionUID = -6597156229798163950L;
    
    /**返回状态：默认ok*/
    private String status = Const.Context.OK;
    /**返回结果*/
    private Object result;
    /**返回错误信息*/
    private String errorMsg;
    
    public JsonResult(Object result){
        this.result = result;
    }
}
