package com.bootdo.actionprogramManage.domain;

import java.util.Date;

public class Warning {
	 private String icon;// 图标
	 private String title1;// 台风蓝色预警信号
	 private String title2;// 珠海气象台2012年6月6日6:54发布
	 private String content;// 信号含义
	 private String content2;// 防御指南
	 private Date valid;
	 private String video;// 视频
	 private String typhoonTitle;// 台风 2012/nNO.6
	 private String typhoonContent;// 台风 的详细内容
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public Date getValid() {
		return valid;
	}
	public void setValid(Date valid) {
		this.valid = valid;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getTyphoonTitle() {
		return typhoonTitle;
	}
	public void setTyphoonTitle(String typhoonTitle) {
		this.typhoonTitle = typhoonTitle;
	}
	public String getTyphoonContent() {
		return typhoonContent;
	}
	public void setTyphoonContent(String typhoonContent) {
		this.typhoonContent = typhoonContent;
	}
	 
	 
}
