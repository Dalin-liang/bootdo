package com.bootdo.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.device.dao.DeviceDao;
import com.bootdo.device.domain.DeviceDO;
import com.bootdo.device.service.DeviceService;



@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public DeviceDO get(Integer deId){
		return deviceDao.get(deId);
	}
	
	@Override
	public List<DeviceDO> list(Map<String, Object> map){
		return deviceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return deviceDao.count(map);
	}
	
	@Override
	public int save(DeviceDO device){
		return deviceDao.save(device);
	}
	
	@Override
	public int update(DeviceDO device){
		return deviceDao.update(device);
	}
	
	@Override
	public int remove(Integer deId){
		return deviceDao.remove(deId);
	}
	
	@Override
	public int batchRemove(Integer[] deIds){
		return deviceDao.batchRemove(deIds);
	}
	
}
