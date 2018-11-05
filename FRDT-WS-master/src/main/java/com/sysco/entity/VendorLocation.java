package com.sysco.entity;

import lombok.Data;

/**
 * Created by chunyu.chen on 9/26/2018.
 */
@Data
public class VendorLocation {
    private Integer id;
    private String city;
    private String dcPlant;
    private String dropTrailer;
    private String physicalAddressZone;
    private String railFacilities;
    private String sourcedProduct;
    private String state;
    private String syscoSuvc;
    private String zipCode;
    //U(updated),  D(deleted), N(newly added)
    private String status;
}
