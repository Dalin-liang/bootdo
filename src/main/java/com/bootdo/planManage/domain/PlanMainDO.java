package com.bootdo.planManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 预案表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public class PlanMainDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//事故类型
	private String accidentTypeId;
	//预警类别
	private String earlywarnTypeId;
	//预警级别
	private String earlywarnLevelId;
	//责任部门 ：部门表ID
	private String dutyDeptId;
	//预案名称
	private String name;
	//预案编码
	private String code;
	//启动条件
	private String startCondition;
	//预警报告单位：部门表ID
	private String reprotDeptId;
	//启动部门 ：部门表ID
	private String startDeptId;
	//是否启用
	private Integer enabled;
	//使用次数
	private Integer useTime;
	//备注
	private String remarks;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;


	//事故类型名称
	private String accidentTypeName;
	//预警类别名称
	private String earlywarnTypeName;
	//预警级别名称
	private String earlywarnLevelName;
	//责任单位名称
	private String dutyDeptName;
	//报告单位名称
	private String reprotDeptName;
	//启动单位名称
	private String startDeptName;

	//预案执行时间
	private Date actionDate;
	private Date actionDateBegin;
	private Date actionDateEnd;

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
	 * 设置：事故类型
	 */
	public void setAccidentTypeId(String accidentTypeId) {
		this.accidentTypeId = accidentTypeId;
	}
	/**
	 * 获取：事故类型
	 */
	public String getAccidentTypeId() {
		return accidentTypeId;
	}
	/**
	 * 设置：预警类别
	 */
	public void setEarlywarnTypeId(String earlywarnTypeId) {
		this.earlywarnTypeId = earlywarnTypeId;
	}
	/**
	 * 获取：预警类别
	 */
	public String getEarlywarnTypeId() {
		return earlywarnTypeId;
	}
	/**
	 * 设置：预警级别
	 */
	public void setEarlywarnLevelId(String earlywarnLevelId) {
		this.earlywarnLevelId = earlywarnLevelId;
	}
	/**
	 * 获取：预警级别
	 */
	public String getEarlywarnLevelId() {
		return earlywarnLevelId;
	}
	/**
	 * 设置：责任部门 ：部门表ID
	 */
	public void setDutyDeptId(String dutyDeptId) {
		this.dutyDeptId = dutyDeptId;
	}
	/**
	 * 获取：责任部门 ：部门表ID
	 */
	public String getDutyDeptId() {
		return dutyDeptId;
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
	 * 设置：预警报告单位：部门表ID
	 */
	public void setReprotDeptId(String reprotDeptId) {
		this.reprotDeptId = reprotDeptId;
	}
	/**
	 * 获取：预警报告单位：部门表ID
	 */
	public String getReprotDeptId() {
		return reprotDeptId;
	}
	/**
	 * 设置：启动部门 ：部门表ID
	 */
	public void setStartDeptId(String startDeptId) {
		this.startDeptId = startDeptId;
	}
	/**
	 * 获取：启动部门 ：部门表ID
	 */
	public String getStartDeptId() {
		return startDeptId;
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
	
	public String getAccidentTypeName() {
		return accidentTypeName;
	}
	public void setAccidentTypeName(String accidentTypeName) {
		this.accidentTypeName = accidentTypeName;
	}
	public String getEarlywarnTypeName() {
		return earlywarnTypeName;
	}
	public void setEarlywarnTypeName(String earlywarnTypeName) {
		this.earlywarnTypeName = earlywarnTypeName;
	}
	public String getEarlywarnLevelName() {
		return earlywarnLevelName;
	}
	public void setEarlywarnLevelName(String earlywarnLevelName) {
		this.earlywarnLevelName = earlywarnLevelName;
	}
	public String getDutyDeptName() {
		return dutyDeptName;
	}
	public void setDutyDeptName(String dutyDeptName) {
		this.dutyDeptName = dutyDeptName;
	}
	public String getReprotDeptName() {
		return reprotDeptName;
	}
	public void setReprotDeptName(String reprotDeptName) {
		this.reprotDeptName = reprotDeptName;
	}
	public String getStartDeptName() {
		return startDeptName;
	}
	public void setStartDeptName(String startDeptName) {
		this.startDeptName = startDeptName;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getActionDateBegin() {
		return actionDateBegin;
	}

	public void setActionDateBegin(Date actionDateBegin) {
		this.actionDateBegin = actionDateBegin;
	}

	public Date getActionDateEnd() {
		return actionDateEnd;
	}

	public void setActionDateEnd(Date actionDateEnd) {
		this.actionDateEnd = actionDateEnd;
	}
}
