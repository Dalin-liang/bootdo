package com.bootdo.planManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 预案响应部门
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public class PlanRespDeptDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//预案表ID
	private String planMainId;
	//部门名称 关联部门表id 
	private String deptId;
	//响应工作内容
	private String content;
	//负责人姓名
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
	
	private String deptName;
	private String planMainName;
	
	//排序号
	private Integer sortNo;
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
	 * 设置：部门名称 关联部门表id 
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门名称 关联部门表id 
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：响应工作内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：响应工作内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：负责人姓名
	 */
	public void setLiabilityMan(String liabilityMan) {
		this.liabilityMan = liabilityMan;
	}
	/**
	 * 获取：负责人姓名
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPlanMainName() {
		return planMainName;
	}
	public void setPlanMainName(String planMainName) {
		this.planMainName = planMainName;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	
	
}
