package com.sysco.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sysco.utils.CustomJsonDateDeserializer;
import com.sysco.validator.YNPattern;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "supplier info")
public class SupplierInfo implements Serializable {
    @ApiModelProperty(hidden = true)
    private String uuid;
    @ApiModelProperty(notes = "supplier id, max length 128", required = true, example = "101829273")
    @NotBlank(message = "syscoSuvc:can not be empty")
    @Length(min = 1, max=128, message = "syscoSuvc:String length cannot exceed {max}")
    @Transient
    private String syscoSuvc;
    @ApiModelProperty(notes = "supplier effectiveDate, MM-dd-yyyy", required = true, example = "MM-dd-yyyy")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date effectiveDate;
    @ApiModelProperty(notes = "supplier name, max length 128", required = true, example = "Azar Nut")
    @NotBlank(message = "supplierName:can not be empty")
    @Length(min = 1, max=128, message = "supplierName:String length cannot exceed {max}")
    private String supplierName;
    @ApiModelProperty(notes = "corporate address, max length 128", required = true, example = "1800 Northwestern Dr")
    //@NotBlank(message = "corporateAddress:can not be empty")
    @Length(max=128, message = "corporateAddress:String length cannot exceed {max}")
    private String corporateAddress;
    @ApiModelProperty(notes = "supplier Chain Contact, max length 128", required = true, example = "Ray Hernandez")
    //@NotBlank(message = "supplierChainContact:can not be empty")
    @Length(max=128, message = "supplierChainContact:String length cannot exceed {max}")
    private String supplierChainContact;
    @ApiModelProperty(notes = "supplier Chain Manager ,max length 128", required = true, example = "Ray Hernandez")
    //@NotBlank(message = "supplierChainManager:can not be empty")
    @Length( max=128, message = "supplierChainManager:String length cannot exceed {max}")
    private String supplierChainManager;
    @ApiModelProperty(notes = "supplier phone, max length 30", required = true, example = "915-298-5475")
    //@NotBlank(message = "phone:can not be empty")
    @Length( max=30, message = "phone:String length cannot exceed {max}")
    private String phone;
    @ApiModelProperty(notes = "supplier email, max length 128", required = true, example = "rhernandez@scsolutionsinc.com")
    //@NotBlank(message = "email:can not be empty")
    @Email(message = "email:" + "E-mail format is incorrect")
    @Length( max=128, message = "email:String length cannot exceed {max}")
    private String email;
    @ApiModelProperty(notes = "supplier temperatureCode, Coodler or Frozen or Dry,  max length 30", required = true, example = "Coodler")
    //@NotBlank(message = "temperatureCode:can not be empty")
    @Length(max=30, message = "temperatureCode:String length cannot exceed {max}")
    private String temperatureCode;
    @ApiModelProperty(notes = "supplier bid Category, max length 128", required = true, example = "")
    //@NotBlank(message = "bidCategory:can not be empty")
    @Length( max=128, message = "bidCategory:String length cannot exceed {max}")
    private String bidCategory;
    @ApiModelProperty(notes = "supplier general Supplier Comments, max length 128", example = "ps : good day")
    //@Length(min = 1, max=128, message = "generalSupplierComments:String length cannot exceed {max}")
    private String generalSupplierComments;
    @ApiModelProperty(notes = "product costing based on FOB Basic or Delivered ,max length 30", required = true, example = "FOB")
    //@NotBlank(message = "productCosting:can not be empty")
    @Length(max=30, message = "generalSupplierComments:String length cannot exceed {max}")
    private String productCosting;
    @ApiModelProperty(notes = "lowest product cost,The value is 'N' or 'Y'", required = true, example = "Y")
    //@NotBlank(message = "lowestProductCost:can not be empty")
    @Length(max=1, message = "lowestProductCost:String length cannot exceed {max}")
    @YNPattern(message = "lowestProductCost:The value is 'N' or 'Y'")
    private String lowestProductCost;
    @ApiModelProperty(notes = "product Unit of Measure (Case,CWT,NWT,Cube), max length 30", required = true, example = "CWT")
    //@NotBlank(message = "productUom:can not be empty")
    @Length(max=30, message = "productUom:String length cannot exceed {max}")
    private String productUom;
    @ApiModelProperty(notes = "freight Unit of Measure (Case,CWT,NWT,Cube), max length 30", required = true, example = "Cube")
    //@NotBlank(message = "freightUom:can not be empty")
    @Length(max=30, message = "freightUom:String length cannot exceed {max}")
    private String freightUom;
    @ApiModelProperty(notes = "published Freight Rates, max length 30", required = true, example = "Bracket")
    //@NotBlank(message = "publishedFreightRates:can not be empty")
    @Length(max=30, message = "publishedFreightRates:String length cannot exceed {max}")
    private String publishedFreightRates;
    @ApiModelProperty(notes = "offer pickup allowances, The value is 'N' or 'Y'", required = true, example = "N")
    //@NotBlank(message = "pickupAllowances:can not be empty")
    @Length(max=1, message = "pickupAllowances:String length cannot exceed {max}")
    @YNPattern(message = "pickupAllowances:The value is 'N' or 'Y'")
    private String pickupAllowances;
    @ApiModelProperty(notes = "freight Different ,The value is 'N' or 'Y'", required = true, example = "Y")
    //@NotBlank(message = "freightDifferent:can not be empty")
    @Length(max=1, message = "freightDifferent:String length cannot exceed {max}")
    @YNPattern(message = "freightDifferent:The value is 'N' or 'Y'")
    private String freightDifferent;
    @ApiModelProperty(notes = "supplier Comments Freight, if 'freightDifferent' yes ,  max length 128", example = "ps:comments")
    @Length(max=128, message = "supplierCommentsFreight:String length cannot exceed {max}")
    private String supplierCommentsFreight;
    @ApiModelProperty(notes = "minimum order requirement for delivery(Use UOM list in freightUom), max length 128", required = true, example = "2000")
    //@NotBlank(message = "minimumDelivery:can not be empty")
    @Length(max=128, message = "minimumDelivery:String length cannot exceed {max}")
    private String minimumDelivery;
    @ApiModelProperty(notes = "minimum order requirement for pickup(Use UOM list in freightUom),  max length 128", required = true, example = "500")
    //@NotBlank(message = "minimumPickup:can not be empty")
    @Length(max=128, message = "minimumPickup:String length cannot exceed {max}")
    private String minimumPickup;
    @ApiModelProperty(notes = "the values in minimumDelivery and minimumPickup different, The value is 'N' or 'Y'", required = true, example = "Y")
    //@NotBlank(message = "minimumDifferent:can not be empty")
    @Length(max=1, message = "minimumDifferent:String length cannot exceed {max}")
    @YNPattern(message = "minimumDifferent:The value is 'N' or 'Y'")
    private String minimumDifferent;
    @ApiModelProperty(notes = "delivery Pickup Period(Annually,Weekly,Monthly...), max length 30", required = true, example = "Annually")
    //@NotBlank(message = "deliveryPickupPeriod:can not be empty")
    @Length(max=30, message = "deliveryPickupPeriod:String length cannot exceed {max}")
    private String deliveryPickupPeriod;
    @ApiModelProperty(notes = "fuel Surcharge Period(Annually,Weekly,Monthly...), max length 30", required = true, example = "Annually")
    //@NotBlank(message = "fuelSurchargePeriod:can not be empty")
    @Length(max=30, message = "fuelSurchargePeriod:String length cannot exceed {max}")
    private String fuelSurchargePeriod;
    @ApiModelProperty(notes = "additional Fees, The value is 'N' or 'Y", required = true, example = "Y")
    //@NotBlank(message = "additionalFees:can not be empty")
    @Length(max=1, message = "additionalFees:String length cannot exceed {max}")
    @YNPattern(message = "additionalFees:The value is 'N' or 'Y'")
    private String additionalFees;
    @ApiModelProperty(notes = "acknowledge compliance with Shipper load & Count, The value is 'N' or 'Y", required = true, example = "Y")
    @Length(max=1, message = "complianceShipperCount:String length cannot exceed {max}")
    @YNPattern(message = "complianceShipperCount:The value is 'N' or 'Y'")
    private String complianceShipperCount;
    @ApiModelProperty(notes = "current Pallet Program,  max length 30", required = true, example = "N")
    //@NotBlank(message = "currentPalletProgram:can not be empty")
    @Length( max=30, message = "currentPalletProgram:String length cannot exceed {max}")
    private String currentPalletProgram;
    @ApiModelProperty(notes = "supplier Comments Profile, if 'fuelSurchargePeriod' Others , max length 128", example = "ps:comments")
    @Length(max=128, message = "supplierCommentsProfile:String length cannot exceed {max}")
    private String supplierCommentsProfile;

