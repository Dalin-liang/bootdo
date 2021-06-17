package com.bootdo.planManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 预案任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public class PlanTaskDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//排序号
	private Integer sortNo;
	//预案表ID
	private String planMainId;
	//任务名称
	private String name;
	//任务内容
	private String content;
	//负责人
	private String liabilityMan;
	//联系电话
	private String mobile;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;
	//接收任务的对象类型(个人、应急队伍、部门)
	private Integer type;
	//负责对象的ID
	private String liabilityId;
	//任务类型
	private Integer taskType;

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
	 * 设置：排序号
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	/**
	 * 获取：排序号
	 */
	public Integer getSortNo() {
		return sortNo;
	}
	/**
	 * 设置：预案表ID
	 */
	public void setPlanMainId(String planMainId) {
		this.planMainId = planMainId;
	}
	/**
	 * 获取：预案表ID
	 */
	public String getPlanMainId() {
		return planMainId;
	}
	/**
	 * 设置：任务名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：任务名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：任务内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：任务内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：负责人
	 */
	public void setLiabilityMan(String liabilityMan) {
		this.liabilityMan = liabilityMan;
	}
	/**
	 * 获取：负责人
	 */
	public String getLiabilityMan() {
		return liabilityMan;
	}
	/**
	 * 设置：联系电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：联系电话
	 */
	public String getMobile() {
		return mobile;
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
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLiabilityId() {
		return liabilityId;
	}
	public void setLiabilityId(String liabilityId) {
		this.liabilityId = liabilityId;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	
	
}
