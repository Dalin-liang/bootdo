package com.bootdo.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageQuery extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	// 
	private int offset;
	// 每页条数
	private int limit;

	public PageQuery(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		int page = Integer.parseInt(params.get("page").toString());	
		this.limit = Integer.parseInt(params.get("limit").toString());	
		this.offset = (page-1)*this.limit;
		this.put("offset", offset);
		this.put("page", page);
		this.put("limit", limit);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.put("offset", offset);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
