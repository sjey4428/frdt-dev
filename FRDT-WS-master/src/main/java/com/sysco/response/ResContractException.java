package com.sysco.response;

import lombok.Data;

import java.util.List;

/**
 * Created by james.zhu on 2018/9/26.
 */
@Data
public class ResContractException {
    private ResBracket brackets;

    private String contractExceptionName;

    private List<ResShipOpco> contractException;
}
