package com.bootdo.support.dto;

import java.util.Date;

public class SupportGoodsPushDTO {
     private String id;
    private String goodsinfo_id;
    private String goodsstorehouse_id;
    private String supplier;
    private String goodsbrand;
    private Integer pushnum;
    private Date pushdate;
    private String remarks;
    private String create_by;
    private Date create_date;
    private String update_by;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getGoodsbrand() {
        return goodsbrand;
    }

    public void setGoodsbrand(String goodsbrand) {
        this.goodsbrand = goodsbrand;
    }

    public Integer getPushnum() {
        return pushnum;
    }

    public void setPushnum(Integer pushnum) {
        this.pushnum = pushnum;
    }

    public Date getPushdate() {
        return pushdate;
    }

    public void setPushdate(Date pushdate) {
        this.pushdate = pushdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
