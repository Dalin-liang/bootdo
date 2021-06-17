package com.bootdo.app.base;

import java.util.Date;

public class APPEventInfo {

    /**
     * 上报人姓名
     */
    private String repName;

    /**
     * 上报人电话
     */
    private String repPhone;

    /**
     * 事件地址
     */
    private String address;

    /**
     * 事件描述
     */
    private String eventDesc;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件经纬度
     */
    private String latLon;

    /**
     * 上报部门
     */
    private String repDept;

    /**
     * 接收人
     */
    private String deptPerson;

    /**
     * 上报时间
     */
    private Date repTime;

    /**
     * 预警级别
     */
    private String warnLevel;

    /**
     * 预警类型
     */
    private String warnType;


    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepPhone() {
        return repPhone;
    }

    public void setRepPhone(String repPhone) {
        this.repPhone = repPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getRepDept() {
        return repDept;
    }

    public void setRepDept(String repDept) {
        this.repDept = repDept;
    }

    public String getDeptPerson() {
        return deptPerson;
    }

    public void setDeptPerson(String deptPerson) {
        this.deptPerson = deptPerson;
    }

    public Date getRepTime() {
        return repTime;
    }

    public void setRepTime(Date repTime) {
        this.repTime = repTime;
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }
}
