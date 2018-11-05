package com.sysco.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

public class SupplierLocationKey implements Serializable {
    @ApiModelProperty(hidden = true)
    private Integer shipPointNo;
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

    public Integer getShipPointNo() {
        return shipPointNo;
    }

    public void setShipPointNo(Integer shipPointNo) {
        this.shipPointNo = shipPointNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}