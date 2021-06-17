package com.bootdo.planManage.domain;

import com.bootdo.support.entity.SupportExpertInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 预警类别
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public class PlanEarlywarnTypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//预警类别名称
	private String name;
	//事故类型表ID
	private String accidentTypeId;
	//状态 ：新增、使用中、停用、启用
	private Integer status;
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
	//对应的专家
	private List<SupportExpertInfo> experInfo;

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
	 * 设置：预警类别名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：预警类别名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：事故类型表ID
	 */
	public void setAccidentTypeId(String accidentTypeId) {
		this.accidentTypeId = accidentTypeId;
	}
	/**
	 * 获取：事故类型表ID
	 */
	public String getAccidentTypeId() {
		return accidentTypeId;
	}
	/**
	 * 设置：状态 ：新增、使用中、停用、启用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 ：新增、使用中、停用、启用
	 */
	public Integer getStatus() {
		return status;
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


	public List<SupportExpertInfo> getExperInfo() {
		return experInfo;
	}

	public void setExperInfo(List<SupportExpertInfo> experInfo) {
		this.experInfo = experInfo;
	}
}
