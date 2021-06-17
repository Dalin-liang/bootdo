package com.bootdo.support.dto;

import java.util.Date;
import java.util.List;

public class SupportExpertInfoDTO {
    private String id;
    private String name;
    private Integer sex;
    private Integer age;
    private String mobile;
    private String position;
    private String goodat;
    private String remarks;
    private String create_by;
    private Date create_date;
    private String update_by;
    private Date update_date;

    private String typeof;//类型：
    private String ethnic;// 民族：
    private String organization;// 机构：
    private String title;//头衔：
    private String descrip;//描述
    private String addr;// 住址
    private String sn;//  编号
    private List<String> earlywarnName;
    private List<String> earlywarn_type_id;

    private String earlywarnIds;

    public String getEarlywarnIds() {
        return earlywarnIds;
    }

    public void setEarlywarnIds(String earlywarnIds) {
        this.earlywarnIds = earlywarnIds;
    }
    public List<String> getEarlywarn_type_id() {
        return earlywarn_type_id;
    }

    public void setEarlywarn_type_id(List<String> earlywarn_type_id) {
        this.earlywarn_type_id = earlywarn_type_id;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGoodat() {
        return goodat;
    }

    public void setGoodat(String goodat) {
        this.goodat = goodat;
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

    public String getTypeof() {
        return typeof;
    }

    public void setTypeof(String typeof) {
        this.typeof = typeof;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<String> getEarlywarnName() {
        return earlywarnName;
    }

    public void setEarlywarnName(List<String> earlywarnName) {
        this.earlywarnName = earlywarnName;
    }
}
