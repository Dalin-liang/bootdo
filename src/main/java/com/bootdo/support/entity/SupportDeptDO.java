package com.bootdo.support.entity;

import java.util.Date;
import java.util.List;

public class SupportDeptDO {

    //
    private String id;
    //单位名称
    private String name;
    //单位联系人
    private String contact;
    //单位联系人电话
    private String mobile;
    //单位经纬度
    private String latLon;
    //单位地址
    private String addr;
    //是否启用
    private Integer enabled;
    //备注
    private String remarks;
    //记录录入人
    private String createBy;
    //记录录入时间
    private Date createDate;
    //记录更新人
    private String updateBy;
    //记录更新时间
    private Date updateDate;
    //关联应急人员
    private List<SupportDeptPerson> deptperson;

    /**
     * 设置：
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：单位名称
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取：单位名称
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：单位联系人
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * 获取：单位联系人
     */
    public String getContact() {
        return contact;
    }
    /**
     * 设置：单位联系人电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     * 获取：单位联系人电话
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * 设置：单位经纬度
     */
    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }
    /**
     * 获取：单位经纬度
     */
    public String getLatLon() {
        return latLon;
    }
    /**
     * 设置：单位地址
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }
    /**
     * 获取：单位地址
     */
    public String getAddr() {
        return addr;
    }
    /**
     * 设置：是否启用
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
    /**
     * 获取：是否启用
     */
    public Integer getEnabled() {
        return enabled;
    }
    /**
     * 设置：备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * 获取：备注
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * 设置：记录录入人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：记录录入人
     */
    public String getCreateBy() {
        return createBy;
    }
    /**
     * 设置：记录录入时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 获取：记录录入时间
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * 设置：记录更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    /**
     * 获取：记录更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }
    /**
     * 设置：记录更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    /**
     * 获取：记录更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    public List<SupportDeptPerson> getDeptperson() {
        return deptperson;
    }

    public void setDeptperson(List<SupportDeptPerson> deptperson) {
        this.deptperson = deptperson;
    }
}
