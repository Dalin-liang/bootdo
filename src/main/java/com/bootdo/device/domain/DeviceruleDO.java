package com.bootdo.device.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 设备与报警阈值表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public class DeviceruleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//设备阈值ID
	private Integer drId;
	//所属设备ID
	private Integer deId;
	//预警/报警等级
	private String drGrade;
	//阈值上线
	private Float drUpperlimit;
	//阈值下线
	private Float drLowerlimit;
	//类型（预警、报警）
	private String drTypesof;
	//是否推送至应急平台
	private Integer drIsPush;
	//描述说明
	private String drDescription;
	//备注
	private String drRemark;

	/**
	 * 设置：设备阈值ID
	 */
	public void setDrId(Integer drId) {
		this.drId = drId;
	}
	/**
	 * 获取：设备阈值ID
	 */
	public Integer getDrId() {
		return drId;
	}
	/**
	 * 设置：所属设备ID
	 */
	public void setDeId(Integer deId) {
		this.deId = deId;
	}
	/**
	 * 获取：所属设备ID
	 */
	public Integer getDeId() {
		return deId;
	}
	/**
	 * 设置：预警/报警等级
	 */
	public void setDrGrade(String drGrade) {
		this.drGrade = drGrade;
	}
	/**
	 * 获取：预警/报警等级
	 */
	public String getDrGrade() {
		return drGrade;
	}
	/**
	 * 设置：阈值上线
	 */
	public void setDrUpperlimit(Float drUpperlimit) {
		this.drUpperlimit = drUpperlimit;
	}
	/**
	 * 获取：阈值上线
	 */
	public Float getDrUpperlimit() {
		return drUpperlimit;
	}
	/**
	 * 设置：阈值下线
	 */
	public void setDrLowerlimit(Float drLowerlimit) {
		this.drLowerlimit = drLowerlimit;
	}
	/**
	 * 获取：阈值下线
	 */
	public Float getDrLowerlimit() {
		return drLowerlimit;
	}
	/**
	 * 设置：类型（预警、报警）
	 */
	public void setDrTypesof(String drTypesof) {
		this.drTypesof = drTypesof;
	}
	/**
	 * 获取：类型（预警、报警）
	 */
	public String getDrTypesof() {
		return drTypesof;
	}
	/**
	 * 设置：是否推送至应急平台
	 */
	public void setDrIsPush(Integer drIsPush) {
		this.drIsPush = drIsPush;
	}
	/**
	 * 获取：是否推送至应急平台
	 */
	public Integer getDrIsPush() {
		return drIsPush;
	}
	/**
	 * 设置：描述说明
	 */
	public void setDrDescription(String drDescription) {
		this.drDescription = drDescription;
	}
	/**
	 * 获取：描述说明
	 */
	public String getDrDescription() {
		return drDescription;
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
