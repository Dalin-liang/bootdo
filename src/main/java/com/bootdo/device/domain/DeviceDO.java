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
public class DeviceDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//设备ID
	private Integer deId;
	//所属站点ID
	private Integer stId;
	//设备名称
	private String deName;
	//设备术语
	private String deTerminology;
	//设备代码
	private String deCode;
	//通讯接口
	private String deInterface;
	//通讯端口
	private String dePort;
	//通讯账号
	private String deLogin;
	//通讯密码
	private String dePassword;
	//通讯通道
	private String deChannel;
	//设备分类（水质、噪音、气象、视频）
	private String deTypesof;
	//监测数据单位
	private String deUnit;
	//维护人员
	private String deMaintain;
	//维护电话
	private String deMobilephone;
	//设备简介
	private String deDescription;
	//备注
	private String deRemark;

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
	 * 设置：所属站点ID
	 */
	public void setStId(Integer stId) {
		this.stId = stId;
	}
	/**
	 * 获取：所属站点ID
	 */
	public Integer getStId() {
		return stId;
	}
	/**
	 * 设置：设备名称
	 */
	public void setDeName(String deName) {
		this.deName = deName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getDeName() {
		return deName;
	}
	/**
	 * 设置：设备术语
	 */
	public void setDeTerminology(String deTerminology) {
		this.deTerminology = deTerminology;
	}
	/**
	 * 获取：设备术语
	 */
	public String getDeTerminology() {
		return deTerminology;
	}
	/**
	 * 设置：设备代码
	 */
	public void setDeCode(String deCode) {
		this.deCode = deCode;
	}
	/**
	 * 获取：设备代码
	 */
	public String getDeCode() {
		return deCode;
	}
	/**
	 * 设置：通讯接口
	 */
	public void setDeInterface(String deInterface) {
		this.deInterface = deInterface;
	}
	/**
	 * 获取：通讯接口
	 */
	public String getDeInterface() {
		return deInterface;
	}
	/**
	 * 设置：通讯端口
	 */
	public void setDePort(String dePort) {
		this.dePort = dePort;
	}
	/**
	 * 获取：通讯端口
	 */
	public String getDePort() {
		return dePort;
	}
	/**
	 * 设置：通讯账号
	 */
	public void setDeLogin(String deLogin) {
		this.deLogin = deLogin;
	}
	/**
	 * 获取：通讯账号
	 */
	public String getDeLogin() {
		return deLogin;
	}
	/**
	 * 设置：通讯密码
	 */
	public void setDePassword(String dePassword) {
		this.dePassword = dePassword;
	}
	/**
	 * 获取：通讯密码
	 */
	public String getDePassword() {
		return dePassword;
	}
	/**
	 * 设置：通讯通道
	 */
	public void setDeChannel(String deChannel) {
		this.deChannel = deChannel;
	}
	/**
	 * 获取：通讯通道
	 */
	public String getDeChannel() {
		return deChannel;
	}
	/**
	 * 设置：设备分类（水质、噪音、气象、视频）
	 */
	public void setDeTypesof(String deTypesof) {
		this.deTypesof = deTypesof;
	}
	/**
	 * 获取：设备分类（水质、噪音、气象、视频）
	 */
	public String getDeTypesof() {
		return deTypesof;
	}
	/**
	 * 设置：监测数据单位
	 */
	public void setDeUnit(String deUnit) {
		this.deUnit = deUnit;
	}
	/**
	 * 获取：监测数据单位
	 */
	public String getDeUnit() {
		return deUnit;
	}
	/**
	 * 设置：维护人员
	 */
	public void setDeMaintain(String deMaintain) {
		this.deMaintain = deMaintain;
	}
	/**
	 * 获取：维护人员
	 */
	public String getDeMaintain() {
		return deMaintain;
	}
	/**
	 * 设置：维护电话
	 */
	public void setDeMobilephone(String deMobilephone) {
		this.deMobilephone = deMobilephone;
	}
	/**
	 * 获取：维护电话
	 */
	public String getDeMobilephone() {
		return deMobilephone;
	}
	/**
	 * 设置：设备简介
	 */
	public void setDeDescription(String deDescription) {
		this.deDescription = deDescription;
	}
	/**
	 * 获取：设备简介
	 */
	public String getDeDescription() {
		return deDescription;
	}
	/**
	 * 设置：备注
	 */
	public void setDeRemark(String deRemark) {
		this.deRemark = deRemark;
	}
	/**
	 * 获取：备注
	 */
	public String getDeRemark() {
		return deRemark;
	}
}
