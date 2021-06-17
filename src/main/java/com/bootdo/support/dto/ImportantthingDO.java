package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 要情表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-24 09:27:55
 */
public class ImportantthingDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//填写要情人员所在的部门表ID
	private String deptId;
	//填写要情人员 ：单位人员表ID
	private String deptpersonId;
	//值班日期
	private Date schedulingDate;
	//是否异常
	private Integer isException;
	//值班要情
	private String content;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;
	//备注
	private String remarks;

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
	 * 设置：填写要情人员所在的部门表ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：填写要情人员所在的部门表ID
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：填写要情人员 ：单位人员表ID
	 */
	public void setDeptpersonId(String deptpersonId) {
		this.deptpersonId = deptpersonId;
	}
	/**
	 * 获取：填写要情人员 ：单位人员表ID
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
	 * 设置：值班要情
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：值班要情
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
}
