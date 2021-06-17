package com.bootdo.common.domain;

import java.io.Serializable;

import java.util.Date;



/**
 * 
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-14 14:32:54
 */
public class CommonFileDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//关联Id
	private String relationId;
	//附件类型（照片或视频）
	private Integer fileType;
	//附件地址
	private String fileUrl;
	//附件缩略图地址
	private String smallFileUrl;
	//附件物理地址
	private String filePhysicalAddress;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//关联表名
	private String fromTableanme;

	private String fileName;
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
	 * 设置：关联Id
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	/**
	 * 获取：关联Id
	 */
	public String getRelationId() {
		return relationId;
	}
	/**
	 * 设置：附件类型（照片或视频）
	 */
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	/**
	 * 获取：附件类型（照片或视频）
	 */
	public Integer getFileType() {
		return fileType;
	}
	/**
	 * 设置：附件地址
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	/**
	 * 获取：附件地址
	 */
	public String getFileUrl() {
		return fileUrl;
	}
	/**
	 * 设置：附件缩略图地址
	 */
	public void setSmallFileUrl(String smallFileUrl) {
		this.smallFileUrl = smallFileUrl;
	}
	/**
	 * 获取：附件缩略图地址
	 */
	public String getSmallFileUrl() {
		return smallFileUrl;
	}
	/**
	 * 设置：附件物理地址
	 */
	public void setFilePhysicalAddress(String filePhysicalAddress) {
		this.filePhysicalAddress = filePhysicalAddress;
	}
	/**
	 * 获取：附件物理地址
	 */
	public String getFilePhysicalAddress() {
		return filePhysicalAddress;
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
	/**
	 * 设置：关联表名
	 */
	public void setFromTableanme(String fromTableanme) {
		this.fromTableanme = fromTableanme;
	}
	/**
	 * 获取：关联表名
	 */
	public String getFromTableanme() {
		return fromTableanme;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
