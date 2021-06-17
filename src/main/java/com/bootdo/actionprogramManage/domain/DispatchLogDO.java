package com.bootdo.actionprogramManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 应急调度日志表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:16
 */
public class DispatchLogDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//应急执行方案表ID
	private String actionprogramId;
	//应急执行方案code
	private String code;
	//日志概要
	private String summary;
	//日志具体内容
	private String content;
	//地址
	private String address;
	//经纬度
	private String latLon;
	//日志时间
	private Date time;
	//是否在上大屏显示
	private Integer showBigscreen;
	//是否逻辑删除
	private Integer isDel;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;

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
	 * 设置：应急执行方案表ID
	 */
	public void setActionprogramId(String actionprogramId) {
		this.actionprogramId = actionprogramId;
	}
	/**
	 * 获取：应急执行方案表ID
	 */
	public String getActionprogramId() {
		return actionprogramId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置：日志时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 获取：日志时间
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * 设置：是否在上大屏显示
	 */
	public void setShowBigscreen(Integer showBigscreen) {
		this.showBigscreen = showBigscreen;
	}
	/**
	 * 获取：是否在上大屏显示
	 */
	public Integer getShowBigscreen() {
		return showBigscreen;
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

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatLon() {
		return latLon;
	}

	public void setLatLon(String latLon) {
		this.latLon = latLon;
	}
}
