package com.product.repository;

import com.product.entity.TransferDetail;
public interface TransferDetailService{


    int deleteByPrimaryKey(Long id);

    int insert(TransferDetail record);

    int insertSelective(TransferDetail record);

    TransferDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransferDetail record);

    int updateByPrimaryKey(TransferDetail record);

}
