package com.bootdo.support.entity;

import java.util.Date;

public class SupportGoodsTrans {
    private String id;
    private String goodsinfo_id;
    private String from_goodsstorehouse_id;
    private String to_goodsstorehouse_id;
    private Integer transnum;
    private Date transdate;
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

    public String getFrom_goodsstorehouse_id() {
        return from_goodsstorehouse_id;
    }

    public void setFrom_goodsstorehouse_id(String from_goodsstorehouse_id) {
        this.from_goodsstorehouse_id = from_goodsstorehouse_id;
    }

    public String getTo_goodsstorehouse_id() {
        return to_goodsstorehouse_id;
    }

    public void setTo_goodsstorehouse_id(String to_goodsstorehouse_id) {
        this.to_goodsstorehouse_id = to_goodsstorehouse_id;
    }

    public Integer getTransnum() {
        return transnum;
    }

    public void setTransnum(Integer transnum) {
        this.transnum = transnum;
    }

    public Date getTransdate() {
        return transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
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
