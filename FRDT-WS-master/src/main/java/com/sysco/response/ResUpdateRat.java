package com.sysco.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sysco.utils.CustomJsonDateDeserializer;
import lombok.Data;

import java.util.Date;


/**
 * Created by james.zhu on 2018/10/16.
 */
@Data
public class ResUpdateRat {
    private String pickupAllowance;

    private String bracketValue;

    private String bracketType;

    private String surcharge;

    private String puPercentage;

    @JsonFormat(pattern = "MM-dd-yy")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date effectiveDate;
}
