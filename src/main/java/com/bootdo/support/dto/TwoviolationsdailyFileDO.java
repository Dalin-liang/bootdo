package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 两违日志附件表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 14:13:35
 */
public class TwoviolationsdailyFileDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//safeguard_twoviolationsdaily表id
	private String twoviolationsdailyId;
	//文件类型(0-图片，1-文件，2-视频，3-其他)
	private Integer type;
	//URL地址
	private String url;
	//创建时间
	private Date createDate;
	//物理地址
	private String physicalAddress;
	//内容排序
	private Integer sort;
	//逻辑删除标记
	private Integer delFlag;

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
	 * 设置：safeguard_twoviolationsdaily表id
	 */
	public void setTwoviolationsdailyId(String twoviolationsdailyId) {
		this.twoviolationsdailyId = twoviolationsdailyId;
	}
	/**
	 * 获取：safeguard_twoviolationsdaily表id
	 */
	public String getTwoviolationsdailyId() {
		return twoviolationsdailyId;
	}
	/**
	 * 设置：文件类型(0-图片，1-文件，2-视频，3-其他)
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：文件类型(0-图片，1-文件，2-视频，3-其他)
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：URL地址
	 */
	public String getUrl() {
		return url;
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
}
