package com.bootdo.newData.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 14:45:32
 */
public class DutyPersonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//分管领导姓名
	private String name;
	//分管领导职位
	private String job;
	//分管领导联系电话
	private String mobile;
	//村居
	private String towns;
	//责任人
	private String duty;
	//责任人职位
	private String dutyjob;
	//责任人联系电话
	private String dutymobile;
	//合作社
	private String coop;
	//合作社责任人
	private String coopduty;
	//合作社职务
	private String coopjob;
	//合作社联系电话
	private String coopmobile;
	//备注
	private String remarks;
	//删除标记
	private Integer deleted;
	//插入时间
	private Date submitdate;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：分管领导姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：分管领导姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：分管领导职位
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * 获取：分管领导职位
	 */
	public String getJob() {
		return job;
	}
	/**
	 * 设置：分管领导联系电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：分管领导联系电话
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：村居
	 */
	public void setTowns(String towns) {
		this.towns = towns;
	}
	/**
	 * 获取：村居
	 */
	public String getTowns() {
		return towns;
	}
	/**
	 * 设置：责任人
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}
	/**
	 * 获取：责任人
	 */
	public String getDuty() {
		return duty;
	}
	/**
	 * 设置：责任人职位
	 */
	public void setDutyjob(String dutyjob) {
		this.dutyjob = dutyjob;
	}
	/**
	 * 获取：责任人职位
	 */
	public String getDutyjob() {
		return dutyjob;
	}
	/**
	 * 设置：责任人联系电话
	 */
	public void setDutymobile(String dutymobile) {
		this.dutymobile = dutymobile;
	}
	/**
	 * 获取：责任人联系电话
	 */
	public String getDutymobile() {
		return dutymobile;
	}
	/**
	 * 设置：合作社
	 */
	public void setCoop(String coop) {
		this.coop = coop;
	}
	/**
	 * 获取：合作社
	 */
	public String getCoop() {
		return coop;
	}
	/**
	 * 设置：合作社责任人
	 */
	public void setCoopduty(String coopduty) {
		this.coopduty = coopduty;
	}
	/**
	 * 获取：合作社责任人
	 */
	public String getCoopduty() {
		return coopduty;
	}
	/**
	 * 设置：合作社职务
	 */
	public void setCoopjob(String coopjob) {
		this.coopjob = coopjob;
	}
	/**
	 * 获取：合作社职务
	 */
	public String getCoopjob() {
		return coopjob;
	}
	/**
	 * 设置：合作社联系电话
	 */
	public void setCoopmobile(String coopmobile) {
		this.coopmobile = coopmobile;
	}
	/**
	 * 获取：合作社联系电话
	 */
	public String getCoopmobile() {
		return coopmobile;
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
	/**
	 * 设置：删除标记
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * 获取：删除标记
	 */
	public Integer getDeleted() {
		return deleted;
	}
	/**
	 * 设置：插入时间
	 */
	public void setSubmitdate(Date submitdate) {
		this.submitdate = submitdate;
	}
	/**
	 * 获取：插入时间
	 */
	public Date getSubmitdate() {
		return submitdate;
	}
}
