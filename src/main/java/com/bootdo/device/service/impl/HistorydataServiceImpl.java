package com.bootdo.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.device.dao.HistorydataDao;
import com.bootdo.device.domain.HistorydataDO;
import com.bootdo.device.service.HistorydataService;



@Service
public class HistorydataServiceImpl implements HistorydataService {
	@Autowired
	private HistorydataDao historydataDao;
	
	@Override
	public HistorydataDO get(Integer hdId){
		return historydataDao.get(hdId);
	}
	
	@Override
	public List<HistorydataDO> list(Map<String, Object> map){
		return historydataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return historydataDao.count(map);
	}
	
	@Override
	public int save(HistorydataDO historydata){
		return historydataDao.save(historydata);
	}
	
	@Override
	public int update(HistorydataDO historydata){
		return historydataDao.update(historydata);
	}
	
	@Override
	public int remove(Integer hdId){
		return historydataDao.remove(hdId);
	}
	
	@Override
	public int batchRemove(Integer[] hdIds){
		return historydataDao.batchRemove(hdIds);
	}
	
}
