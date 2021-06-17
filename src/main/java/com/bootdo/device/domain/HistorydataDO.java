package com.bootdo.device.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 历史数据
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public class HistorydataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//历史数据ID
	private Integer hdId;
	//设备ID
	private Integer deId;
	//均值
	private Float hdMonitorvalue;
	//登记时间
	private Date hdDatetime;
	//最大值
	private Float hdMaxvalue;
	//最大值时间
	private Date hdMaxtime;
	//最小值
	private Float hdMinivalue;
	//最小值时间
	private Date hdMinitime;
	//监测状态（正常、标定、超标）
	private String hdStatus;
	//备注
	private String hdRemark;

	/**
	 * 设置：历史数据ID
	 */
	public void setHdId(Integer hdId) {
		this.hdId = hdId;
	}
	/**
	 * 获取：历史数据ID
	 */
	public Integer getHdId() {
		return hdId;
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
	 * 设置：均值
	 */
	public void setHdMonitorvalue(Float hdMonitorvalue) {
		this.hdMonitorvalue = hdMonitorvalue;
	}
	/**
	 * 获取：均值
	 */
	public Float getHdMonitorvalue() {
		return hdMonitorvalue;
	}
	/**
	 * 设置：登记时间
	 */
	public void setHdDatetime(Date hdDatetime) {
		this.hdDatetime = hdDatetime;
	}
	/**
	 * 获取：登记时间
	 */
	public Date getHdDatetime() {
		return hdDatetime;
	}
	/**
	 * 设置：最大值
	 */
	public void setHdMaxvalue(Float hdMaxvalue) {
		this.hdMaxvalue = hdMaxvalue;
	}
	/**
	 * 获取：最大值
	 */
	public Float getHdMaxvalue() {
		return hdMaxvalue;
	}
	/**
	 * 设置：最大值时间
	 */
	public void setHdMaxtime(Date hdMaxtime) {
		this.hdMaxtime = hdMaxtime;
	}
	/**
	 * 获取：最大值时间
	 */
	public Date getHdMaxtime() {
		return hdMaxtime;
	}
	/**
	 * 设置：最小值
	 */
	public void setHdMinivalue(Float hdMinivalue) {
		this.hdMinivalue = hdMinivalue;
	}
	/**
	 * 获取：最小值
	 */
	public Float getHdMinivalue() {
		return hdMinivalue;
	}
	/**
	 * 设置：最小值时间
	 */
	public void setHdMinitime(Date hdMinitime) {
		this.hdMinitime = hdMinitime;
	}
	/**
	 * 获取：最小值时间
	 */
	public Date getHdMinitime() {
		return hdMinitime;
	}
	/**
	 * 设置：监测状态（正常、标定、超标）
	 */
	public void setHdStatus(String hdStatus) {
		this.hdStatus = hdStatus;
	}
	/**
	 * 获取：监测状态（正常、标定、超标）
	 */
	public String getHdStatus() {
		return hdStatus;
	}
	/**
	 * 设置：备注
	 */
	public void setHdRemark(String hdRemark) {
		this.hdRemark = hdRemark;
	}
	/**
	 * 获取：备注
	 */
	public String getHdRemark() {
		return hdRemark;
	}
}
