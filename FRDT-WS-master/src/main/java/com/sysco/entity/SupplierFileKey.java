package com.sysco.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
@ApiModel(description = "supplier file url")
public class SupplierFileKey implements Serializable {
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

    @ApiModelProperty(notes = "supplier fileUrl,max length 128", required = true, example = "frdt_resend.pptx")
    @NotBlank(message = "fileUrl:can not be empty")
    @Length(min = 1, max=128, message = "fileUrl:String length cannot exceed {max}")
    private String fileUrl;

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }
}