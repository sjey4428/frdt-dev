package com.sysco.entity;

import lombok.Data;

/**
 * Created by chunyu.chen on 9/26/2018.
 */
@Data
public class VendorContractException {
    private Integer id;
    private String syscoSuvc;
    private String contractException;
    //U(updated),  D(deleted), N(newly added)
    private String status;
}
