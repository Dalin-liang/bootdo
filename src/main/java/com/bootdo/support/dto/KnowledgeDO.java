package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 知识库
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-13 10:21:50
 */
public class KnowledgeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//预警ID
	private String warnId;
	//分类
	private Integer type;
	//标题
	private String title;
	//来源
	private String source;
	//关键字
	private String keywork;
	//概要
	private String outline;
	//内容
	private String content;
	//备注
	private String remark;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;

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
	 * 设置：预警ID
	 */
	public void setWarnId(String warnId) {
		this.warnId = warnId;
	}
	/**
	 * 获取：预警ID
	 */
	public String getWarnId() {
		return warnId;
	}
	/**
	 * 设置：分类
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：分类
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：来源
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：来源
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置：关键字
	 */
	public void setKeywork(String keywork) {
		this.keywork = keywork;
	}
	/**
	 * 获取：关键字
	 */
	public String getKeywork() {
		return keywork;
	}
	/**
	 * 设置：概要
	 */
	public void setOutline(String outline) {
		this.outline = outline;
	}
	/**
	 * 获取：概要
	 */
	public String getOutline() {
		return outline;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
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
}
