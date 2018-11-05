package com.sysco.request;

import com.sysco.entity.*;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by james.zhu on 2018/8/22.
 */
@ApiModel(description = "create supplier request body")
public class ReqSupplier {
    @Valid
    private SupplierInfo info;
    @Valid
    private List<SupplierLocation> locations;
    @Valid
    private List<SupplierFileKey> fileUrls;
    @Valid
    private List<SupplierContractException> contractExceptions;
    @Valid
    private ReqMaximum truckMaximum;
    @Valid
    private ReqMaximum railMaximum;

    @NotBlank(message = "email:can not be empty")
    @Email(message = "email:" + "E-mail format is incorrect")
    @Length(min = 1, max=128, message = "email:String length cannot exceed {max}")
    private String email;

    public SupplierInfo getInfo() {
        return info;
    }

    public void setInfo(SupplierInfo info) {
        this.info = info;
    }

    public List<SupplierLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<SupplierLocation> locations) {
        this.locations = locations;
    }

    public List<SupplierFileKey> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<SupplierFileKey> fileUrls) {
        this.fileUrls = fileUrls;
    }

    public List<SupplierContractException> getContractExceptions() {
        return contractExceptions;
    }

    public void setContractExceptions(List<SupplierContractException> contractExceptions) {
        this.contractExceptions = contractExceptions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ReqMaximum getTruckMaximum() {
        return truckMaximum;
    }

    public void setTruckMaximum(ReqMaximum truckMaximum) {
        this.truckMaximum = truckMaximum;
    }

    public ReqMaximum getRailMaximum() {
        return railMaximum;
    }

    public void setRailMaximum(ReqMaximum railMaximum) {
        this.railMaximum = railMaximum;
    }
}
