package com.bootdo.device.service;

import com.bootdo.device.domain.StationDO;

import java.util.List;
import java.util.Map;

/**
 * 物联设备表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public interface StationService {
	
	StationDO get(Integer stId);
	
	List<StationDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StationDO station);
	
	int update(StationDO station);
	
	int remove(Integer stId);
	
	int batchRemove(Integer[] stIds);
}
