package com.bootdo.device.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 物联设备表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public class StationDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//站点ID
	private Integer stId;
	//站点名称
	private String stName;
	//站点代码
	private String stCode;
	//站点相册
	private String stImage;
	//站点地址
	private String stAddress;
	//经度
	private Float stLog;
	//纬度
	private Float stLat;
	//站点类行（水质、消防、视频）
	private String stTypesof;
	//建站时间
	private Date stBuildtime;
	//所属部门
	private String stDepartment;
	//责任人
	private String stResponsible;
	//联系电话
	private String stMobilephone;
	//站点简介
	private String stDescription;
	//备注
	private String stRemark;

	/**
	 * 设置：站点ID
	 */
	public void setStId(Integer stId) {
		this.stId = stId;
	}
	/**
	 * 获取：站点ID
	 */
	public Integer getStId() {
		return stId;
	}
	/**
	 * 设置：站点名称
	 */
	public void setStName(String stName) {
		this.stName = stName;
	}
	/**
	 * 获取：站点名称
	 */
	public String getStName() {
		return stName;
	}
	/**
	 * 设置：站点代码
	 */
	public void setStCode(String stCode) {
		this.stCode = stCode;
	}
	/**
	 * 获取：站点代码
	 */
	public String getStCode() {
		return stCode;
	}
	/**
	 * 设置：站点相册
	 */
	public void setStImage(String stImage) {
		this.stImage = stImage;
	}
	/**
	 * 获取：站点相册
	 */
	public String getStImage() {
		return stImage;
	}
	/**
	 * 设置：站点地址
	 */
	public void setStAddress(String stAddress) {
		this.stAddress = stAddress;
	}
	/**
	 * 获取：站点地址
	 */
	public String getStAddress() {
		return stAddress;
	}
	/**
	 * 设置：经度
	 */
	public void setStLog(Float stLog) {
		this.stLog = stLog;
	}
	/**
	 * 获取：经度
	 */
	public Float getStLog() {
		return stLog;
	}
	/**
	 * 设置：纬度
	 */
	public void setStLat(Float stLat) {
		this.stLat = stLat;
	}
	/**
	 * 获取：纬度
	 */
	public Float getStLat() {
		return stLat;
	}
	/**
	 * 设置：站点类行（水质、消防、视频）
	 */
	public void setStTypesof(String stTypesof) {
		this.stTypesof = stTypesof;
	}
	/**
	 * 获取：站点类行（水质、消防、视频）
	 */
	public String getStTypesof() {
		return stTypesof;
	}
	/**
	 * 设置：建站时间
	 */
	public void setStBuildtime(Date stBuildtime) {
		this.stBuildtime = stBuildtime;
	}
	/**
	 * 获取：建站时间
	 */
	public Date getStBuildtime() {
		return stBuildtime;
	}
	/**
	 * 设置：所属部门
	 */
	public void setStDepartment(String stDepartment) {
		this.stDepartment = stDepartment;
	}
	/**
	 * 获取：所属部门
	 */
	public String getStDepartment() {
		return stDepartment;
	}
	/**
	 * 设置：责任人
	 */
	public void setStResponsible(String stResponsible) {
		this.stResponsible = stResponsible;
	}
	/**
	 * 获取：责任人
	 */
	public String getStResponsible() {
		return stResponsible;
	}
	/**
	 * 设置：联系电话
	 */
	public void setStMobilephone(String stMobilephone) {
		this.stMobilephone = stMobilephone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getStMobilephone() {
		return stMobilephone;
	}
	/**
	 * 设置：站点简介
	 */
	public void setStDescription(String stDescription) {
		this.stDescription = stDescription;
	}
	/**
	 * 获取：站点简介
	 */
	public String getStDescription() {
		return stDescription;
	}
	/**
	 * 设置：备注
	 */
	public void setStRemark(String stRemark) {
		this.stRemark = stRemark;
	}
	/**
	 * 获取：备注
	 */
	public String getStRemark() {
		return stRemark;
	}
}
