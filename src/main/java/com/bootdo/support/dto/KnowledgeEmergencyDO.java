package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 应急行动表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public class KnowledgeEmergencyDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//knowledge_info表id
	private String knowledgeInfoId;
	//隔离与公共安全
	private String isolationSafety;
	//泄漏处理
	private String leakageResponse;
	//火灾扑救
	private String fireFighting;
	//急救
	private String emergencyRescue;
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
	 * 设置：隔离与公共安全
	 */
	public void setIsolationSafety(String isolationSafety) {
		this.isolationSafety = isolationSafety;
	}
	/**
	 * 获取：隔离与公共安全
	 */
	public String getIsolationSafety() {
		return isolationSafety;
	}
	/**
	 * 设置：泄漏处理
	 */
	public void setLeakageResponse(String leakageResponse) {
		this.leakageResponse = leakageResponse;
	}
	/**
	 * 获取：泄漏处理
	 */
	public String getLeakageResponse() {
		return leakageResponse;
	}
	/**
	 * 设置：火灾扑救
	 */
	public void setFireFighting(String fireFighting) {
		this.fireFighting = fireFighting;
	}
	/**
	 * 获取：火灾扑救
	 */
	public String getFireFighting() {
		return fireFighting;
	}
	/**
	 * 设置：急救
	 */
	public void setEmergencyRescue(String emergencyRescue) {
		this.emergencyRescue = emergencyRescue;
	}
	/**
	 * 获取：急救
	 */
	public String getEmergencyRescue() {
		return emergencyRescue;
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
