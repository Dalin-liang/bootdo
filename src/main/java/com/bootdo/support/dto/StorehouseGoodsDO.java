package com.bootdo.support.dto;

import java.io.Serializable;

import java.util.Date;



/**
 * 保障库-物资关联表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-21 08:54:16
 */
public class StorehouseGoodsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//保障库表ID
	private String goodsstorehouseId;
	//物资基本信息表ID
	private String goodsinfoId;
	//库存数量
	private Integer inventorynum;

	/**
	 * 设置：保障库表ID
	 */
	public void setGoodsstorehouseId(String goodsstorehouseId) {
		this.goodsstorehouseId = goodsstorehouseId;
	}
	/**
	 * 获取：保障库表ID
	 */
	public String getGoodsstorehouseId() {
		return goodsstorehouseId;
	}
	/**
	 * 设置：物资基本信息表ID
	 */
	public void setGoodsinfoId(String goodsinfoId) {
		this.goodsinfoId = goodsinfoId;
	}
	/**
	 * 获取：物资基本信息表ID
	 */
	public String getGoodsinfoId() {
		return goodsinfoId;
	}
	/**
	 * 设置：库存数量
	 */
	public void setInventorynum(Integer inventorynum) {
		this.inventorynum = inventorynum;
	}
	/**
	 * 获取：库存数量
	 */
	public Integer getInventorynum() {
		return inventorynum;
	}
}
