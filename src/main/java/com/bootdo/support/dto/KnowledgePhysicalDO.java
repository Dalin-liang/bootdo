package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 理化性质表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public class KnowledgePhysicalDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//knowledge_info表id
	private String knowledgeInfoId;
	//熔点（℃）
	private Float meltingPoint;
	//沸点（℃）
	private Float boilingPoint;
	//相对湿度（水=1）
	private Float relativeHumidity;
	//相对密度（空气=1）
	private Float relativeDensity;
	//饱和蒸汽压（kPa）
	private Float saturatedVaporPressure;
	//临界温度（℃）
	private Float criticalTemperature;
	//临界压力（Mpa）
	private Float criticalPressure;
	//溶解性
	private String solubility;
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
	 * 设置：knowledge_info表id
	 */
	public void setKnowledgeInfoId(String knowledgeInfoId) {
		this.knowledgeInfoId = knowledgeInfoId;
	}
	/**
	 * 获取：knowledge_info表id
	 */
	public String getKnowledgeInfoId() {
		return knowledgeInfoId;
	}
	/**
	 * 设置：熔点（℃）
	 */
	public void setMeltingPoint(Float meltingPoint) {
		this.meltingPoint = meltingPoint;
	}
	/**
	 * 获取：熔点（℃）
	 */
	public Float getMeltingPoint() {
		return meltingPoint;
	}
	/**
	 * 设置：沸点（℃）
	 */
	public void setBoilingPoint(Float boilingPoint) {
		this.boilingPoint = boilingPoint;
	}
	/**
	 * 获取：沸点（℃）
	 */
	public Float getBoilingPoint() {
		return boilingPoint;
	}
	/**
	 * 设置：相对湿度（水=1）
	 */
	public void setRelativeHumidity(Float relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	/**
	 * 获取：相对湿度（水=1）
	 */
	public Float getRelativeHumidity() {
		return relativeHumidity;
	}
	/**
	 * 设置：相对密度（空气=1）
	 */
	public void setRelativeDensity(Float relativeDensity) {
		this.relativeDensity = relativeDensity;
	}
	/**
	 * 获取：相对密度（空气=1）
	 */
	public Float getRelativeDensity() {
		return relativeDensity;
	}
	/**
	 * 设置：饱和蒸汽压（kPa）
	 */
	public void setSaturatedVaporPressure(Float saturatedVaporPressure) {
		this.saturatedVaporPressure = saturatedVaporPressure;
	}
	/**
	 * 获取：饱和蒸汽压（kPa）
	 */
	public Float getSaturatedVaporPressure() {
		return saturatedVaporPressure;
	}
	/**
	 * 设置：临界温度（℃）
	 */
	public void setCriticalTemperature(Float criticalTemperature) {
		this.criticalTemperature = criticalTemperature;
	}
	/**
	 * 获取：临界温度（℃）
	 */
	public Float getCriticalTemperature() {
		return criticalTemperature;
	}
	/**
	 * 设置：临界压力（Mpa）
	 */
	public void setCriticalPressure(Float criticalPressure) {
		this.criticalPressure = criticalPressure;
	}
	/**
	 * 获取：临界压力（Mpa）
	 */
	public Float getCriticalPressure() {
		return criticalPressure;
	}
	/**
	 * 设置：溶解性
	 */
	public void setSolubility(String solubility) {
		this.solubility = solubility;
	}
	/**
	 * 获取：溶解性
	 */
	public String getSolubility() {
		return solubility;
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
