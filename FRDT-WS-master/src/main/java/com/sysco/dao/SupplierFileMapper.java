package com.sysco.dao;

import com.sysco.entity.SupplierFileKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierFileMapper {
    int deleteByPrimaryKey(SupplierFileKey key);

    int insert(SupplierFileKey record);

    int insertSelective(SupplierFileKey record);

    int insertBatch(List<SupplierFileKey> locations);

    List<SupplierFileKey> selectByUuid(@Param("uuid") String uuid);
}