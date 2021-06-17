package com.bootdo.dispatch.center.res;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.base.*;
import com.bootdo.dispatch.center.base.Location.SimpleLocation;
import com.bootdo.dispatch.center.vo.GoodInfoVO;

import java.io.Serializable;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/9/2
 */
public class GoodDistributionRes implements DispatchRes,Locatable,Contactable {

    private String goodId;
    private GoodInfoVO goodInfo;
    private Integer goodNum;
    private String houseId;
    private String houseName;
    private String houseCode;
    private String latLon;
    private String address;
    private String contact;
    private String mobile;
    private ContactWay contactWay;

    private Location location;

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public GoodInfoVO getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(GoodInfoVO goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public ContactWay getContactWay() {
        return contactWay;
    }

    public void setContactWay(ContactWay contactWay) {
        this.contactWay = contactWay;
    }

    @Override
    public Serializable getResId() {
        return goodId+"_"+houseId;
    }

    @Override
    public void ready() {
        if(StringUtils.isNotEmpty(latLon)){
            String[] vals = latLon.split(",");
            if(vals.length==2){
                setLocation(new SimpleLocation(vals[1],vals[0]));
            }
        }
        if(StringUtils.isNotEmpty(contact)||StringUtils.isNotEmpty(mobile)){
            setContactWay(new ContactWay(contact,mobile));
        }
    }
}
