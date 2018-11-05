package com.sysco.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierPointDetail extends SupplierPointDetailKey implements Serializable {
    private String opcoName;

    private String city;

    private String state;

    private String zipCode;

    private String syscoCurrentlyPicksup;

    private BigDecimal distanceInMiles;

    private String tempCode;

    private BigDecimal linehaulRate;

    private BigDecimal fuelSurchargeRate;

    private BigDecimal stopCharge;

    private BigDecimal unloadCharge;

    private String pickupAllowanceUom;

    private BigDecimal pickupAllowance;

    private String freightRateUom;

    private BigDecimal bracket1;

    private BigDecimal bracket2;

    private BigDecimal bracket3;

    private BigDecimal bracket4;

    private BigDecimal bracket5;

    private BigDecimal bracket6;

    private BigDecimal bracket7;

    private BigDecimal bracket8;

    private BigDecimal bracket9;

    private BigDecimal bracket10;

    private String bracketsUom;

    private Date createdDate;

    private String createdTime;

    private Date modifiedDate;

    private String modifiedTime;

    private String modifiedBy;

    private String freightUpdateUuid;

    private static final long serialVersionUID = 1L;

    public String getOpcoName() {
        return opcoName;
    }

    public void setOpcoName(String opcoName) {
        this.opcoName = opcoName == null ? null : opcoName.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getSyscoCurrentlyPicksup() {
        return syscoCurrentlyPicksup;
    }

    public void setSyscoCurrentlyPicksup(String syscoCurrentlyPicksup) {
        this.syscoCurrentlyPicksup = syscoCurrentlyPicksup == null ? null : syscoCurrentlyPicksup.trim();
    }

    public BigDecimal getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(BigDecimal distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode == null ? null : tempCode.trim();
    }

    public BigDecimal getLinehaulRate() {
        return linehaulRate;
    }

    public void setLinehaulRate(BigDecimal linehaulRate) {
        this.linehaulRate = linehaulRate;
    }

    public BigDecimal getFuelSurchargeRate() {
        return fuelSurchargeRate;
    }

    public void setFuelSurchargeRate(BigDecimal fuelSurchargeRate) {
        this.fuelSurchargeRate = fuelSurchargeRate;
    }

    public BigDecimal getStopCharge() {
        return stopCharge;
    }

    public void setStopCharge(BigDecimal stopCharge) {
        this.stopCharge = stopCharge;
    }

    public BigDecimal getUnloadCharge() {
        return unloadCharge;
    }

    public void setUnloadCharge(BigDecimal unloadCharge) {
        this.unloadCharge = unloadCharge;
    }


    public BigDecimal getPickupAllowance() {
        return pickupAllowance;
    }

    public void setPickupAllowance(BigDecimal pickupAllowance) {
        this.pickupAllowance = pickupAllowance;
    }


    public BigDecimal getBracket1() {
        return bracket1;
    }

    public void setBracket1(BigDecimal bracket1) {
        this.bracket1 = bracket1;
    }

    public BigDecimal getBracket2() {
        return bracket2;
    }

    public void setBracket2(BigDecimal bracket2) {
        this.bracket2 = bracket2;
    }

    public BigDecimal getBracket3() {
        return bracket3;
    }

    public void setBracket3(BigDecimal bracket3) {
        this.bracket3 = bracket3;
    }

    public BigDecimal getBracket4() {
        return bracket4;
    }

    public void setBracket4(BigDecimal bracket4) {
        this.bracket4 = bracket4;
    }

    public BigDecimal getBracket5() {
        return bracket5;
    }

    public void setBracket5(BigDecimal bracket5) {
        this.bracket5 = bracket5;
    }

    public BigDecimal getBracket6() {
        return bracket6;
    }

    public void setBracket6(BigDecimal bracket6) {
        this.bracket6 = bracket6;
    }

    public BigDecimal getBracket7() {
        return bracket7;
    }

    public void setBracket7(BigDecimal bracket7) {
        this.bracket7 = bracket7;
    }

    public BigDecimal getBracket8() {
        return bracket8;
    }

    public void setBracket8(BigDecimal bracket8) {
        this.bracket8 = bracket8;
    }

    public BigDecimal getBracket9() {
        return bracket9;
    }

    public void setBracket9(BigDecimal bracket9) {
        this.bracket9 = bracket9;
    }

    public BigDecimal getBracket10() {
        return bracket10;
    }

    public void setBracket10(BigDecimal bracket10) {
        this.bracket10 = bracket10;
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

    public String getBracketsUom() {
        return bracketsUom;
    }

    public void setBracketsUom(String bracketsUom) {
        this.bracketsUom = bracketsUom;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public String getFreightUpdateUuid() {
        return freightUpdateUuid;
    }

    public void setFreightUpdateUuid(String freightUpdateUuid) {
        this.freightUpdateUuid = freightUpdateUuid;
    }
}