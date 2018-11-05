package com.sysco.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * Created by james.zhu on 2018/9/7.
 */
public class ReqMaximum {
    @ApiModelProperty(notes = " Maximum Weight")
    @DecimalMin(value = "0", message = "This value must be an integer or a decimal")
    private BigDecimal weight;
    @ApiModelProperty(notes = " Maximum Cubes")
    @DecimalMin(value = "0", message = "This value must be an integer or a decimal")
    private BigDecimal cubes;
    @ApiModelProperty(notes = " Maximum Cases")
    @DecimalMin(value = "0", message = "This value must be an integer or a decimal")
    private BigDecimal cases;
    @ApiModelProperty(notes = " Maximum Pallets")
    @DecimalMin(value = "0", message = "This value must be an integer or a decimal")
    private BigDecimal pallets;

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCubes() {
        return cubes;
    }

    public void setCubes(BigDecimal cubes) {
        this.cubes = cubes;
    }

    public BigDecimal getCases() {
        return cases;
    }

    public void setCases(BigDecimal cases) {
        this.cases = cases;
    }

    public BigDecimal getPallets() {
        return pallets;
    }

    public void setPallets(BigDecimal pallets) {
        this.pallets = pallets;
    }
}
