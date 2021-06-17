package com.bootdo.archive.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 摄像头调度表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:08
 */
public class ArchiveWebcamDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//方案表ID
	private String actionprogramId;
	//使用者ID
	private String user;
	//摄像头ID
	private String webcamId;
	//使用开始时间
	private Date beginTime;
	//使用结束时间
	private Date endTime;
	//记录录入人
	private Long createBy;
	//记录录入时间
	private Date createDate;

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
	 * 设置：方案表ID
	 */
	public void setActionprogramId(String actionprogramId) {
		this.actionprogramId = actionprogramId;
	}
	/**
	 * 获取：方案表ID
	 */
	public String getActionprogramId() {
		return actionprogramId;
	}
	/**
	 * 设置：使用者ID
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * 获取：使用者ID
	 */
	public String getUser() {
		return user;
	}
	/**
	 * 设置：摄像头ID
	 */
	public void setWebcamId(String webcamId) {
		this.webcamId = webcamId;
	}
	/**
	 * 获取：摄像头ID
	 */
	public String getWebcamId() {
		return webcamId;
	}
	/**
	 * 设置：使用开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取：使用开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置：使用结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：使用结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：记录录入人
	 */
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：记录录入人
	 */
	public Long getCreateBy() {
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
}
