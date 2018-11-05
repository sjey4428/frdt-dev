package com.sysco.dao;

import com.sysco.entity.SupplierContractException;
import com.sysco.entity.SupplierContractExceptionKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierContractExceptionMapper {
    int deleteByPrimaryKey(SupplierContractExceptionKey key);

    int insert(SupplierContractException record);

    int insertSelective(SupplierContractException record);

    SupplierContractException selectByPrimaryKey(SupplierContractExceptionKey key);

    int updateByPrimaryKeySelective(SupplierContractException record);

    int updateByPrimaryKey(SupplierContractException record);

    int insertBatch(List<SupplierContractException> locations);

    SupplierContractException selectByContractException(@Param("uuid") String uuid, @Param("contractException") String contractException);

    List<SupplierContractException> selectByUuid(@Param("uuid")String uuid, @Param("deleted")String deleted);
}