    @Length(max=1, message = "enable:String length cannot exceed {max}")
    @YNPattern(message = "enable:The value is 'N' or 'Y'")
    private String enable;

    @ApiModelProperty(hidden = true)
    private BigDecimal truckloadMaximumWeight;
    @ApiModelProperty(hidden = true)
    private BigDecimal truckloadMaximumCubes;
    @ApiModelProperty(hidden = true)
    private BigDecimal truckloadMaximumCases;
    @ApiModelProperty(hidden = true)
    private BigDecimal truckloadMaximumPallets;
    @ApiModelProperty(hidden = true)
    private BigDecimal railMaximumWeight;
    @ApiModelProperty(hidden = true)
    private BigDecimal railMaximumCubes;
    @ApiModelProperty(hidden = true)
    private BigDecimal railMaximumCases;
    @ApiModelProperty(hidden = true)
    private BigDecimal railMaximumPallets;
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
    private Date lastUploadedDate;
    @ApiModelProperty(hidden = true)
    private Date lastDateOfFullReview;
    @ApiModelProperty(hidden = true)
    private Date nextReviewDueDate;
    @ApiModelProperty(hidden = true)
    private String deleted;
    @ApiModelProperty(hidden = true)
    private String createdBy;
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
    private String bracketsUom;
    @Length(max=128, message = "minimumPickup:String length cannot exceed {max}")
    private String businessCenter;
    @Length(max=1, message = "corpBilled:String length cannot exceed {max}")
    @YNPattern(message = "corpBilled:The value is 'N' or 'Y'")
    private String corpBilled;
    @Length(max=1, message = "trueFobSupplier:String length cannot exceed {max}")
    @YNPattern(message = "trueFobSupplier:The value is 'N' or 'Y'")
    private String trueFobSupplier;

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getCorporateAddress() {
        return corporateAddress;
    }

