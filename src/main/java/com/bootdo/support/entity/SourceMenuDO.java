package com.bootdo.support.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 接报来源详细类目表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:12:25
 */
public class SourceMenuDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private Long id;
	//名称
	private String name;
	//类型
	private String type;
	//watch_source表id
	private String watchSourceId;
	//备注
	private String remarks;

	private Integer enabled;

	private String watchSourceName;

	/**
	 * 设置：唯一主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：唯一主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：watch_source表id
	 */
	public void setWatchSourceId(String watchSourceId) {
		this.watchSourceId = watchSourceId;
	}
	/**
	 * 获取：watch_source表id
	 */
	public String getWatchSourceId() {
		return watchSourceId;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getWatchSourceName() {
		return watchSourceName;
	}

	public void setWatchSourceName(String watchSourceName) {
		this.watchSourceName = watchSourceName;
	}
}
