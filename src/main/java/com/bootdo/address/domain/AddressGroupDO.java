package com.bootdo.address.domain;

import com.bootdo.support.entity.SupportDeptPerson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 通讯录组表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-05-22 11:16:47
 */
public class AddressGroupDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private String id;
	//通讯录组的类型
	private Integer type;
	//通讯录组名
	private String name;
	//是否启用。1:启用；0：禁用
	private Integer enabled;
	//排序号
	private Integer sortno;
	//备注信息
	private String remarks;
	//创建者
	private String createBy;
	//创建时间
	private Date createDate;

	//关联应急人员
	private List<SupportDeptPerson> deptperson;

	/**
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：通讯录组的类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：通讯录组的类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：通讯录组名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：通讯录组名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：是否启用。1:启用；0：禁用
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	/**
	 * 获取：是否启用。1:启用；0：禁用
	 */
	public Integer getEnabled() {
		return enabled;
	}
	/**
	 * 设置：排序号
	 */
	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}
	/**
	 * 获取：排序号
	 */
	public Integer getSortno() {
		return sortno;
	}
	/**
	 * 设置：备注信息
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注信息
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	public List<SupportDeptPerson> getDeptperson() {
		return deptperson;
	}

	public void setDeptperson(List<SupportDeptPerson> deptperson) {
		this.deptperson = deptperson;
	}
}
