package com.bootdo.actionprogramManage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务反馈表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-23 09:02:06
 */
public class DispatchTaskFeedbackDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private String id;
	//关联表名
	private String fromTable;
	//反馈时间
	private Date feedbackDate;
	//反馈地点
	private String address;
	//来源类型 0 app 1后台
	private Integer sourceType;
	//反馈人类别 0 用户id 1 姓名
	private Integer personType;
	//应急人员表Id
	private String deptpersonId;
	//逻辑删除标志
	private Integer delFlag;
	//备注
	private String remark;
	//记录录入人
	private String createBy;
	//记录录入时间
	private Date createDate;
	//记录更新人
	private String updateBy;
	//记录更新时间
	private Date updateDate;
	//实际反馈人姓名
	private String deptpesonName;
	//响应部门表id或任务表id
	private String relationId;
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
	 * 设置：关联表名
	 */
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}
	/**
	 * 获取：关联表名
	 */
	public String getFromTable() {
		return fromTable;
	}
	/**
	 * 设置：反馈时间
	 */
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	/**
	 * 获取：反馈时间
	 */
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	/**
	 * 设置：反馈地点
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：反馈地点
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：来源类型 0 app 1后台
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * 获取：来源类型 0 app 1后台
	 */
	public Integer getSourceType() {
		return sourceType;
	}
	/**
	 * 设置：反馈人类别 0 用户id 1 姓名
	 */
	public void setPersonType(Integer personType) {
		this.personType = personType;
	}
	/**
	 * 获取：反馈人类别 0 用户id 1 姓名
	 */
	public Integer getPersonType() {
		return personType;
	}
	/**
	 * 设置：应急人员表Id
	 */
	public void setDeptpersonId(String deptpersonId) {
		this.deptpersonId = deptpersonId;
	}
	/**
	 * 获取：应急人员表Id
	 */
	public String getDeptpersonId() {
		return deptpersonId;
	}
	/**
	 * 设置：逻辑删除标志
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：逻辑删除标志
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：记录录入人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：记录录入人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：记录录入时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：记录录入时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：记录更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：记录更新人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：记录更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：记录更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：实际反馈人姓名
	 */
	public void setDeptpesonName(String deptpesonName) {
		this.deptpesonName = deptpesonName;
	}
	/**
	 * 获取：实际反馈人姓名
	 */
	public String getDeptpesonName() {
		return deptpesonName;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	
}
