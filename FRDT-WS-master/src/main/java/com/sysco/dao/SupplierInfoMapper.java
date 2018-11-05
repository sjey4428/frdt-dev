package com.sysco.dao;

import com.sysco.entity.*;
import com.sysco.request.SCSFilterCondition;

import java.util.List;

public interface SupplierInfoMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(SupplierInfo record);

    int insertSelective(SupplierInfo record);

    SupplierInfo selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(SupplierInfo record);

    int updateByPrimaryKey(SupplierInfo record);

    List<SupplierInfo> selectBySupplierName(String supplierName);

    List<SupplierInfo> selectVendorByNameLike(String supplierName);

    List<Documents> fetchDocumentsBySupplierName(String vendorName);

    List<SCSSuppliersInfo> selectSCSSupplier(SCSFilterCondition scsFilterCondition);

    List<VendorDetails> getVendorOpcoInfoById(String vendorId);

    List<VendorContractException> getVendorContractExceptionById(String vendorId);

    List<VendorLocation> getVendorLocationById(String vendorId);

    List<VendorFileUrl> getVendorFileUrlById(String vendorId);
}