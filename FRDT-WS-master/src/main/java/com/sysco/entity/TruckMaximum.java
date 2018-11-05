package com.sysco.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by chunyu.chen on 9/26/2018.
 */
@Data
public class TruckMaximum {
    private BigDecimal cases;
    private BigDecimal cubes;
    private BigDecimal pallets;
    private BigDecimal weight;
}
