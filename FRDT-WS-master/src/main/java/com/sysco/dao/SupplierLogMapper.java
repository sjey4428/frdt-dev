package com.sysco.dao;

import com.sysco.entity.SupplierLog;

import java.util.List;

public interface SupplierLogMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(SupplierLog record);

    int insertSelective(SupplierLog record);

    SupplierLog selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(SupplierLog record);

    int updateByPrimaryKey(SupplierLog record);

    List<SupplierLog> selectLogList();
}