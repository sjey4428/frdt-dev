package com.sysco.dao;

import com.sysco.entity.ShipPointOpcoDto;
import com.sysco.entity.SupplierPointDetail;
import com.sysco.entity.SupplierPointDetailKey;
import com.sysco.request.ShipOpcoFilterCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierPointDetailMapper {
    int deleteByPrimaryKey(SupplierPointDetailKey key);

    int insert(SupplierPointDetail record);

    int insertSelective(SupplierPointDetail record);

    SupplierPointDetail selectByPrimaryKey(SupplierPointDetailKey key);

    int updateByPrimaryKeySelective(SupplierPointDetail record);

    int updateByPrimaryKey(SupplierPointDetail record);

    int insertBatch(List<SupplierPointDetail> records);

    List<SupplierPointDetail> selectBySupplierName(String supplierName);

    List<ShipPointOpcoDto> selectOpcoList(ShipOpcoFilterCondition shipOpcoFilterCondition);

    List<SupplierPointDetail> findPointsByUTS(@Param("shipPointNo") Integer shipPointNo, @Param("uuid")String uuid, @Param("type")String type);
}