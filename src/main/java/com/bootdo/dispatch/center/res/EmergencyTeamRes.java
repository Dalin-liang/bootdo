package com.bootdo.dispatch.center.res;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.base.*;
import com.bootdo.dispatch.center.base.Location.SimpleLocation;
import com.bootdo.dispatch.center.vo.TeamMemberVO;

import java.util.List;

/**
 * 应急队伍
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
public class EmergencyTeamRes implements DispatchRes,ReLocatable,Contactable {

    /**
     * 队伍Id
     */
    private String resId;

    /**
     * 队伍名称
     */
    private String teamName;

    /**
     * 队伍类型Id
     */
    private String teamTypeId;

    /**
     * 队伍类型名称
     */
    private String teamTypeName;
    /**
     * 队伍人数
     */
    private int numOfTeam;

    /**
     * 队伍地址
     */
    private String address;

    /**
     * 主要单位Id
     */
    private String mainDeptId;

    /**
     * 主要单位名称
     */
    private String mainDeptName;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lon;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 电话
     */
    private String mobile;

    /**
     * 定位信息
     */
    private Location location;

    /**
     * 成员
     */
    private List<TeamMemberVO> members;

    /**
     * 联系方式
     */
    private ContactWay contactWay;

    @Override
    public ResType getResTypeEnum() {
        return ResType.EMERGENCY_TEAM;
    }

    @Override
    public Location getLocation() {
        return location;
    }


    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamTypeId() {
        return teamTypeId;
    }

    public void setTeamTypeId(String teamTypeId) {
        this.teamTypeId = teamTypeId;
    }

    public String getTeamTypeName() {
        return teamTypeName;
    }

    public void setTeamTypeName(String teamTypeName) {
        this.teamTypeName = teamTypeName;
    }

    public int getNumOfTeam() {
        return numOfTeam;
    }

    public void setNumOfTeam(int numOfTeam) {
        this.numOfTeam = numOfTeam;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMainDeptId() {
        return mainDeptId;
    }

    public void setMainDeptId(String mainDeptId) {
        this.mainDeptId = mainDeptId;
    }

    public String getMainDeptName() {
        return mainDeptName;
    }

    public void setMainDeptName(String mainDeptName) {
        this.mainDeptName = mainDeptName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
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

    public List<TeamMemberVO> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMemberVO> members) {
        this.members = members;
    }

    @Override
    public ContactWay getContactWay() {
        return contactWay;
    }

    public void setContactWay(ContactWay contactWay) {
        this.contactWay = contactWay;
    }

    @Override
    public void ready() {
        if(StringUtils.isNotEmpty(lat)&&StringUtils.isNotEmpty(lon))
            setLocation(new SimpleLocation(lon,lat));
        if(StringUtils.isNotEmpty(contact)||StringUtils.isNotEmpty(mobile)){
            setContactWay(new ContactWay(contact,mobile));
        }
    }
}
