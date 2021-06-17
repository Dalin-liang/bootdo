package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 危险性表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:45
 */
public class KnowledgeDangerDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//knowledge_info表id
	private String knowledgeInfoId;
	//危险性类别
	private String dangerType;
	//危险性
	private String dangerCharacteristic;
	//健康危害
	private String healthHarm;
	//环境影响
	private String environmentalImpact;
	//创建时间
	private Date createDate;

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
	 * 设置：危险性类别
	 */
	public void setDangerType(String dangerType) {
		this.dangerType = dangerType;
	}
	/**
	 * 获取：危险性类别
	 */
	public String getDangerType() {
		return dangerType;
	}
	/**
	 * 设置：危险性
	 */
	public void setDangerCharacteristic(String dangerCharacteristic) {
		this.dangerCharacteristic = dangerCharacteristic;
	}
	/**
	 * 获取：危险性
	 */
	public String getDangerCharacteristic() {
		return dangerCharacteristic;
	}
	/**
	 * 设置：健康危害
	 */
	public void setHealthHarm(String healthHarm) {
		this.healthHarm = healthHarm;
	}
	/**
	 * 获取：健康危害
	 */
	public String getHealthHarm() {
		return healthHarm;
	}
	/**
	 * 设置：环境影响
	 */
	public void setEnvironmentalImpact(String environmentalImpact) {
		this.environmentalImpact = environmentalImpact;
	}
	/**
	 * 获取：环境影响
	 */
	public String getEnvironmentalImpact() {
		return environmentalImpact;
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
}
