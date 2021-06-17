package com.bootdo.support.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 物资一级分类表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-30 10:01:07
 */
public class OneleveltypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//物资一级分类名称
	private String name;
	//是否启用
	private Integer enabled;
	//排序编号
	private Integer sortNo;
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
	 * 设置：物资一级分类名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：物资一级分类名称
	 */
	public String getName() {
		return name;
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
	 * 设置：排序编号
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	/**
	 * 获取：排序编号
	 */
	public Integer getSortNo() {
		return sortNo;
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
}
