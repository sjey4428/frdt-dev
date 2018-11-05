package com.sysco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class SupplierLog implements Serializable {
    private String uuid;

    private String userId;

    private String userName;

    private String logHeader;

    private String logDesc;

    private String verdorUuid;

    private String verdorName;

    @JsonFormat(pattern = "MM-dd-yy HH:mm:ss")
    private Date operTime;

    private String operResult;

    private static final long serialVersionUID = 1L;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLogHeader() {
        return logHeader;
    }

    public void setLogHeader(String logHeader) {
        this.logHeader = logHeader == null ? null : logHeader.trim();
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc == null ? null : logDesc.trim();
    }

    public String getVerdorUuid() {
        return verdorUuid;
    }

    public void setVerdorUuid(String verdorUuid) {
        this.verdorUuid = verdorUuid == null ? null : verdorUuid.trim();
    }

    public String getVerdorName() {
        return verdorName;
    }

    public void setVerdorName(String verdorName) {
        this.verdorName = verdorName == null ? null : verdorName.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOperResult() {
        return operResult;
    }

    public void setOperResult(String operResult) {
        this.operResult = operResult == null ? null : operResult.trim();
    }
}