package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 防护措施表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public class KnowledgeProtectDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//knowledge_info表id
	private String knowledgeInfoId;
	//呼吸系统防护
	private String respiratory;
	//眼睛防护
	private String eye;
	//身体防护
	private String body;
	//手防护
	private String hand;
	//其它
	private String other;
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
	 * 设置：呼吸系统防护
	 */
	public void setRespiratory(String respiratory) {
		this.respiratory = respiratory;
	}
	/**
	 * 获取：呼吸系统防护
	 */
	public String getRespiratory() {
		return respiratory;
	}
	/**
	 * 设置：眼睛防护
	 */
	public void setEye(String eye) {
		this.eye = eye;
	}
	/**
	 * 获取：眼睛防护
	 */
	public String getEye() {
		return eye;
	}
	/**
	 * 设置：身体防护
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * 获取：身体防护
	 */
	public String getBody() {
		return body;
	}
	/**
	 * 设置：手防护
	 */
	public void setHand(String hand) {
		this.hand = hand;
	}
	/**
	 * 获取：手防护
	 */
	public String getHand() {
		return hand;
	}
	/**
	 * 设置：其它
	 */
	public void setOther(String other) {
		this.other = other;
	}
	/**
	 * 获取：其它
	 */
	public String getOther() {
		return other;
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
