package com.bootdo.device.service;

import com.bootdo.device.domain.DeviceruleDO;

import java.util.List;
import java.util.Map;

/**
 * 设备与报警阈值表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public interface DeviceruleService {
	
	DeviceruleDO get(Integer drId);
	
	List<DeviceruleDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeviceruleDO devicerule);
	
	int update(DeviceruleDO devicerule);
	
	int remove(Integer drId);
	
	int batchRemove(Integer[] drIds);
}
