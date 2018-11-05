package com.sysco.request;

import lombok.Data;

import java.util.List;


/**
 * Created by james.zhu on 2018/9/19.
 */
@Data
public class ReqUpdateFreightRate {
    private String vendorNumber;

    private String vendorName;

    private ReqBracket brackets;

    private String bracketUOM;

    private List<ReqShipOpco> shipPoints;

    private List<ReqContractException> contractExceptions;

}
