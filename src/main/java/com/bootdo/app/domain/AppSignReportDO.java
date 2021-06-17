package com.bootdo.app.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 巡查上报
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-16 20:08:39
 */
public class AppSignReportDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private String id;
	//上报人姓名
	private String repname;
	//上报人联系电话
	private String repphone;
	//性别（1男0女2其他）
	private Integer sex;
	//事件地址
	private String eventaddr;
	//事件纬度
	private String lat;
	//事件经度
	private String lon;
	//事件描述
	private String eventdesc;
	//上报时间
	private Date repdate;
	//数据来源类别（1、app)
	private String sourceType;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;
	//备注
	private String remarks;
	//事件类型
	private String eventType;
	//接报信息表ID
	private String watchReceiveId;

	/**
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：上报人姓名
	 */
	public void setRepname(String repname) {
		this.repname = repname;
	}
	/**
	 * 获取：上报人姓名
	 */
	public String getRepname() {
		return repname;
	}
	/**
	 * 设置：上报人联系电话
	 */
	public void setRepphone(String repphone) {
		this.repphone = repphone;
	}
	/**
	 * 获取：上报人联系电话
	 */
	public String getRepphone() {
		return repphone;
	}
	/**
	 * 设置：性别（1男0女2其他）
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别（1男0女2其他）
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：事件地址
	 */
	public void setEventaddr(String eventaddr) {
		this.eventaddr = eventaddr;
	}
	/**
	 * 获取：事件地址
	 */
	public String getEventaddr() {
		return eventaddr;
	}
	/**
	 * 设置：事件纬度
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * 获取：事件纬度
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * 设置：事件经度
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	/**
	 * 获取：事件经度
	 */
	public String getLon() {
		return lon;
	}
	/**
	 * 设置：事件描述
	 */
	public void setEventdesc(String eventdesc) {
		this.eventdesc = eventdesc;
	}
	/**
	 * 获取：事件描述
	 */
	public String getEventdesc() {
		return eventdesc;
	}
	/**
	 * 设置：上报时间
	 */
	public void setRepdate(Date repdate) {
		this.repdate = repdate;
	}
	/**
	 * 获取：上报时间
	 */
	public Date getRepdate() {
		return repdate;
	}
	/**
	 * 设置：数据来源类别（1、app)
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * 获取：数据来源类别（1、app)
	 */
	public String getSourceType() {
		return sourceType;
	}
	/**
	 * 设置：记录录入人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：记录录入人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：记录录入时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：记录录入时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：记录更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：记录更新人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：记录更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：记录更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置：事件类型
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * 获取：事件类型
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * 设置：接报信息表ID
	 */
	public void setWatchReceiveId(String watchReceiveId) {
		this.watchReceiveId = watchReceiveId;
	}
	/**
	 * 获取：接报信息表ID
	 */
	public String getWatchReceiveId() {
		return watchReceiveId;
	}
}
