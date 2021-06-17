package com.bootdo.actionprogramManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 应急执行方案主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
public class DispatchActionprogramMainDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//方案编码
	private String code;
	//方案状态(执行中、已结案)
	private Integer status;
	//方案执行时间
	private Date actionDate;
	//结案时间
	private Date closecaseDate;
	//归档日期
	private Date archiveDate;
	//是否已归档
	private Integer isArchive;
	//记录录入人
	private Long createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private Long updateBy;
	//记录更新时间
	private Date updateDate;
	//关联的事件编码
	private String eventCode;
	private String eventdesc;
	private String planmainName;

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
	 * 设置：方案编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：方案编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：方案状态(执行中、已结案)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：方案状态(执行中、已结案)
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：方案执行时间
	 */
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	/**
	 * 获取：方案执行时间
	 */
	public Date getActionDate() {
		return actionDate;
	}
	/**
	 * 设置：结案时间
	 */
	public void setClosecaseDate(Date closecaseDate) {
		this.closecaseDate = closecaseDate;
	}
	/**
	 * 获取：结案时间
	 */
	public Date getClosecaseDate() {
		return closecaseDate;
	}
	/**
	 * 设置：归档日期
	 */
	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
	/**
	 * 获取：归档日期
	 */
	public Date getArchiveDate() {
		return archiveDate;
	}
	/**
	 * 设置：是否已归档
	 */
	public void setIsArchive(Integer isArchive) {
		this.isArchive = isArchive;
	}
	/**
	 * 获取：是否已归档
	 */
	public Integer getIsArchive() {
		return isArchive;
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
	/**
	 * 设置：记录更新人
	 */
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：记录更新人
	 */
	public Long getUpdateBy() {
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

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventdesc() {
		return eventdesc;
	}

	public void setEventdesc(String eventdesc) {
		this.eventdesc = eventdesc;
	}

	public String getPlanmainName() {
		return planmainName;
	}

	public void setPlanmainName(String planmainName) {
		this.planmainName = planmainName;
	}
}
