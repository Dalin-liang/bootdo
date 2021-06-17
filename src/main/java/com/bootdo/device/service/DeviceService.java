package com.bootdo.device.service;

import com.bootdo.device.domain.DeviceDO;

import java.util.List;
import java.util.Map;

/**
 * 物联设备表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public interface DeviceService {
	
	DeviceDO get(Integer deId);
	
	List<DeviceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeviceDO device);
	
	int update(DeviceDO device);
	
	int remove(Integer deId);
	
	int batchRemove(Integer[] deIds);
}
