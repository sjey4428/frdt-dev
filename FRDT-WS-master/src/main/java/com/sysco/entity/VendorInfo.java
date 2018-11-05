package com.sysco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by chunyu.chen on 9/26/2018.
 */
@Data
public class VendorInfo {
    private String vendorName;
    private String vendorNumber;
    private String freightUom;
    @JsonFormat(pattern = "MM-dd-yy")
    private Date effectiveDate;
    private String additionalFees;
    private String bidCategory;
    private String complianceShipperCount;
    private String corporateAddress;
    private String currentPalletProgram;
    private String deliveryPickupPeriod;
    private String email;
    private String temperatureCode;
    private String freightDifferent;
    private String fuelSurchargePeriod;
    private String generalSupplierComments;
    private String lowestProductCost;
    private String minimumDelivery;
    private String minimumDifferent;
    private String minimumPickup;
    private String Phone;
    private String pickupAllowances;
    private String productCosting;
    private String productUom;
    private String publishedFreightRates;
    private String supplierChainContact;
    private String supplierChainManager;
    private String supplierCommentsFreight;
    private String supplierCommentsProfile;
    private RailMaximum railMaximum;
    private TruckMaximum truckMaximum;
    private List<VendorContractException> contractExceptions;
    private List<VendorLocation> locations;
    private List<VendorFileUrl> fileUrls;
}
