package com.product.mapper;

import com.product.entity.TransferDetail;

public interface TransferDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransferDetail record);

    int insertSelective(TransferDetail record);

    /**根据消息Id获取交易明细*/
    TransferDetail getByMsgId(String msgId);
    
    TransferDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransferDetail record);

    int updateByPrimaryKey(TransferDetail record);
}