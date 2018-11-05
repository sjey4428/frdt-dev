package com.sysco.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;


/**
 * Created by chunyu.chen on 9/12/2018.
 */
@Data
@ApiModel(description = "supplier info")
public class DateOfReview {
    @ApiModelProperty(notes = "supplier name, max length 128", required = true, example = "test")
    @NotBlank(message = "supplierName:can not be empty")
    @Length(min = 1, max=128, message = "supplierName:String length cannot exceed {max}")
    private String supplierName;
    @Valid
    @ApiModelProperty(example = "MM-dd-yy")
    private String lastDateOfFullReview;
    @Valid
    @ApiModelProperty(example = "MM-dd-yy")
    private String nextReviewDueDate;
}
