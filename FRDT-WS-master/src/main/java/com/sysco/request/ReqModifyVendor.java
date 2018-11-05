package com.sysco.request;

import com.sysco.entity.*;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by james.zhu on 2018/10/10.
 */
@Data
public class ReqModifyVendor {
    @Valid
    private SupplierInfo info;
    @Valid
    private List<VendorLocation> locations;
    @Valid
    private List<VendorFileUrl> fileUrls;
    @Valid
    private List<VendorContractException> contractExceptions;
    @Valid
    private ReqMaximum truckMaximum;
    @Valid
    private ReqMaximum railMaximum;
    @NotBlank(message = "email:can not be empty")
    @Email(message = "email:" + "E-mail format is incorrect")
    @Length(min = 1, max=128, message = "email:String length cannot exceed {max}")
    private String email;
}
