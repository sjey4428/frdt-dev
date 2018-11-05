package com.sysco.request;

import com.sysco.response.ResUpdateRat;
import lombok.Data;

/**
 * Created by james.zhu on 2018/9/26.
 */
@Data
public class ReqShipOpco {
    private String opco;

    private String city;

    private String state;

    private String zipCode;

    private Integer shipPoint;

    private String pickupAllowance;

    private String pickupAllowanceUom;

    private String freightRateUom;

    private ReqBracket freightRate;

    private ResUpdateRat updateRat;
}
