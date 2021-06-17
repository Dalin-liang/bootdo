package com.bootdo.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.device.dao.AlertdataDao;
import com.bootdo.device.domain.AlertdataDO;
import com.bootdo.device.service.AlertdataService;



@Service
public class AlertdataServiceImpl implements AlertdataService {
	@Autowired
	private AlertdataDao alertdataDao;
	
	@Override
	public AlertdataDO get(Integer adId){
		return alertdataDao.get(adId);
	}
	
	@Override
	public List<AlertdataDO> list(Map<String, Object> map){
		return alertdataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return alertdataDao.count(map);
	}
	
	@Override
	public int save(AlertdataDO alertdata){
		return alertdataDao.save(alertdata);
	}
	
	@Override
	public int update(AlertdataDO alertdata){
		return alertdataDao.update(alertdata);
	}
	
	@Override
	public int remove(Integer adId){
		return alertdataDao.remove(adId);
	}
	
	@Override
	public int batchRemove(Integer[] adIds){
		return alertdataDao.batchRemove(adIds);
	}
	
}
