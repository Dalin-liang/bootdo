package com.bootdo.device.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 监测实时
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public class RealDataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//实时数据ID
	private Integer rdId;
	//设备ID
	private Integer deId;
	//监测数据
	private Float drValue;
	//监测时间
	private Date drMonitortime;
	//监测状态（正常、标定、超标）
	private String drStatus;
	//备注
	private String drRemark;

	/**
	 * 设置：实时数据ID
	 */
	public void setRdId(Integer rdId) {
		this.rdId = rdId;
	}
	/**
	 * 获取：实时数据ID
	 */
	public Integer getRdId() {
		return rdId;
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
	 * 设置：监测数据
	 */
	public void setDrValue(Float drValue) {
		this.drValue = drValue;
	}
	/**
	 * 获取：监测数据
	 */
	public Float getDrValue() {
		return drValue;
	}
	/**
	 * 设置：监测时间
	 */
	public void setDrMonitortime(Date drMonitortime) {
		this.drMonitortime = drMonitortime;
	}
	/**
	 * 获取：监测时间
	 */
	public Date getDrMonitortime() {
		return drMonitortime;
	}
	/**
	 * 设置：监测状态（正常、标定、超标）
	 */
	public void setDrStatus(String drStatus) {
		this.drStatus = drStatus;
	}
	/**
	 * 获取：监测状态（正常、标定、超标）
	 */
	public String getDrStatus() {
		return drStatus;
	}
	/**
	 * 设置：备注
	 */
	public void setDrRemark(String drRemark) {
		this.drRemark = drRemark;
	}
	/**
	 * 获取：备注
	 */
	public String getDrRemark() {
		return drRemark;
	}
}
