package com.sysco.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightUpdateDetailKey implements Serializable {
    private String uuid;

    private Date effectiveDate;

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}