package com.bootdo.actionprogramManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 执行方案的预案响应部门归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
public class DispatchRespdeptDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//执行方案的预案主表ID
	private String dispatchPlanmainId;
	//部门名称
	private String deptName;
	//响应工作内容
	private String content;
	//负责人姓名
	private String liabilityMan;
	//联系电话
	private String mobile;
	//记录录入人
	private Long createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private Long updateBy;
	//记录更新时间
	private Date updateDate;
	
	//任务是否安排
	private Integer isArrange;
	//现场的任务状态(已接收到任务、开始执行中、执行遇到困难、执行完成)
	private Integer actionStatus;
	//是否逻辑删除
	private Integer isDel;
	//方案表ID
	private String actionprogramId;
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
	 * 设置：执行方案的预案主表ID
	 */
	public void setDispatchPlanmainId(String dispatchPlanmainId) {
		this.dispatchPlanmainId = dispatchPlanmainId;
	}
	/**
	 * 获取：执行方案的预案主表ID
	 */
	public String getDispatchPlanmainId() {
		return dispatchPlanmainId;
	}
	/**
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptName() {
		return deptName;
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
	public Integer getIsArrange() {
		return isArrange;
	}
	public void setIsArrange(Integer isArrange) {
		this.isArrange = isArrange;
	}
	public Integer getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(Integer actionStatus) {
		this.actionStatus = actionStatus;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getActionprogramId() {
		return actionprogramId;
	}
	public void setActionprogramId(String actionprogramId) {
		this.actionprogramId = actionprogramId;
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
