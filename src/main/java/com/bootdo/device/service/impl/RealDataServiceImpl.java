package com.bootdo.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.device.dao.RealDataDao;
import com.bootdo.device.domain.RealDataDO;
import com.bootdo.device.service.RealDataService;



@Service
public class RealDataServiceImpl implements RealDataService {
	@Autowired
	private RealDataDao realDataDao;
	
	@Override
	public RealDataDO get(Integer rdId){
		return realDataDao.get(rdId);
	}
	
	@Override
	public List<RealDataDO> list(Map<String, Object> map){
		return realDataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return realDataDao.count(map);
	}
	
	@Override
	public int save(RealDataDO realData){
		return realDataDao.save(realData);
	}
	
	@Override
	public int update(RealDataDO realData){
		return realDataDao.update(realData);
	}
	
	@Override
	public int remove(Integer rdId){
		return realDataDao.remove(rdId);
	}
	
	@Override
	public int batchRemove(Integer[] rdIds){
		return realDataDao.batchRemove(rdIds);
	}
	
}
