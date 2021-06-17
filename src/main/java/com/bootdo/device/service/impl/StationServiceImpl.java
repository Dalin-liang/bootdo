package com.bootdo.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.device.dao.StationDao;
import com.bootdo.device.domain.StationDO;
import com.bootdo.device.service.StationService;



@Service
public class StationServiceImpl implements StationService {
	@Autowired
	private StationDao stationDao;
	
	@Override
	public StationDO get(Integer stId){
		return stationDao.get(stId);
	}
	
	@Override
	public List<StationDO> list(Map<String, Object> map){
		return stationDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return stationDao.count(map);
	}
	
	@Override
	public int save(StationDO station){
		return stationDao.save(station);
	}
	
	@Override
	public int update(StationDO station){
		return stationDao.update(station);
	}
	
	@Override
	public int remove(Integer stId){
		return stationDao.remove(stId);
	}
	
	@Override
	public int batchRemove(Integer[] stIds){
		return stationDao.batchRemove(stIds);
	}
	
}
