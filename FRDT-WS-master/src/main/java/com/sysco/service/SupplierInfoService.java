package com.sysco.service;

import com.sysco.entity.SupplierLog;
import com.sysco.entity.VendorInfo;
import com.sysco.entity.ShipPointOpcoDto;
import com.sysco.request.*;
import com.sysco.response.GenericResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * Created by james.zhu on 2018/8/22.
 */
public interface SupplierInfoService {
    void createSupplier(ReqSupplier reqSupplier);
    void sendAttachmentsMail(ReqSupplier reqSupplier);
    String uploadDocuments(MultipartFile file, String syscoSuvc);
    String downLoadDocuments(String syscoSuvc, String fileUrl);
    void deleteDocuments(String supplierName, String fileUrl);
    String upLoadSupplierTemp(MultipartFile file,String email);
    boolean updateDateOfReview(DateOfReview dateOfReview);
    GenericResponse fetchSCSSupplier(SCSFilterCondition scsFilterCondition);
    List<ShipPointOpcoDto> selectOpcoList(ShipOpcoFilterCondition shipOpcoFilterCondition);

    VendorInfo getVendorInfoById(String vendorId);

    List<SupplierLog> selectLogList();

    String modifySupplier(ReqModifyVendor reqModifyVendor);
    void sendModifyEmail(String uuid, ReqModifyVendor reqModifyVendor);
    void updateFreightRate(ReqUpdateFreightRate updateFreightRate);
}
