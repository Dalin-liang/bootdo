package com.bootdo.sms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SmsConfig {
	@Value("${sms.config.endpoint}")
    private String endpoint;
	
	@Value("${sms.config.uid}")
    private String uid;
	
	@Value("${sms.config.password}")
    private String password;
	
	@Value("${sms.config.open}")
    private boolean open;

	@Value("#{'${sms.config.blockList}'.split(',')}")
	private List<String> blockList;
	
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<String> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<String> blockList) {
		this.blockList = blockList;
	}

}
