package com.bootdo.newData.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:53:35
 */
public class ProtectPersonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//工程名称
	private String wname;
	//防汛责任人职位
	private String ajob;
	//防汛责任人
	private String aname;
	//防汛责任人联系电话
	private String amobile;
	//技术责任人职位
	private String bjob;
	//技术责任人
	private String bname;
	//技术责任人电话
	private String bmobile;
	//巡查责任人职位
	private String cjob;
	//巡查责任人
	private String cname;
	//巡查责任人电话
	private String cmobile;
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
	 * 设置：工程名称
	 */
	public void setWname(String wname) {
		this.wname = wname;
	}
	/**
	 * 获取：工程名称
	 */
	public String getWname() {
		return wname;
	}
	/**
	 * 设置：防汛责任人职位
	 */
	public void setAjob(String ajob) {
		this.ajob = ajob;
	}
	/**
	 * 获取：防汛责任人职位
	 */
	public String getAjob() {
		return ajob;
	}
	/**
	 * 设置：防汛责任人
	 */
	public void setAname(String aname) {
		this.aname = aname;
	}
	/**
	 * 获取：防汛责任人
	 */
	public String getAname() {
		return aname;
	}
	/**
	 * 设置：防汛责任人联系电话
	 */
	public void setAmobile(String amobile) {
		this.amobile = amobile;
	}
	/**
	 * 获取：防汛责任人联系电话
	 */
	public String getAmobile() {
		return amobile;
	}
	/**
	 * 设置：技术责任人职位
	 */
	public void setBjob(String bjob) {
		this.bjob = bjob;
	}
	/**
	 * 获取：技术责任人职位
	 */
	public String getBjob() {
		return bjob;
	}
	/**
	 * 设置：技术责任人
	 */
	public void setBname(String bname) {
		this.bname = bname;
	}
	/**
	 * 获取：技术责任人
	 */
	public String getBname() {
		return bname;
	}
	/**
	 * 设置：技术责任人电话
	 */
	public void setBmobile(String bmobile) {
		this.bmobile = bmobile;
	}
	/**
	 * 获取：技术责任人电话
	 */
	public String getBmobile() {
		return bmobile;
	}
	/**
	 * 设置：巡查责任人职位
	 */
	public void setCjob(String cjob) {
		this.cjob = cjob;
	}
	/**
	 * 获取：巡查责任人职位
	 */
	public String getCjob() {
		return cjob;
	}
	/**
	 * 设置：巡查责任人
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * 获取：巡查责任人
	 */
	public String getCname() {
		return cname;
	}
	/**
	 * 设置：巡查责任人电话
	 */
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}
	/**
	 * 获取：巡查责任人电话
	 */
	public String getCmobile() {
		return cmobile;
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
