package com.bootdo.baiyi.domain;

import java.io.Serializable;


/**
 * 位置数据
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-09-16 10:11:24
 */
public class PushdataLocationDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//15位设备唯一序号
	private String imei;
	//发生时间YYYY-MM-DD HH:mm:SS
	private String timeBegin;
	//是否为响应
	private Integer isReply;
	//是否轨迹
	private Integer isTrack;
	//城市
	private String city;
	//地址
	private String address;
	//经度
	private Double lon;
	//纬度
	private Double lat;
	//类型   0:Gps定位; 1:基站定位
	private String type;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：15位设备唯一序号
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * 获取：15位设备唯一序号
	 */
	public String getImei() {
		return imei;
	}
	/**
	 * 设置：发生时间YYYY-MM-DD HH:mm:SS
	 */
	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}
	/**
	 * 获取：发生时间YYYY-MM-DD HH:mm:SS
	 */
	public String getTimeBegin() {
		return timeBegin;
	}
	/**
	 * 设置：是否为响应
	 */
	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}
	/**
	 * 获取：是否为响应
	 */
	public Integer getIsReply() {
		return isReply;
	}
	/**
	 * 设置：是否轨迹
	 */
	public void setIsTrack(Integer isTrack) {
		this.isTrack = isTrack;
	}
	/**
	 * 获取：是否轨迹
	 */
	public Integer getIsTrack() {
		return isTrack;
	}
	/**
	 * 设置：城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：经度
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}
	/**
	 * 获取：经度
	 */
	public Double getLon() {
		return lon;
	}
	/**
	 * 设置：纬度
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}
	/**
	 * 获取：纬度
	 */
	public Double getLat() {
		return lat;
	}
	/**
	 * 设置：类型   0:Gps定位; 1:基站定位
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型   0:Gps定位; 1:基站定位
	 */
	public String getType() {
		return type;
	}

}
