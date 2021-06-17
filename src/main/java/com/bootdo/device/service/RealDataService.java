package com.bootdo.device.service;

import com.bootdo.device.domain.RealDataDO;

import java.util.List;
import java.util.Map;

/**
 * 监测实时
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public interface RealDataService {
	
	RealDataDO get(Integer rdId);
	
	List<RealDataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(RealDataDO realData);
	
	int update(RealDataDO realData);
	
	int remove(Integer rdId);
	
	int batchRemove(Integer[] rdIds);
}
