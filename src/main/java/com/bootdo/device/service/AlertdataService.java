package com.bootdo.device.service;

import com.bootdo.device.domain.AlertdataDO;

import java.util.List;
import java.util.Map;

/**
 * 预警/报警数据
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public interface AlertdataService {
	
	AlertdataDO get(Integer adId);
	
	List<AlertdataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AlertdataDO alertdata);
	
	int update(AlertdataDO alertdata);
	
	int remove(Integer adId);
	
	int batchRemove(Integer[] adIds);
}
