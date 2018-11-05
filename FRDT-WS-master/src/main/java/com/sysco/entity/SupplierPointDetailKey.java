package com.sysco.entity;

import java.io.Serializable;

public class SupplierPointDetailKey implements Serializable {
    private Integer shipPointNo;

    private String uuid;

    private String opcoNo;

    private String type;

    private static final long serialVersionUID = 1L;

    public Integer getShipPointNo() {
        return shipPointNo;
    }

    public void setShipPointNo(Integer shipPointNo) {
        this.shipPointNo = shipPointNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpcoNo() {
        return opcoNo;
    }

    public void setOpcoNo(String opcoNo) {
        this.opcoNo = opcoNo == null ? null : opcoNo.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}