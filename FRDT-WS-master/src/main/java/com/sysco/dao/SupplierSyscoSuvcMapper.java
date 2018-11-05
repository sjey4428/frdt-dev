package com.sysco.dao;

import com.sysco.entity.SupplierSyscoSuvc;
import com.sysco.entity.SupplierSyscoSuvcKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierSyscoSuvcMapper {
    int deleteByPrimaryKey(SupplierSyscoSuvcKey key);

    int insert(SupplierSyscoSuvc record);

    int insertSelective(SupplierSyscoSuvc record);

    SupplierSyscoSuvc selectByPrimaryKey(SupplierSyscoSuvcKey key);

    int updateByPrimaryKeySelective(SupplierSyscoSuvc record);

    int updateByPrimaryKey(SupplierSyscoSuvc record);

    List<SupplierSyscoSuvc> selectBySyscoSuvc(@Param("syscoSuvcs") String[]  syscoSuvcs);

    int insertBatch(List<SupplierSyscoSuvc> supplierSyscoSuvcs);
}