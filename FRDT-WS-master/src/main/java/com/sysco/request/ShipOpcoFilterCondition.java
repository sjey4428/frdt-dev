package com.sysco.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by james.zhu on 2018/9/19.
 */
@Data
public class ShipOpcoFilterCondition {
    @ApiModelProperty(required = true)
    private Integer pageNum;
    @ApiModelProperty(required = true)
    private Integer pageSize;

    private String sort;
    private String order;

    @ApiModelProperty(required = true)
    private String vendorNumber;
    private String opcoNo;

    @ApiModelProperty(hidden = true)
    private String[] opcoList;
    @ApiModelProperty(hidden = true)
    private String uuid;
    @ApiModelProperty(hidden = true)
    private String type;
}
