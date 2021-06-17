package com.bootdo.sms.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-27 09:56:18
 */
public class SmsSendRecordDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//短信id
	private String smsId;
	//发送时间
	private Date time;
	//发送内容
	private String content;
	//接收人手机号码(单个手机号码)
	private String mobile;
	//短信状态
	private Integer status;
	//短信code
	private String code;
	//短信状态查询时间
	private Date statusTime;
	//关联表的表名
	private String associationTableName;
	//关联表的id
	private String associationTableId;
	//应急执行方案表ID
	private String actionprogramId;

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
	 * 设置：短信id
	 */
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	/**
	 * 获取：短信id
	 */
	public String getSmsId() {
		return smsId;
	}
	/**
	 * 设置：发送时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 获取：发送时间
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * 设置：发送内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：发送内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：接收人手机号码(单个手机号码)
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：接收人手机号码(单个手机号码)
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：短信状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：短信状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：短信code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：短信code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：短信状态查询时间
	 */
	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	/**
	 * 获取：短信状态查询时间
	 */
	public Date getStatusTime() {
		return statusTime;
	}
	/**
	 * 设置：关联表的表名
	 */
	public void setAssociationTableName(String associationTableName) {
		this.associationTableName = associationTableName;
	}
	/**
	 * 获取：关联表的表名
	 */
	public String getAssociationTableName() {
		return associationTableName;
	}
	/**
	 * 设置：关联表的id
	 */
	public void setAssociationTableId(String associationTableId) {
		this.associationTableId = associationTableId;
	}
	/**
	 * 获取：关联表的id
	 */
	public String getAssociationTableId() {
		return associationTableId;
	}
	/**
	 * 设置：应急执行方案表ID
	 */
	public void setActionprogramId(String actionprogramId) {
		this.actionprogramId = actionprogramId;
	}
	/**
	 * 获取：应急执行方案表ID
	 */
	public String getActionprogramId() {
		return actionprogramId;
	}
}
