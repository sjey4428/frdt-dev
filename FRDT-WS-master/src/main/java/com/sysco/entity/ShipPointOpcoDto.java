package com.sysco.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by james.zhu on 2018/9/19.
 */
@Data
public class ShipPointOpcoDto {
    private Integer shipPointNo;

    private String vendorNumber;

    private String vendorName;

    private String opcoNo;

    private String opcoName;

    private String type;

    private String city;

    private String state;

    private String zipCode;

    private String syscoCurrentlyPicksup;

    private BigDecimal distanceInMiles;

    private String tempCode;

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

    private BigDecimal updatePickupAllowance;

    private BigDecimal bracketValue;

    private Integer bracketType;

    private BigDecimal surcharge;

    private BigDecimal puPercentage;

    private String freightUpdateUuid;

    private Date freightEffectiveDate;

    //ship point
    private String physicalAddressZone;

    private String pointCity;

    private String pointState;

    private String pointZipCode;

    private String pointDcPlant;

    private String sourcedProduct;

    private String railFacilities;

    private String dropTrailer;

    private Date pointEffectiveDate;

    private String pointDeleted;

    private String pointPickupAllowanceUom;

    private String pointFreightRateUom;

    //contract
    private String contractException;

    private Date contractEffectiveDate;

    private String contractDeleted;

    private String contractBracket1Header;

    private String contractBracket2Header;

    private String contractBracket3Header;

    private String contractBracket4Header;

    private String contractBracket5Header;

    private String contractBracket6Header;

    private String contractBracket7Header;

    private String contractBracket8Header;

    private String contractPickupAllowanceUom;

    private String contractFreightRateUom;

    private String contractBracketsUom;


    //vendor info
    private String corporateAddress;

    private String supplierChainContact;

    private String supplierChainManager;

    private String phone;

    private String email;

    private Date effectiveDate;

    private String temperatureCode;

    private String bidCategory;

    private String generalSupplierComments;

    private String productCosting;

    private String lowestProductCost;

    private String productUom;

    private String freightUom;

    private String bracket1Header;

    private String bracket2Header;

    private String bracket3Header;

    private String bracket4Header;

    private String bracket5Header;

    private String bracket6Header;

    private String bracket7Header;

    private String bracket8Header;

    private String bracketsUom;

    private String publishedFreightRates;

    private String pickupAllowances;

    private String freightDifferent;

    private String supplierCommentsFreight;

    private String minimumDelivery;

    private String minimumPickup;

    private String minimumDifferent;

    private String deliveryPickupPeriod;

    private String fuelSurchargePeriod;

    private String additionalFees;

    private String complianceShipperCount;

    private String currentPalletProgram;

    private String supplierCommentsProfile;

    private BigDecimal truckloadMaximumWeight;

    private BigDecimal truckloadMaximumCubes;

    private BigDecimal truckloadMaximumCases;

    private BigDecimal truckloadMaximumPallets;

    private BigDecimal railMaximumWeight;

    private BigDecimal railMaximumCubes;

    private BigDecimal railMaximumCases;

    private BigDecimal railMaximumPallets;

    private Date lastUploadedDate;

    private Date lastDateOfFullReview;

    private Date nextReviewDueDate;

    private String deleted;
}
