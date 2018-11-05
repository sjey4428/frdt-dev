package com.sysco.request;

import lombok.Data;

import java.util.List;

/**
 * Created by james.zhu on 2018/9/26.
 */
@Data
public class ReqContractException {
    private ReqBracket brackets;

    private String contractExceptionName;

    private List<ReqShipOpco> contractException;
}
