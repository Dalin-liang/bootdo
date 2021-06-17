package com.bootdo.support.entity;

import java.util.Date;

public class SupportStoragePlan {
    private String id;
    private String goodsinfo_id;
    private String goodsstorehouse_id;
    private Integer min_storage;
    private Integer plan_storage;
    private String remarks;
    private Date update_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsinfo_id() {
        return goodsinfo_id;
    }

    public void setGoodsinfo_id(String goodsinfo_id) {
        this.goodsinfo_id = goodsinfo_id;
    }

    public String getGoodsstorehouse_id() {
        return goodsstorehouse_id;
    }

    public void setGoodsstorehouse_id(String goodsstorehouse_id) {
        this.goodsstorehouse_id = goodsstorehouse_id;
    }

    public Integer getMin_storage() {
        return min_storage;
    }

    public void setMin_storage(Integer min_storage) {
        this.min_storage = min_storage;
    }

    public Integer getPlan_storage() {
        return plan_storage;
    }

    public void setPlan_storage(Integer plan_storage) {
        this.plan_storage = plan_storage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
