package com.sysco.dao;

import com.sysco.entity.SupplierLocation;
import com.sysco.entity.SupplierLocationKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierLocationMapper {
    int deleteByPrimaryKey(SupplierLocationKey key);

    int insert(SupplierLocation record);

    int insertSelective(SupplierLocation record);

    SupplierLocation selectByPrimaryKey(SupplierLocationKey key);

    int updateByPrimaryKeySelective(SupplierLocation record);

    int updateByPrimaryKey(SupplierLocation record);

    int insertBatch(List<SupplierLocation> locations);

    SupplierLocation selectByAddressZone(@Param("uuid")String uuid, @Param("physicalAddressZone") String physicalAddressZone);

    List<SupplierLocation> selectByUuid(@Param("uuid")String uuid, @Param("deleted")String deleted);
}