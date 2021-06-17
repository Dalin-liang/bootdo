package com.bootdo.dispatch.center.res;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.base.*;
import com.bootdo.dispatch.center.base.Location.SimpleLocation;

/**
 * 应急装备
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/23
 */
public class EmergencyEquipment implements DispatchRes,Locatable,Contactable {

    /**
     * 装备Id
     */
    private String resId;
    /**
     * 名称
     */
    private String name;
    /**
     * 装备编号
     */
    private String code;
    /**
     * 所属队伍
     */
    private String teamId;
    /**
     * 所属队伍名称
     */
    private String teamName;
    /**
     * 装备类型
     */
    private String equipTypeId;
    /**
     * 装备类型
     */
    private String equipTypeName;
    /**
     * 经纬
     */
    private String latLon;
    /**
     * 定位信息
     */
    private Location location;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 联系方式
     */
    private ContactWay contactWay;

    private String addr;

    @Override
    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public ContactWay getContactWay() {
        return contactWay;
    }

    public void setContactWay(ContactWay contactWay) {
        this.contactWay = contactWay;
    }

    @Override
    public ResType getResTypeEnum() {
        return ResType.EMERGENCY_EQUIPMENT;
    }

    public String getEquipTypeId() {
        return equipTypeId;
    }

    public void setEquipTypeId(String equipTypeId) {
        this.equipTypeId = equipTypeId;
    }

    public String getEquipTypeName() {
        return equipTypeName;
    }

    public void setEquipTypeName(String equipTypeName) {
        this.equipTypeName = equipTypeName;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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
