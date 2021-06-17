package com.bootdo.support.dto;

import java.util.Date;



/**
 * 信息接报管理
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 09:07:54
 */
public class ReceiveInfoDTO {
//主键ID
	private String id;
	//上报人姓名
	private String repname;
	//上报人联系电话
	private String repphone;
	//性别
	private Integer sex;
	//事件地址
	private String eventaddr;
	//事件经纬度
	private String lat_lon;
	//事件描述
	private String eventdesc;
	//上报时间
	private Date repdate;
	//接报途径(电话、短信、APP、微信、各部门的报警信息、日常巡查（巡查上报）、物联网接入模块（超过预警阀值）[消防模块、水质监测、特殊人群手环、视频])
	private String source_type;
	//关联单位表ID
	private String dept_id;
	//事故类别表ID
	private String accident_type_id;
	//事件级别
	private String eventlevel;
	//是否暂存
	private Integer tempsaveFlag;
	//备注
	private String remarks;
	//是否推送到应急指挥调度平台(0未推送；5已推送)
	private Integer ispush;
	//接收人
	private String deptperson_id;
	//earlywarn_type表id
	private String earlywarn_id;
	//接报信息是否受理（0 否 1是）
	private String is_acceptance;
	//值守人员受理时间
	private Date acceptance_time;
	//值守人员的受理方式：上报1、推送5、终结10
	private String acceptance_type;
	//上报审批状态：上报审批中1、推送(同意)5、终结(不同意)10
	private String examine_type;
	//上报的审批意见
	private String examine_opinion;
	//接收人的来源类型 1部门 2值班
	private String examiner_type;
	//审批时间
	private Date examine_time;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;
	//上报时间起
	private Date repdateBegin;
	//上报时间起
	private Date repdateEnd;

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
	 * 设置：事件经纬度
	 */
	public String getLat_lon() {
		return lat_lon;
	}
	/**
	 * 获取：事件经纬度
	 */
	public void setLat_lon(String lat_lon) {
		this.lat_lon = lat_lon;
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


	public String getSource_type() {
		return source_type;
	}

	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getAccident_type_id() {
		return accident_type_id;
	}

	public void setAccident_type_id(String accident_type_id) {
		this.accident_type_id = accident_type_id;
	}

	public String getDeptperson_id() {
		return deptperson_id;
	}

	public void setDeptperson_id(String deptperson_id) {
		this.deptperson_id = deptperson_id;
	}

	public String getEarlywarn_id() {
		return earlywarn_id;
	}

	public void setEarlywarn_id(String earlywarn_id) {
		this.earlywarn_id = earlywarn_id;
	}

	/**
	 * 设置：事件级别
	 */
	public void setEventlevel(String eventlevel) {
		this.eventlevel = eventlevel;
	}
	/**
	 * 获取：事件级别
	 */
	public String getEventlevel() {
		return eventlevel;
	}
	/**
	 * 设置：是否暂存
	 */
	public void setTempsaveFlag(Integer tempsaveFlag) {
		this.tempsaveFlag = tempsaveFlag;
	}
	/**
	 * 获取：是否暂存
	 */
	public Integer getTempsaveFlag() {
		return tempsaveFlag;
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

	/**
	 * 获取：接报信息是否受理（0 否 1是）
	 * @return
	 */
	public String getIs_acceptance() {
		return is_acceptance;
	}

	/**
	 * 设置：接报信息是否受理（0 否 1是）
	 * @param is_acceptance
	 */
	public void setIs_acceptance(String is_acceptance) {
		this.is_acceptance = is_acceptance;
	}
	/**
	 * 值守人员的受理方式：上报1、推送5、终结10
	 * @return
	 */
	public String getAcceptance_type() {
		return acceptance_type;
	}

	public void setAcceptance_type(String acceptance_type) {
		this.acceptance_type = acceptance_type;
	}
	/**
	 * 上报审批状态：上报审批中1、推送(同意)5、终结(不同意)10
	 * @return
	 */
	public String getExamine_type() {
		return examine_type;
	}

	public void setExamine_type(String examine_type) {
		this.examine_type = examine_type;
	}
	/**
	 * 上报的审批意见
	 * @return
	 */
	public String getExamine_opinion() {
		return examine_opinion;
	}

	public void setExamine_opinion(String examine_opinion) {
		this.examine_opinion = examine_opinion;
	}

	public String getExaminer_type() {
		return examiner_type;
	}

	public void setExaminer_type(String examiner_type) {
		this.examiner_type = examiner_type;
	}

	public Date getAcceptance_time() {
		return acceptance_time;
	}

	public void setAcceptance_time(Date acceptance_time) {
		this.acceptance_time = acceptance_time;
	}

	public Date getExamine_time() {
		return examine_time;
	}

	public void setExamine_time(Date examine_time) {
		this.examine_time = examine_time;
	}

	public Date getRepdateBegin() {
		return repdateBegin;
	}

	public void setRepdateBegin(Date repdateBegin) {
		this.repdateBegin = repdateBegin;
	}

	public Date getRepdateEnd() {
		return repdateEnd;
	}

	public void setRepdateEnd(Date repdateEnd) {
		this.repdateEnd = repdateEnd;
	}
}
