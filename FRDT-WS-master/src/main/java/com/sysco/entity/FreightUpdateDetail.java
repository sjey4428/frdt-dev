package com.sysco.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FreightUpdateDetail extends FreightUpdateDetailKey implements Serializable {
    private BigDecimal pickupAllowance;

    private BigDecimal bracketValue;

    private Integer bracketType;

    private BigDecimal surcharge;

    private BigDecimal puPercent;

    private Date updateDate;

    private Date createdDate;

    private String createdTime;

    private Date modifiedDate;

    private String modifiedTime;

    private String modifiedBy;

    private static final long serialVersionUID = 1L;

    public BigDecimal getPickupAllowance() {
        return pickupAllowance;
    }

    public void setPickupAllowance(BigDecimal pickupAllowance) {
        this.pickupAllowance = pickupAllowance;
    }

    public BigDecimal getBracketValue() {
        return bracketValue;
    }

    public void setBracketValue(BigDecimal bracketValue) {
        this.bracketValue = bracketValue;
    }

    public Integer getBracketType() {
        return bracketType;
    }

    public void setBracketType(Integer bracketType) {
        this.bracketType = bracketType;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    public BigDecimal getPuPercent() {
        return puPercent;
    }

    public void setPuPercent(BigDecimal puPercent) {
        this.puPercent = puPercent;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
}