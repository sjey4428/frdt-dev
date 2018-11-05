package com.sysco.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

public class SupplierContractExceptionKey implements Serializable {
    @ApiModelProperty(hidden = true)
    private Integer supplierContractNo;
    @ApiModelProperty(hidden = true)
    private String uuid;
    @ApiModelProperty(notes = "supplier id, max length 128", required = true, example = "101829273")
    @NotBlank(message = "syscoSuvc:can not be empty")
    @Length(min = 1, max=128, message = "syscoSuvc:String length cannot exceed {max}")
    @Transient
    private String syscoSuvc;

    public String getSyscoSuvc() {
        return syscoSuvc;
    }

    public void setSyscoSuvc(String syscoSuvc) {
        this.syscoSuvc = syscoSuvc;
    }

    private static final long serialVersionUID = 1L;

    public Integer getSupplierContractNo() {
        return supplierContractNo;
    }

    public void setSupplierContractNo(Integer supplierContractNo) {
        this.supplierContractNo = supplierContractNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}