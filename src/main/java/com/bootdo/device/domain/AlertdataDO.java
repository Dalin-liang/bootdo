package com.bootdo.device.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 预警/报警数据
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public class AlertdataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//预报警ID
	private Integer adId;
	//设备ID
	private Integer deId;
	//报警等级
	private String adGrade;
	//报警类型（预警、报警）
	private String adTypesof;
	//报警值
	private Float adAlertvalue;
	//开始时间
	private Date adBegintime;
	//结束时间
	private Date adEndtime;
	//期间最大值
	private Float adMaxvalue;
	//备注
	private String adReamrk;

	/**
	 * 设置：预报警ID
	 */
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	/**
	 * 获取：预报警ID
	 */
	public Integer getAdId() {
		return adId;
	}
	/**
	 * 设置：设备ID
	 */
	public void setDeId(Integer deId) {
		this.deId = deId;
	}
	/**
	 * 获取：设备ID
	 */
	public Integer getDeId() {
		return deId;
	}
	/**
	 * 设置：报警等级
	 */
	public void setAdGrade(String adGrade) {
		this.adGrade = adGrade;
	}
	/**
	 * 获取：报警等级
	 */
	public String getAdGrade() {
		return adGrade;
	}
	/**
	 * 设置：报警类型（预警、报警）
	 */
	public void setAdTypesof(String adTypesof) {
		this.adTypesof = adTypesof;
	}
	/**
	 * 获取：报警类型（预警、报警）
	 */
	public String getAdTypesof() {
		return adTypesof;
	}
	/**
	 * 设置：报警值
	 */
	public void setAdAlertvalue(Float adAlertvalue) {
		this.adAlertvalue = adAlertvalue;
	}
	/**
	 * 获取：报警值
	 */
	public Float getAdAlertvalue() {
		return adAlertvalue;
	}
	/**
	 * 设置：开始时间
	 */
	public void setAdBegintime(Date adBegintime) {
		this.adBegintime = adBegintime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getAdBegintime() {
		return adBegintime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setAdEndtime(Date adEndtime) {
		this.adEndtime = adEndtime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getAdEndtime() {
		return adEndtime;
	}
	/**
	 * 设置：期间最大值
	 */
	public void setAdMaxvalue(Float adMaxvalue) {
		this.adMaxvalue = adMaxvalue;
	}
	/**
	 * 获取：期间最大值
	 */
	public Float getAdMaxvalue() {
		return adMaxvalue;
	}
	/**
	 * 设置：备注
	 */
	public void setAdReamrk(String adReamrk) {
		this.adReamrk = adReamrk;
	}
	/**
	 * 获取：备注
	 */
	public String getAdReamrk() {
		return adReamrk;
	}
}
