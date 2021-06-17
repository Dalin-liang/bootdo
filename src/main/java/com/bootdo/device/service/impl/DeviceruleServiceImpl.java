package com.bootdo.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.device.dao.DeviceruleDao;
import com.bootdo.device.domain.DeviceruleDO;
import com.bootdo.device.service.DeviceruleService;



@Service
public class DeviceruleServiceImpl implements DeviceruleService {
	@Autowired
	private DeviceruleDao deviceruleDao;
	
	@Override
	public DeviceruleDO get(Integer drId){
		return deviceruleDao.get(drId);
	}
	
	@Override
	public List<DeviceruleDO> list(Map<String, Object> map){
		return deviceruleDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return deviceruleDao.count(map);
	}
	
	@Override
	public int save(DeviceruleDO devicerule){
		return deviceruleDao.save(devicerule);
	}
	
	@Override
	public int update(DeviceruleDO devicerule){
		return deviceruleDao.update(devicerule);
	}
	
	@Override
	public int remove(Integer drId){
		return deviceruleDao.remove(drId);
	}
	
	@Override
	public int batchRemove(Integer[] drIds){
		return deviceruleDao.batchRemove(drIds);
	}
	
}
