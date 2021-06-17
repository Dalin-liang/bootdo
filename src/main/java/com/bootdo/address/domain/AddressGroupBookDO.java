package com.bootdo.address.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * ADDRESS_GROUP_BOOK通讯录组和通讯录的关联表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-05-22 14:40:35
 */
public class AddressGroupBookDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//通讯录组address_group表的id
	private String groupid;
	//通讯录safeguard_deptperson表的id
	private String bookid;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：通讯录组address_group表的id
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	/**
	 * 获取：通讯录组address_group表的id
	 */
	public String getGroupid() {
		return groupid;
	}
	/**
	 * 设置：通讯录safeguard_deptperson表的id
	 */
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	/**
	 * 获取：通讯录safeguard_deptperson表的id
	 */
	public String getBookid() {
		return bookid;
	}
}
