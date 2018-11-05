package com.sysco.response;

import lombok.Data;

import java.util.List;


/**
 * Created by james.zhu on 2018/9/19.
 */
@Data
public class ResShipOpcoDetail {
    private String vendorNumber;

    private String vendorName;

    private ResBracket brackets;

    private String bracketUOM;

    private List<ResShipOpco> shipPoints;

    private List<ResContractException> contractExceptions;

}
