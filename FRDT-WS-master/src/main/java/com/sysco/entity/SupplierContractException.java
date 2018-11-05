package com.sysco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

public class SupplierContractException extends SupplierContractExceptionKey implements Serializable {
    @ApiModelProperty(notes = "supplier contract Exception, max length 128", example = "contract Exception")
    @Length(max=128, message = "contractException1:String length cannot exceed {max}")
    private String contractException;

    @ApiModelProperty(hidden = true)
    private Date createdDate;
    @ApiModelProperty(hidden = true)
    private String createdTime;
    @ApiModelProperty(hidden = true)
    private Date modifiedDate;
    @ApiModelProperty(hidden = true)
    private String modifiedTime;
    @ApiModelProperty(hidden = true)
    private String modifiedBy;

    @ApiModelProperty(hidden = true)
    private Date effectiveDate;
    @ApiModelProperty(hidden = true)
    private String deleted;

    @ApiModelProperty(hidden = true)
    private String bracket1Header;
    @ApiModelProperty(hidden = true)
    private String bracket2Header;
    @ApiModelProperty(hidden = true)
    private String bracket3Header;
    @ApiModelProperty(hidden = true)
    private String bracket4Header;
    @ApiModelProperty(hidden = true)
    private String bracket5Header;
    @ApiModelProperty(hidden = true)
    private String bracket6Header;
    @ApiModelProperty(hidden = true)
    private String bracket7Header;
    @ApiModelProperty(hidden = true)
    private String bracket8Header;

    @ApiModelProperty(hidden = true)
    private String pickupAllowanceUom;
    @ApiModelProperty(hidden = true)
    private String freightRateUom;
    @ApiModelProperty(hidden = true)
    private String bracketsUom;

    public String getPickupAllowanceUom() {
        return pickupAllowanceUom;
    }

    public void setPickupAllowanceUom(String pickupAllowanceUom) {
        this.pickupAllowanceUom = pickupAllowanceUom;
    }

    public String getFreightRateUom() {
        return freightRateUom;
    }

    public void setFreightRateUom(String freightRateUom) {
        this.freightRateUom = freightRateUom;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    private static final long serialVersionUID = 1L;

    public String getContractException() {
        return contractException;
    }

    public void setContractException(String contractException) {
        this.contractException = contractException == null ? null : contractException.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime == null ? null : createdTime.trim();
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime == null ? null : modifiedTime.trim();
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public String getBracket1Header() {
        return bracket1Header;
    }

    public void setBracket1Header(String bracket1Header) {
        this.bracket1Header = bracket1Header;
    }

    public String getBracket2Header() {
        return bracket2Header;
    }

    public void setBracket2Header(String bracket2Header) {
        this.bracket2Header = bracket2Header;
    }

    public String getBracket3Header() {
        return bracket3Header;
    }

    public void setBracket3Header(String bracket3Header) {
        this.bracket3Header = bracket3Header;
    }

    public String getBracket4Header() {
        return bracket4Header;
    }

    public void setBracket4Header(String bracket4Header) {
        this.bracket4Header = bracket4Header;
    }

    public String getBracket5Header() {
        return bracket5Header;
    }

    public void setBracket5Header(String bracket5Header) {
        this.bracket5Header = bracket5Header;
    }

    public String getBracket6Header() {
        return bracket6Header;
    }

    public void setBracket6Header(String bracket6Header) {
        this.bracket6Header = bracket6Header;
    }

    public String getBracket7Header() {
        return bracket7Header;
    }

    public void setBracket7Header(String bracket7Header) {
        this.bracket7Header = bracket7Header;
    }

    public String getBracket8Header() {
        return bracket8Header;
    }

    public void setBracket8Header(String bracket8Header) {
        this.bracket8Header = bracket8Header;
    }

    public String getBracketsUom() {
        return bracketsUom;
    }

    public void setBracketsUom(String bracketsUom) {
        this.bracketsUom = bracketsUom;
    }
}