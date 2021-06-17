package com.bootdo.support.entity;

import java.util.Date;

public class SupportEquipment {
    private String id;
    private String name;
    private String equiptype_id;
    private String equipstatus_id;
    private String code;
    private String team_id;
    private String equipstorehouse_id;
    private String contact;
    private String mobile;
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

    public String getEquiptype_id() {
        return equiptype_id;
    }

    public void setEquiptype_id(String equiptype_id) {
        this.equiptype_id = equiptype_id;
    }

    public String getEquipstatus_id() {
        return equipstatus_id;
    }

    public void setEquipstatus_id(String equipstatus_id) {
        this.equipstatus_id = equipstatus_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getEquipstorehouse_id() {
        return equipstorehouse_id;
    }

    public void setEquipstorehouse_id(String equipstorehouse_id) {
        this.equipstorehouse_id = equipstorehouse_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
