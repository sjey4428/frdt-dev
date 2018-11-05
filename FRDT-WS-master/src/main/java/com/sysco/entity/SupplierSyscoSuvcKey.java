package com.sysco.entity;

import java.io.Serializable;

public class SupplierSyscoSuvcKey implements Serializable {
    private String syscoSuvc;

    private String refUuid;

    private static final long serialVersionUID = 1L;

    public String getSyscoSuvc() {
        return syscoSuvc;
    }

    public void setSyscoSuvc(String syscoSuvc) {
        this.syscoSuvc = syscoSuvc == null ? null : syscoSuvc.trim();
    }

    public String getRefUuid() {
        return refUuid;
    }

    public void setRefUuid(String refUuid) {
        this.refUuid = refUuid == null ? null : refUuid.trim();
    }
}