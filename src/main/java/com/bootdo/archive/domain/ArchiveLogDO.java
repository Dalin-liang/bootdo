package com.bootdo.archive.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 应急调度日志归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
public class ArchiveLogDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//应急执行方案表ID
	private String actionprogramId;
	//日志时间
	private Date time;
	//是否在上大屏显示
	private Integer showBigscreen;
	//逻辑删除（0删除1未删除）
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
	 * 设置：逻辑删除（0删除1未删除）
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：逻辑删除（0删除1未删除）
	 */
	public Integer getIsDel() {
		return isDel;
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
}
