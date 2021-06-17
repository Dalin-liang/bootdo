package com.bootdo.support.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 执法人轨迹表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-15 09:44:23
 */
public class PresonWayPointDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//记录时间
	private Date createTime;
	//纬度
	private String latitude;
	//经度
	private String longitude;
	//用户id
	private String userId;

	private SupportDeptPerson person;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：记录时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：记录时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：纬度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：纬度
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置：经度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：经度
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}

	public SupportDeptPerson getPerson() {
		return person;
	}

	public void setPerson(SupportDeptPerson person) {
		this.person = person;
	}
}
