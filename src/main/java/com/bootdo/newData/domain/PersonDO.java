package com.bootdo.newData.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:11:07
 */
public class PersonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//村名
	private String towns;
	//姓名
	private String name;
	//性别
	private String sex;
	//年龄
	private Integer age;
	//地址
	private String address;
	//家属姓名
	private String fname;
	//联系方式
	private String mobile;
	//村干部姓名
	private String tname;
	//村干部联系电话
	private String tmobile;
	//安置地点
	private String point;
	//删除标记
	private Integer deleted;
	//插入时间
	private Date submitdate;
	//备注
	private String remarks;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：村名
	 */
	public void setTowns(String towns) {
		this.towns = towns;
	}
	/**
	 * 获取：村名
	 */
	public String getTowns() {
		return towns;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：家属姓名
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * 获取：家属姓名
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * 设置：联系方式
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：联系方式
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：村干部姓名
	 */
	public void setTname(String tname) {
		this.tname = tname;
	}
	/**
	 * 获取：村干部姓名
	 */
	public String getTname() {
		return tname;
	}
	/**
	 * 设置：村干部联系电话
	 */
	public void setTmobile(String tmobile) {
		this.tmobile = tmobile;
	}
	/**
	 * 获取：村干部联系电话
	 */
	public String getTmobile() {
		return tmobile;
	}
	/**
	 * 设置：安置地点
	 */
	public void setPoint(String point) {
		this.point = point;
	}
	/**
	 * 获取：安置地点
	 */
	public String getPoint() {
		return point;
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
