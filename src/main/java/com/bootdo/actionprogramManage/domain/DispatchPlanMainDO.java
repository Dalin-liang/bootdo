package com.bootdo.actionprogramManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 执行方案的预案归档主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
public class DispatchPlanMainDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//预案主表ID
	private String planmainId;
	//应急执行方案表ID
	private String actionprogramId;
	//事故类型
	private String accidentTypeName;
	//预警类别
	private String earlywarnTypeName;
	//预警级别
	private String earlywarnLevelName;
	//责任部门名称
	private String dutyDeptName;
	//预案名称
	private String name;
	//预案编码
	private String code;
	//启动条件
	private String startCondition;
	//预警报告单位
	private String reprotDeptName;
	//启动部门 名称
	private String startDeptName;
	//是否启用
	private Integer enabled;
	//使用次数
	private Integer useTime;
	//备注
	private String remarks;
	//记录录入人
	private Long createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private Long updateBy;
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
	 * 设置：预案主表ID
	 */
	public void setPlanmainId(String planmainId) {
		this.planmainId = planmainId;
	}
	/**
	 * 获取：预案主表ID
	 */
	public String getPlanmainId() {
		return planmainId;
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
	 * 设置：事故类型
	 */
	public void setAccidentTypeName(String accidentTypeName) {
		this.accidentTypeName = accidentTypeName;
	}
	/**
	 * 获取：事故类型
	 */
	public String getAccidentTypeName() {
		return accidentTypeName;
	}
	/**
	 * 设置：预警类别
	 */
	public void setEarlywarnTypeName(String earlywarnTypeName) {
		this.earlywarnTypeName = earlywarnTypeName;
	}
	/**
	 * 获取：预警类别
	 */
	public String getEarlywarnTypeName() {
		return earlywarnTypeName;
	}
	/**
	 * 设置：预警级别
	 */
	public void setEarlywarnLevelName(String earlywarnLevelName) {
		this.earlywarnLevelName = earlywarnLevelName;
	}
	/**
	 * 获取：预警级别
	 */
	public String getEarlywarnLevelName() {
		return earlywarnLevelName;
	}
	/**
	 * 设置：责任部门名称
	 */
	public void setDutyDeptName(String dutyDeptName) {
		this.dutyDeptName = dutyDeptName;
	}
	/**
	 * 获取：责任部门名称
	 */
	public String getDutyDeptName() {
		return dutyDeptName;
	}
	/**
	 * 设置：预案名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：预案名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：预案编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：预案编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：启动条件
	 */
	public void setStartCondition(String startCondition) {
		this.startCondition = startCondition;
	}
	/**
	 * 获取：启动条件
	 */
	public String getStartCondition() {
		return startCondition;
	}
	/**
	 * 设置：预警报告单位
	 */
	public void setReprotDeptName(String reprotDeptName) {
		this.reprotDeptName = reprotDeptName;
	}
	/**
	 * 获取：预警报告单位
	 */
	public String getReprotDeptName() {
		return reprotDeptName;
	}
	/**
	 * 设置：启动部门 名称
	 */
	public void setStartDeptName(String startDeptName) {
		this.startDeptName = startDeptName;
	}
	/**
	 * 获取：启动部门 名称
	 */
	public String getStartDeptName() {
		return startDeptName;
	}
	/**
	 * 设置：是否启用
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	/**
	 * 获取：是否启用
	 */
	public Integer getEnabled() {
		return enabled;
	}
	/**
	 * 设置：使用次数
	 */
	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}
	/**
	 * 获取：使用次数
	 */
	public Integer getUseTime() {
		return useTime;
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
}
