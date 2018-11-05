package com.sysco.response;

import lombok.Data;

/**
 * Created by james.zhu on 2018/9/26.
 */
@Data
public class ResShipOpco {
    private Integer _id;
    
    private String opcoNo;

    private String opcoName;

    private String vendorNumber;

    private String vendorName;

    private String type;

    private String city;

    private String state;

    private String zipCode;

    private Integer shipPoint;

    private String pickupAllowance;

    private String pickupAllowanceUom;

    private String freightRateUom;

    private String contractException;

    private ResBracket freightRate;

    private ResUpdateRat updateRat;
}
