package com.sysco.entity;

import java.io.Serializable;

public class SupplierSyscoSuvc extends SupplierSyscoSuvcKey implements Serializable {
    private String deleted;

    private static final long serialVersionUID = 1L;

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
}