package com.bootdo.actionprogramManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务反馈明细表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-22 16:48:17
 */
public class DispatchTaskFeedbackDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//任务反馈表Id
	private String feedbackId;
	//反馈类型 （图片、视频、文字、语音）
	private Integer type;
	//反馈文字内容
	private String content;
	//反馈内容访问地址
	private String url;
	//物理地址
	private String physicalAddress;
	//是否大屏可见
	private Integer isVisible;
	//内容排序
	private Integer sort;
	//逻辑删除标记
	private Integer delFlag;
	
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
	 * 设置：任务反馈表Id
	 */
	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	/**
	 * 获取：任务反馈表Id
	 */
	public String getFeedbackId() {
		return feedbackId;
	}
	/**
	 * 设置：反馈类型 （图片、视频、文字、语音）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：反馈类型 （图片、视频、文字、语音）
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：反馈文字内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：反馈文字内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：反馈内容访问地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：反馈内容访问地址
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：物理地址
	 */
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	/**
	 * 获取：物理地址
	 */
	public String getPhysicalAddress() {
		return physicalAddress;
	}
	/**
	 * 设置：是否大屏可见
	 */
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
	/**
	 * 获取：是否大屏可见
	 */
	public Integer getIsVisible() {
		return isVisible;
	}
	/**
	 * 设置：内容排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：内容排序
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：逻辑删除标记
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：逻辑删除标记
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
