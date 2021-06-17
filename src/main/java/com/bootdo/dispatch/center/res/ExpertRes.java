package com.bootdo.dispatch.center.res;

import com.bootdo.dispatch.center.base.DispatchRes;

/**
 * 专家资源
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/23
 */
public class ExpertRes implements DispatchRes {

    private String resId;
    private String name;
    private Integer sex;
    private Integer age;
    private String mobile;
    private String position;
    private String goodat;
    private String remarks;
    private String typeof;//类型：
    private String ethnic;// 民族：
    private String organization;// 机构：
    private String title;//头衔：
    private String descrip;//描述
    private String addr;// 住址
    private String sn;//  编号


    @Override
    public ResType getResTypeEnum() {
        return ResType.EXPERT;
    }

    @Override
    public String getResId() {
        return resId;
    }


    public void setResId(String resId) {
        this.resId = resId;
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

}
