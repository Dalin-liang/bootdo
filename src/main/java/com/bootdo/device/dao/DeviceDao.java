package com.bootdo.device.dao;

import com.bootdo.device.domain.DeviceDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 物联设备表
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
@Mapper
public interface DeviceDao {

	DeviceDO get(Integer deId);
	
	List<DeviceDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DeviceDO device);
	
	int update(DeviceDO device);
	
	int remove(Integer de_id);
	
	int batchRemove(Integer[] deIds);
}
