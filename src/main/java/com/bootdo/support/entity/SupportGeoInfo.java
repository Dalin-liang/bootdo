package com.bootdo.support.entity;

import java.util.Date;

public class SupportGeoInfo {
    private String id;
    private String name;
    private String attention_type;
    private String geotype_id;
    private String lat_lon;
    private String addr;
    private Integer enabled;
    private Integer sort_no;
    private Integer number;
    private String contact_number;
    private String contact;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttention_type() {
        return attention_type;
    }

    public void setAttention_type(String attention_type) {
        this.attention_type = attention_type;
    }

    public String getGeotype_id() {
        return geotype_id;
    }

    public void setGeotype_id(String geotype_id) {
        this.geotype_id = geotype_id;
    }

    public String getLat_lon() {
        return lat_lon;
    }

    public void setLat_lon(String lat_lon) {
        this.lat_lon = lat_lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getSort_no() {
        return sort_no;
    }

    public void setSort_no(Integer sort_no) {
        this.sort_no = sort_no;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
