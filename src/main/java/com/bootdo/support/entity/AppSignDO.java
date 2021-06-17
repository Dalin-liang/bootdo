package com.bootdo.support.entity;

import java.io.Serializable;



/**
 * 签到表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-15 09:44:23
 */
public class AppSignDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//用户名
	private String userid;
	//网格区域（行政区域）编码
	private String areacode;
	//中文名
	private String cname;
	//签到时间
	private String signtime;
	//纬度
	private String lat;
	//经度
	private String lon;
	//地址
	private String address;
	//累计签到次数
	private Integer ljcount;
	//签到照片路径
	private String qdzplj;
	//是否计入签到  1不计入
	private String jrqd;
	//签到类型 1早上2下午
	private Integer signType;
	//1早班2中班3晚班4其他
	private Integer workType;

	private SupportDeptPerson person;

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
	 * 设置：用户名
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户名
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * 设置：网格区域（行政区域）编码
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	/**
	 * 获取：网格区域（行政区域）编码
	 */
	public String getAreacode() {
		return areacode;
	}
	/**
	 * 设置：中文名
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * 获取：中文名
	 */
	public String getCname() {
		return cname;
	}
	/**
	 * 设置：签到时间
	 */
	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}
	/**
	 * 获取：签到时间
	 */
	public String getSigntime() {
		return signtime;
	}
	/**
	 * 设置：纬度
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * 获取：纬度
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * 设置：经度
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	/**
	 * 获取：经度
	 */
	public String getLon() {
		return lon;
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
	 * 设置：累计签到次数
	 */
	public void setLjcount(Integer ljcount) {
		this.ljcount = ljcount;
	}
	/**
	 * 获取：累计签到次数
	 */
	public Integer getLjcount() {
		return ljcount;
	}
	/**
	 * 设置：签到照片路径
	 */
	public void setQdzplj(String qdzplj) {
		this.qdzplj = qdzplj;
	}
	/**
	 * 获取：签到照片路径
	 */
	public String getQdzplj() {
		return qdzplj;
	}
	/**
	 * 设置：是否计入签到  1不计入
	 */
	public void setJrqd(String jrqd) {
		this.jrqd = jrqd;
	}
	/**
	 * 获取：是否计入签到  1不计入
	 */
	public String getJrqd() {
		return jrqd;
	}
	/**
	 * 设置：签到类型 1早上2下午
	 */
	public void setSignType(Integer signType) {
		this.signType = signType;
	}
	/**
	 * 获取：签到类型 1早上2下午
	 */
	public Integer getSignType() {
		return signType;
	}
	/**
	 * 设置：1早班2中班3晚班4其他
	 */
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	/**
	 * 获取：1早班2中班3晚班4其他
	 */
	public Integer getWorkType() {
		return workType;
	}

	public SupportDeptPerson getPerson() {
		return person;
	}

	public void setPerson(SupportDeptPerson person) {
		this.person = person;
	}
}
