package com.bootdo.sms.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 短信发送配置表
 *
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 16:15:21
 */
public class SmsSendConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Long id;
	//配置类型(0-接报信息，1-预案，2-方案)
	private String type;
	//配置对象id
	private String targetid;
	//是否发送（0-否，1-是）
	private Integer issend;
	//备注
	private String remarks;
	private String targetName;

	private String code;
	private String Sourceid;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：配置类型(0-接报信息，1-预案，2-方案)
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：配置类型(0-接报信息，1-预案，2-方案)
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：配置对象id
	 */
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
	/**
	 * 获取：配置对象id
	 */
	public String getTargetid() {
		return targetid;
	}
	/**
	 * 设置：是否发送（0-否，1-是）
	 */
	public void setIssend(Integer issend) {
		this.issend = issend;
	}
	/**
	 * 获取：是否发送（0-否，1-是）
	 */
	public Integer getIssend() {
		return issend;
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

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSourceid() {
		return Sourceid;
	}

	public void setSourceid(String sourceid) {
		Sourceid = sourceid;
	}
}
