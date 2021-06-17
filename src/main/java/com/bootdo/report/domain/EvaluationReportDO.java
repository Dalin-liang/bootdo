package com.bootdo.report.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-04-02 16:27:24
 */
public class EvaluationReportDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//所属的结案事件Id
	private String archiveActionprogramMainId;
	//事件里面的主要内容
	private String earlywarnContent;
	//上报对象类型(单位或个人)
	private Integer reportObjecttype;
	//上传日期
	private Date reportTime;
	//备注
	private String remarks;
	//上传人
	private String reportPerson;
	//报告编码
	private String reportCode;
	//关键字
	private String keyword;
	//状态
	private String status;

	private String fileList;


	private String planmainName; //预案名称
	private String repname; //上报人名称
	private String repdate; //上报日期
	private String accidentTypeName; //事件名称
	private String earlywarnTypeName; //预警类型
	private String eventlevelName; //预警级别


	/**
	 * 设置：唯一主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：唯一主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：所属的结案事件Id
	 */
	public void setArchiveActionprogramMainId(String archiveActionprogramMainId) {
		this.archiveActionprogramMainId = archiveActionprogramMainId;
	}
	/**
	 * 获取：所属的结案事件Id
	 */
	public String getArchiveActionprogramMainId() {
		return archiveActionprogramMainId;
	}
	/**
	 * 设置：事件里面的主要内容
	 */
	public void setEarlywarnContent(String earlywarnContent) {
		this.earlywarnContent = earlywarnContent;
	}
	/**
	 * 获取：事件里面的主要内容
	 */
	public String getEarlywarnContent() {
		return earlywarnContent;
	}
	/**
	 * 设置：上报对象类型(单位或个人)
	 */
	public void setReportObjecttype(Integer reportObjecttype) {
		this.reportObjecttype = reportObjecttype;
	}
	/**
	 * 获取：上报对象类型(单位或个人)
	 */
	public Integer getReportObjecttype() {
		return reportObjecttype;
	}
	/**
	 * 设置：上传日期
	 */
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	/**
	 * 获取：上传日期
	 */
	public Date getReportTime() {
		return reportTime;
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
	 * 设置：上传人
	 */
	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
	}
	/**
	 * 获取：上传人
	 */
	public String getReportPerson() {
		return reportPerson;
	}
	/**
	 * 设置：报告编码
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	/**
	 * 获取：报告编码
	 */
	public String getReportCode() {
		return reportCode;
	}
	/**
	 * 设置：关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * 获取：关键字
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}

	public String getPlanmainName() {
		return planmainName;
	}

	public void setPlanmainName(String planmainName) {
		this.planmainName = planmainName;
	}


	public String getRepname() {
		return repname;
	}

	public void setRepname(String repname) {
		this.repname = repname;
	}

	public String getRepdate() {
		return repdate;
	}

	public void setRepdate(String repdate) {
		this.repdate = repdate;
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

	public String getEventlevelName() {
		return eventlevelName;
	}

	public void setEventlevelName(String eventlevelName) {
		this.eventlevelName = eventlevelName;
	}
}
