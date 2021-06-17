package com.bootdo.planManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 预警级别
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:49
 */
public class PlanEarlywarnLevelDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//预警类别表ID
	private String earlywarnTypeId;
	//事故类型表ID
	private String accidentTypeId;
	//预警级别名称
	private String name;
	//划分标准
	private String standards;
	//状态 ：新增、使用中、停用、启用
	private Integer status;
	//更新时间
	private Date updateDate;
	//事故类型名称
	private String accidentTypeName;
	//预警类型名称
	private String earlywarnTypeName;

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
	 * 设置：预警类别表ID
	 */
	public void setEarlywarnTypeId(String earlywarnTypeId) {
		this.earlywarnTypeId = earlywarnTypeId;
	}
	/**
	 * 获取：预警类别表ID
	 */
	public String getEarlywarnTypeId() {
		return earlywarnTypeId;
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
	 * 设置：预警级别
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：预警级别
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：划分标准
	 */
	public void setStandards(String standards) {
		this.standards = standards;
	}
	/**
	 * 获取：划分标准
	 */
	public String getStandards() {
		return standards;
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
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
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
	
	
}
