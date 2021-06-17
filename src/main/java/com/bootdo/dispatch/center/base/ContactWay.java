package com.bootdo.dispatch.center.base;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/23
 */
public class ContactWay {

    public String name;

    private String mobile;

    private String tel1;

    private String tel2;


    public ContactWay(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public ContactWay(String name, String mobile, String tel1) {
        this.name = name;
        this.mobile = mobile;
        this.tel1 = tel1;
    }

    public ContactWay(String name, String mobile, String tel1, String tel2) {
        this.name = name;
        this.mobile = mobile;
        this.tel1 = tel1;
        this.tel2 = tel2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ContactWay{");
        sb.append("name='").append(name).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", tel1='").append(tel1).append('\'');
        sb.append(", tel2='").append(tel2).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
