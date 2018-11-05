package com.sysco.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Created by chunyu.chen on 9/14/2018.
 */
@Data
@ApiModel(description = "SCS supplier info parameter")
public class SCSFilterCondition {

    @ApiModelProperty(required = true)
    private Integer pageNum;
    @ApiModelProperty(required = true)
    private Integer pageSize;
    @ApiModelProperty(notes = "supplier name, max length 128",example = "Azar Nut")
    private String vendorName;
    private String supplierChainManager;
    private String businessCenter;
    private String corpBilled;
    private String trueFobSupplier;
    @ApiModelProperty(value = "beginningTime of supplier lastUploadedDate,mm-dd-yy", example = "mm-dd-yy")
    private String lastUploadedDateBegin;
    @ApiModelProperty(value = "endingTime of supplier lastUploadedDate,mm-dd-yy", example = "mm-dd-yy")
    private String lastUploadedDateEnd;
    @ApiModelProperty(value = "beginningTime of supplier lastDateOfFullReview,mm-dd-yy", example = "mm-dd-yy")
    private String lastDateOfFullReviewBegin;
    @ApiModelProperty(value = "endingTime of supplier lastDateOfFullReview,mm-dd-yy", example = "mm-dd-yy")
    private String lastDateOfFullReviewEnd;
    @ApiModelProperty(value = "beginningTime of supplier nextReviewDueDate,mm-dd-yy",  example = "mm-dd-yy")
    private String nextReviewDueDateBegin;
    @ApiModelProperty(value = "endingTime of supplier nextReviewDueDate,mm-dd-yy",  example = "mm-dd-yy")
    private String nextReviewDueDateEnd;
    @ApiModelProperty(value = "supplier_name,supplier_chain_manager,business_center,corp_billed,true_fob_supplier,last_uploaded_date,last_date_of_full_review,next_review_due_date")
    private String sort;
    @ApiModelProperty(value = "ASC,DESC")
    private String order;


}
