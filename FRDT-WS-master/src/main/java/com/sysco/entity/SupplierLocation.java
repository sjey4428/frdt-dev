package com.sysco.entity;

import com.sysco.validator.YNPattern;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "supplier location")
public class SupplierLocation extends SupplierLocationKey implements Serializable {
    @ApiModelProperty(value = "physical Address Zone ,max length 128", required = true, example = "1390 Enclave Pkwy")
    //@NotBlank(message = "physicalAddressZone:can not be empty")
    @Length( max=128, message = "physicalAddressZone:String length cannot exceed {max}")
    private String physicalAddressZone;
    @ApiModelProperty(value = "city,max length 30", required = true, example = "Houston")
    //@NotBlank(message = "city:can not be empty")
    @Length( max=30, message = "city:String length cannot exceed {max}")
    private String city;
    @ApiModelProperty(value = "state,max length 30", required = true, example = "TX")
    //@NotBlank(message = "state:can not be empty")
    @Length( max=30, message = "state:String length cannot exceed {max}")
    private String state;
    @ApiModelProperty(value = "zipCode,max length 30", required = true, example = "77077")
    //@NotBlank(message = "zipCode:can not be empty")
    @Length( max=30, message = "zipCode:String length cannot exceed {max}")
    private String zipCode;
    @ApiModelProperty(value = "DC/Plant,max length 30", required = true, example = "Y")
    //@NotBlank(message = "dcPlant:can not be empty")
    @Length( max=30, message = "dcPlant:String length cannot exceed {max}")
    private String dcPlant;
    @ApiModelProperty(value = "sourced Product, The value is 'N' or 'Y", required = true, example = "Y")
    //@NotBlank(message = "sourcedProduct:can not be empty")
    @Length( max=1, message = "sourcedProduct:String length cannot exceed {max}")
    @YNPattern(message = "sourcedProduct:The value is 'N' or 'Y'")
    private String sourcedProduct;
    @ApiModelProperty(value = "rail Facilities, The value is 'N' or 'Y", required = true, example = "Y")
    //@NotBlank(message = "railFacilities:can not be empty")
    @Length( max=1, message = "railFacilities:String length cannot exceed {max}")
    @YNPattern(message = "railFacilities:The value is 'N' or 'Y'")
    private String railFacilities;
    @ApiModelProperty(value = "drop Trailer, The value is 'N' or 'Y", required = true, example = "Y")
    //@NotBlank(message = "dropTrailer:can not be empty")
    @Length( max=1, message = "dropTrailer:String length cannot exceed {max}")
    @YNPattern(message = "dropTrailer:The value is 'N' or 'Y'")
    private String dropTrailer;
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
    private String pickupAllowanceUom;
    @ApiModelProperty(hidden = true)
    private String freightRateUom;

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

    public String getPhysicalAddressZone() {
        return physicalAddressZone;
    }

    public void setPhysicalAddressZone(String physicalAddressZone) {
        this.physicalAddressZone = physicalAddressZone == null ? null : physicalAddressZone.trim();
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

    public String getDcPlant() {
        return dcPlant;
    }

    public void setDcPlant(String dcPlant) {
        this.dcPlant = dcPlant == null ? null : dcPlant.trim();
    }

    public String getSourcedProduct() {
        return sourcedProduct;
    }

    public void setSourcedProduct(String sourcedProduct) {
        this.sourcedProduct = sourcedProduct == null ? null : sourcedProduct.trim();
    }

    public String getRailFacilities() {
        return railFacilities;
    }

    public void setRailFacilities(String railFacilities) {
        this.railFacilities = railFacilities == null ? null : railFacilities.trim();
    }

    public String getDropTrailer() {
        return dropTrailer;
    }

    public void setDropTrailer(String dropTrailer) {
        this.dropTrailer = dropTrailer == null ? null : dropTrailer.trim();
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

}