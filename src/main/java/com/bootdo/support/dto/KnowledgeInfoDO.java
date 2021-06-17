package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 知识库名单表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public class KnowledgeInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//等级/规格
	private String level;
	//保管地点
	private String storagelocation;
	//使用部门
	private String dept;
	//排序序号
	private Integer px;
	//是否启用
	private Integer enabled;
	//备注
	private String remarks;
	//生效日期
	private Date effectiveDate;
	//录入人
	private String createBy;
	//录入时间
	private Date createDate;
	
	//编号
	private String detailCode;
	//名称
	private String detailName;
	//分子式
	private String detailMolecularFormula;
	//危险性类别
	private String dangerType;
	//危险性类别
	private String dangerTypeName;

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
	 * 设置：等级/规格
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 获取：等级/规格
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * 设置：保管地点
	 */
	public void setStoragelocation(String storagelocation) {
		this.storagelocation = storagelocation;
	}
	/**
	 * 获取：保管地点
	 */
	public String getStoragelocation() {
		return storagelocation;
	}
	/**
	 * 设置：使用部门
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * 获取：使用部门
	 */
	public String getDept() {
		return dept;
	}
	/**
	 * 设置：排序序号
	 */
	public void setPx(Integer px) {
		this.px = px;
	}
	/**
	 * 获取：排序序号
	 */
	public Integer getPx() {
		return px;
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
	 * 设置：生效日期
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * 获取：生效日期
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * 设置：录入人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：录入人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：录入时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：录入时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	public String getDetailCode() {
		return detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getDetailName() {
		return detailName;
	}
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	public String getDetailMolecularFormula() {
		return detailMolecularFormula;
	}
	public void setDetailMolecularFormula(String detailMolecularFormula) {
		this.detailMolecularFormula = detailMolecularFormula;
	}
	public String getDangerType() {
		return dangerType;
	}
	public void setDangerType(String dangerType) {
		this.dangerType = dangerType;
	}

	public String getDangerTypeName() {
		return dangerTypeName;
	}

	public void setDangerTypeName(String dangerTypeName) {
		this.dangerTypeName = dangerTypeName;
	}
}
