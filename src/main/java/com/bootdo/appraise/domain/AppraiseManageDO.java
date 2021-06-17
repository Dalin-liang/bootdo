package com.bootdo.appraise.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-04-02 10:15:36
 */
public class AppraiseManageDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private String id;
	//预案id
	private String planid;
	//应急能力
	private String emergency;
	//及时性
	private String timely;
	//有效性
	private String effective;
	//评估人
	private String person;
	//评估时间
	private Date time;

	private String name;

	private String start_condition;

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：预案id
	 */
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	/**
	 * 获取：预案id
	 */
	public String getPlanid() {
		return planid;
	}
	/**
	 * 设置：应急能力
	 */
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	/**
	 * 获取：应急能力
	 */
	public String getEmergency() {
		return emergency;
	}
	/**
	 * 设置：及时性
	 */
	public void setTimely(String timely) {
		this.timely = timely;
	}
	/**
	 * 获取：及时性
	 */
	public String getTimely() {
		return timely;
	}
	/**
	 * 设置：有效性
	 */
	public void setEffective(String effective) {
		this.effective = effective;
	}
	/**
	 * 获取：有效性
	 */
	public String getEffective() {
		return effective;
	}
	/**
	 * 设置：评估人
	 */
	public void setPerson(String person) {
		this.person = person;
	}
	/**
	 * 获取：评估人
	 */
	public String getPerson() {
		return person;
	}
	/**
	 * 设置：评估时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 获取：评估时间
	 */
	public Date getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_condition() {
		return start_condition;
	}

	public void setStart_condition(String start_condition) {
		this.start_condition = start_condition;
	}
}
