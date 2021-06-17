package com.bootdo.archive.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 执行方案的预警信息归档表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-28 09:37:52
 */
public class ArchiveEarlywarnDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private String id;
	//应急执行方案表ID
	private String actionprogramId;
	//上报人姓名
	private String repname;
	//上报人联系电话
	private String repphone;
	//性别
	private Integer sex;
	//事件地址
	private String eventaddr;
	//事件描述
	private String eventdesc;
	//上报时间
	private Date repdate;
	//接报途径(电话、短信、APP、微信、各部门的报警信息、日常巡查（巡查上报）、物联网接入模块（超过预警阀值）[消防模块、水质监测、特殊人群手环、视频])
	private String sourceType;
	//单位名称
	private String deptName;
	//事故类别名称
	private String accidentTypeName;
	//事件级别
	private String eventlevelName;
	//备注
	private String remarks;
	//接收人
	private String deptpersonId;
	//记录录入人
	private Long createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private Long updateBy;
	//记录更新时间
	private Date updateDate;
	//事件经纬度
	private String latLon;
	//是否推送到应急指挥调度平台(0未推送；5已推送)
	private Integer ispush;
	//earlywarn_type表name
	private String earlywarnTypeName;
	//接报信息是否受理（0 否 1是）
	private Integer isAcceptance;
	//值守人员受理时间
	private Date acceptanceTime;
	//值守人员的受理方式：上报1、推送5、终结10
	private Integer acceptanceType;
	//上报审批状态：上报审批中1、推送(同意)5、终结(不同意)10
	private Integer examineType;
	//上报的审批意见
	private String examineOpinion;
	//审批时间
	private Date examineTime;
	//接收人的来源类型 1部门 2值班
	private Integer examinerType;

	/**
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
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
	 * 设置：上报人姓名
	 */
	public void setRepname(String repname) {
		this.repname = repname;
	}
	/**
	 * 获取：上报人姓名
	 */
	public String getRepname() {
		return repname;
	}
	/**
	 * 设置：上报人联系电话
	 */
	public void setRepphone(String repphone) {
		this.repphone = repphone;
	}
	/**
	 * 获取：上报人联系电话
	 */
	public String getRepphone() {
		return repphone;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：事件地址
	 */
	public void setEventaddr(String eventaddr) {
		this.eventaddr = eventaddr;
	}
	/**
	 * 获取：事件地址
	 */
	public String getEventaddr() {
		return eventaddr;
	}
	/**
	 * 设置：事件描述
	 */
	public void setEventdesc(String eventdesc) {
		this.eventdesc = eventdesc;
	}
	/**
	 * 获取：事件描述
	 */
	public String getEventdesc() {
		return eventdesc;
	}
	/**
	 * 设置：上报时间
	 */
	public void setRepdate(Date repdate) {
		this.repdate = repdate;
	}
	/**
	 * 获取：上报时间
	 */
	public Date getRepdate() {
		return repdate;
	}
	/**
	 * 设置：接报途径(电话、短信、APP、微信、各部门的报警信息、日常巡查（巡查上报）、物联网接入模块（超过预警阀值）[消防模块、水质监测、特殊人群手环、视频])
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * 获取：接报途径(电话、短信、APP、微信、各部门的报警信息、日常巡查（巡查上报）、物联网接入模块（超过预警阀值）[消防模块、水质监测、特殊人群手环、视频])
	 */
	public String getSourceType() {
		return sourceType;
	}
	/**
	 * 设置：单位名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：单位名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：事故类别名称
	 */
	public void setAccidentTypeName(String accidentTypeName) {
		this.accidentTypeName = accidentTypeName;
	}
	/**
	 * 获取：事故类别名称
	 */
	public String getAccidentTypeName() {
		return accidentTypeName;
	}
	/**
	 * 设置：事件级别
	 */
	public void setEventlevelName(String eventlevelName) {
		this.eventlevelName = eventlevelName;
	}
	/**
	 * 获取：事件级别
	 */
	public String getEventlevelName() {
		return eventlevelName;
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
	 * 设置：接收人
	 */
	public void setDeptpersonId(String deptpersonId) {
		this.deptpersonId = deptpersonId;
	}
	/**
	 * 获取：接收人
	 */
	public String getDeptpersonId() {
		return deptpersonId;
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
	 * 设置：事件经纬度
	 */
	public void setLatLon(String latLon) {
		this.latLon = latLon;
	}
	/**
	 * 获取：事件经纬度
	 */
	public String getLatLon() {
		return latLon;
	}
	/**
	 * 设置：是否推送到应急指挥调度平台(0未推送；5已推送)
	 */
	public void setIspush(Integer ispush) {
		this.ispush = ispush;
	}
	/**
	 * 获取：是否推送到应急指挥调度平台(0未推送；5已推送)
	 */
	public Integer getIspush() {
		return ispush;
	}
	/**
	 * 设置：earlywarn_type表name
	 */
	public void setEarlywarnTypeName(String earlywarnTypeName) {
		this.earlywarnTypeName = earlywarnTypeName;
	}
	/**
	 * 获取：earlywarn_type表name
	 */
	public String getEarlywarnTypeName() {
		return earlywarnTypeName;
	}
	/**
	 * 设置：接报信息是否受理（0 否 1是）
	 */
	public void setIsAcceptance(Integer isAcceptance) {
		this.isAcceptance = isAcceptance;
	}
	/**
	 * 获取：接报信息是否受理（0 否 1是）
	 */
	public Integer getIsAcceptance() {
		return isAcceptance;
	}
	/**
	 * 设置：值守人员受理时间
	 */
	public void setAcceptanceTime(Date acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}
	/**
	 * 获取：值守人员受理时间
	 */
	public Date getAcceptanceTime() {
		return acceptanceTime;
	}
	/**
	 * 设置：值守人员的受理方式：上报1、推送5、终结10
	 */
	public void setAcceptanceType(Integer acceptanceType) {
		this.acceptanceType = acceptanceType;
	}
	/**
	 * 获取：值守人员的受理方式：上报1、推送5、终结10
	 */
	public Integer getAcceptanceType() {
		return acceptanceType;
	}
	/**
	 * 设置：上报审批状态：上报审批中1、推送(同意)5、终结(不同意)10
	 */
	public void setExamineType(Integer examineType) {
		this.examineType = examineType;
	}
	/**
	 * 获取：上报审批状态：上报审批中1、推送(同意)5、终结(不同意)10
	 */
	public Integer getExamineType() {
		return examineType;
	}
	/**
	 * 设置：上报的审批意见
	 */
	public void setExamineOpinion(String examineOpinion) {
		this.examineOpinion = examineOpinion;
	}
	/**
	 * 获取：上报的审批意见
	 */
	public String getExamineOpinion() {
		return examineOpinion;
	}
	/**
	 * 设置：审批时间
	 */
	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}
	/**
	 * 获取：审批时间
	 */
	public Date getExamineTime() {
		return examineTime;
	}
	/**
	 * 设置：接收人的来源类型 1部门 2值班
	 */
	public void setExaminerType(Integer examinerType) {
		this.examinerType = examinerType;
	}
	/**
	 * 获取：接收人的来源类型 1部门 2值班
	 */
	public Integer getExaminerType() {
		return examinerType;
	}
}
