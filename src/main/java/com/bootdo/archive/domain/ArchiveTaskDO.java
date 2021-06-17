package com.bootdo.archive.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 应急执行方案归档任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
public class ArchiveTaskDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//方案表ID
	private String actionprogramId;
	//执行方案的预案主表ID
	private String dispatchPlanmainId;
	//任务名称
	private String name;
	//任务内容
	private String content;
	//接收任务的对象类型(个人、应急队伍、部门)
	private Integer type;
	//负责对象的ID
	private String liabilityId;
	//任务是否安排
	private Integer isarrange;
	//现场的任务状态(已接收到任务、开始执行中、执行遇到困难、执行完成)
	private Integer actionStatus;
	//记录录入人
	private Long createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private Long updateBy;
	//记录更新时间
	private Date updateDate;
	//联系电话
	private String mobile;
	//是否逻辑删除
	private Integer isDel;
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
	 * 设置：接收任务的对象类型(个人、应急队伍、部门)
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：接收任务的对象类型(个人、应急队伍、部门)
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：负责对象的ID
	 */
	public void setLiabilityId(String liabilityId) {
		this.liabilityId = liabilityId;
	}
	/**
	 * 获取：负责对象的ID
	 */
	public String getLiabilityId() {
		return liabilityId;
	}
	/**
	 * 设置：任务是否安排
	 */
	public void setIsarrange(Integer isarrange) {
		this.isarrange = isarrange;
	}
	/**
	 * 获取：任务是否安排
	 */
	public Integer getIsarrange() {
		return isarrange;
	}
	/**
	 * 设置：现场的任务状态(已接收到任务、开始执行中、执行遇到困难、执行完成)
	 */
	public void setActionStatus(Integer actionStatus) {
		this.actionStatus = actionStatus;
	}
	/**
	 * 获取：现场的任务状态(已接收到任务、开始执行中、执行遇到困难、执行完成)
	 */
	public Integer getActionStatus() {
		return actionStatus;
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
	 * 设置：是否逻辑删除
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：是否逻辑删除
	 */
	public Integer getIsDel() {
		return isDel;
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
