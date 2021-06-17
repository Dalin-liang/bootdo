package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author oking
 * @email 1992lcg@163.com
 * @date 2019-11-04 16:46:24
 */
public class KnowledgeDangertypeDO implements Serializable {
private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//危险类别名称
	private String dangertypename;
	//排序号
	private Integer sortNo;
	//是否启用
	private Integer enabled;
	//创建时间
	private Date createDate;
	//备注
	private String remarks;

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
	 * 设置：危险类别名称
	 */
	public void setDangertypename(String dangertypename) {
		this.dangertypename = dangertypename;
	}
	/**
	 * 获取：危险类别名称
	 */
	public String getDangertypename() {
		return dangertypename;
	}
	/**
	 * 设置：排序号
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	/**
	 * 获取：排序号
	 */
	public Integer getSortNo() {
		return sortNo;
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
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
