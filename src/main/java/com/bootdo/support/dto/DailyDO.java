package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 值班日报/要情编辑
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-09 17:24:25
 */
public class DailyDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//值班人员 ：单位人员表ID
	private String deptpersonId;
	//值班日期
	private Date schedulingDate;
	//是否异常
	private Integer isException;
	//日报/要情
	private String content;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;
	
	private Integer isPersonInCharge;

	private String deptId;

	private String fileList;

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
	 * 设置：值班人员 ：单位人员表ID
	 */
	public void setDeptpersonId(String deptpersonId) {
		this.deptpersonId = deptpersonId;
	}
	/**
	 * 获取：值班人员 ：单位人员表ID
	 */
	public String getDeptpersonId() {
		return deptpersonId;
	}
	/**
	 * 设置：值班日期
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}
	/**
	 * 获取：值班日期
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}
	/**
	 * 设置：是否异常
	 */
	public void setIsException(Integer isException) {
		this.isException = isException;
	}
	/**
	 * 获取：是否异常
	 */
	public Integer getIsException() {
		return isException;
	}
	/**
	 * 设置：日报/要情
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：日报/要情
	 */
	public String getContent() {
		return content;
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
	public Integer getIsPersonInCharge() {
		return isPersonInCharge;
	}
	public void setIsPersonInCharge(Integer isPersonInCharge) {
		this.isPersonInCharge = isPersonInCharge;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
}