    public void setCorporateAddress(String corporateAddress) {
        this.corporateAddress = corporateAddress == null ? null : corporateAddress.trim();
    }

    public String getSupplierChainContact() {
        return supplierChainContact;
    }

    public void setSupplierChainContact(String supplierChainContact) {
        this.supplierChainContact = supplierChainContact == null ? null : supplierChainContact.trim();
    }

    public String getSupplierChainManager() {
        return supplierChainManager;
    }

    public void setSupplierChainManager(String supplierChainManager) {
        this.supplierChainManager = supplierChainManager == null ? null : supplierChainManager.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTemperatureCode() {
        return temperatureCode;
    }

    public void setTemperatureCode(String temperatureCode) {
        this.temperatureCode = temperatureCode == null ? null : temperatureCode.trim();
    }

    public String getBidCategory() {
        return bidCategory;
    }

    public void setBidCategory(String bidCategory) {
        this.bidCategory = bidCategory == null ? null : bidCategory.trim();
    }

    public String getGeneralSupplierComments() {
        return generalSupplierComments;
    }

    public void setGeneralSupplierComments(String generalSupplierComments) {
        this.generalSupplierComments = generalSupplierComments == null ? null : generalSupplierComments.trim();
    }

    public String getProductCosting() {
        return productCosting;
    }

    public void setProductCosting(String productCosting) {
        this.productCosting = productCosting == null ? null : productCosting.trim();
    }

    public String getLowestProductCost() {
        return lowestProductCost;
    }

    public void setLowestProductCost(String lowestProductCost) {
        this.lowestProductCost = lowestProductCost == null ? null : lowestProductCost.trim();
    }

    public String getProductUom() {
        return productUom;
    }

    public void setProductUom(String productUom) {
        this.productUom = productUom == null ? null : productUom.trim();
    }

    public String getFreightUom() {
        return freightUom;
    }

    public void setFreightUom(String freightUom) {
        this.freightUom = freightUom == null ? null : freightUom.trim();
    }

    public String getPublishedFreightRates() {
        return publishedFreightRates;
    }

    public void setPublishedFreightRates(String publishedFreightRates) {
        this.publishedFreightRates = publishedFreightRates == null ? null : publishedFreightRates.trim();
    }

    public String getPickupAllowances() {
        return pickupAllowances;
    }

    public void setPickupAllowances(String pickupAllowances) {
        this.pickupAllowances = pickupAllowances == null ? null : pickupAllowances.trim();
    }

    public String getFreightDifferent() {
        return freightDifferent;
    }

    public void setFreightDifferent(String freightDifferent) {
        this.freightDifferent = freightDifferent == null ? null : freightDifferent.trim();
    }

    public String getSupplierCommentsFreight() {
        return supplierCommentsFreight;
    }

    public void setSupplierCommentsFreight(String supplierCommentsFreight) {
        this.supplierCommentsFreight = supplierCommentsFreight == null ? null : supplierCommentsFreight.trim();
    }

    public String getMinimumDelivery() {
        return minimumDelivery;
    }

    public void setMinimumDelivery(String minimumDelivery) {
        this.minimumDelivery = minimumDelivery == null ? null : minimumDelivery.trim();
    }

    public String getMinimumPickup() {
        return minimumPickup;
    }

    public void setMinimumPickup(String minimumPickup) {
        this.minimumPickup = minimumPickup == null ? null : minimumPickup.trim();
    }

    public String getMinimumDifferent() {
        return minimumDifferent;
    }

    public void setMinimumDifferent(String minimumDifferent) {
        this.minimumDifferent = minimumDifferent == null ? null : minimumDifferent.trim();
    }

    public String getDeliveryPickupPeriod() {
        return deliveryPickupPeriod;
    }

    public void setDeliveryPickupPeriod(String deliveryPickupPeriod) {
        this.deliveryPickupPeriod = deliveryPickupPeriod == null ? null : deliveryPickupPeriod.trim();
    }

    public String getFuelSurchargePeriod() {
        return fuelSurchargePeriod;
    }

    public void setFuelSurchargePeriod(String fuelSurchargePeriod) {
        this.fuelSurchargePeriod = fuelSurchargePeriod == null ? null : fuelSurchargePeriod.trim();
    }

    public String getAdditionalFees() {
        return additionalFees;
    }

    public void setAdditionalFees(String additionalFees) {
        this.additionalFees = additionalFees == null ? null : additionalFees.trim();
    }

    public String getCurrentPalletProgram() {
        return currentPalletProgram;
    }

    public void setCurrentPalletProgram(String currentPalletProgram) {
        this.currentPalletProgram = currentPalletProgram == null ? null : currentPalletProgram.trim();
    }

    public String getSupplierCommentsProfile() {
        return supplierCommentsProfile;
    }

    public void setSupplierCommentsProfile(String supplierCommentsProfile) {
        this.supplierCommentsProfile = supplierCommentsProfile == null ? null : supplierCommentsProfile.trim();
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

    public BigDecimal getTruckloadMaximumWeight() {
        return truckloadMaximumWeight;
    }

    public void setTruckloadMaximumWeight(BigDecimal truckloadMaximumWeight) {
        this.truckloadMaximumWeight = truckloadMaximumWeight;
    }

    public BigDecimal getTruckloadMaximumCubes() {
        return truckloadMaximumCubes;
    }

    public void setTruckloadMaximumCubes(BigDecimal truckloadMaximumCubes) {
        this.truckloadMaximumCubes = truckloadMaximumCubes;
    }

    public BigDecimal getTruckloadMaximumCases() {
        return truckloadMaximumCases;
    }

    public void setTruckloadMaximumCases(BigDecimal truckloadMaximumCases) {
        this.truckloadMaximumCases = truckloadMaximumCases;
    }

    public BigDecimal getTruckloadMaximumPallets() {
        return truckloadMaximumPallets;
    }

    public void setTruckloadMaximumPallets(BigDecimal truckloadMaximumPallets) {
        this.truckloadMaximumPallets = truckloadMaximumPallets;
    }

    public BigDecimal getRailMaximumWeight() {
        return railMaximumWeight;
    }

    public void setRailMaximumWeight(BigDecimal railMaximumWeight) {
        this.railMaximumWeight = railMaximumWeight;
    }

    public BigDecimal getRailMaximumCubes() {
        return railMaximumCubes;
    }

    public void setRailMaximumCubes(BigDecimal railMaximumCubes) {
        this.railMaximumCubes = railMaximumCubes;
    }

    public BigDecimal getRailMaximumCases() {
        return railMaximumCases;
    }

    public void setRailMaximumCases(BigDecimal railMaximumCases) {
        this.railMaximumCases = railMaximumCases;
    }

    public BigDecimal getRailMaximumPallets() {
        return railMaximumPallets;
    }

    public void setRailMaximumPallets(BigDecimal railMaximumPallets) {
        this.railMaximumPallets = railMaximumPallets;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public String getComplianceShipperCount() {
        return complianceShipperCount;
    }

    public void setComplianceShipperCount(String complianceShipperCount) {
        this.complianceShipperCount = complianceShipperCount;
    }

    public Date getLastUploadedDate() {
        return lastUploadedDate;
    }

    public void setLastUploadedDate(Date lastUploadedDate) {
        this.lastUploadedDate = lastUploadedDate;
    }

    public Date getLastDateOfFullReview() {
        return lastDateOfFullReview;
    }

    public void setLastDateOfFullReview(Date lastDateOfFullReview) {
        this.lastDateOfFullReview = lastDateOfFullReview;
    }

    public Date getNextReviewDueDate() {
        return nextReviewDueDate;
    }

    public void setNextReviewDueDate(Date nextReviewDueDate) {
        this.nextReviewDueDate = nextReviewDueDate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getSyscoSuvc() {
        return syscoSuvc;
    }

    public void setSyscoSuvc(String syscoSuvc) {
        this.syscoSuvc = syscoSuvc;
    }

    public String getBusinessCenter() {
        return businessCenter;
    }

    public void setBusinessCenter(String businessCenter) {
        this.businessCenter = businessCenter;
    }

    public String getCorpBilled() {
        return corpBilled;
    }

    public void setCorpBilled(String corpBilled) {
        this.corpBilled = corpBilled;
    }

    public String getTrueFobSupplier() {
        return trueFobSupplier;
    }

    public void setTrueFobSupplier(String trueFobSupplier) {
        this.trueFobSupplier = trueFobSupplier;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}