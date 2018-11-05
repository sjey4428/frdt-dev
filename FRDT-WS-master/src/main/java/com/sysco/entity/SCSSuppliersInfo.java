package com.sysco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by chunyu.chen on 9/11/2018.
 */
@Data
public class SCSSuppliersInfo {
    private String vendorId;
    private String vendorName;
    private String supplierChainManager;
    @JsonFormat(pattern = "MM-dd-yy")
    private Date lastUploadedDate;
    @JsonFormat(pattern = "MM-dd-yy")
    private Date lastDateOfFullReview;
    @JsonFormat(pattern = "MM-dd-yy")
    private Date nextReviewDueDate;
    private String businessCenter;
    private String corpBilled;
    private String trueFobSupplier;
    private List<Documents> docDetails;
}
