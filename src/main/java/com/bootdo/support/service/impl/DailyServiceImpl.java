package com.bootdo.support.service.impl;

import com.bootdo.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.DailyDao;
import com.bootdo.support.dto.DailyDO;
import com.bootdo.support.service.DailyService;



@Service
public class DailyServiceImpl implements DailyService {
	@Autowired
	private DailyDao dailyDao;
	
	@Override
	public DailyDO get(String id){
		return dailyDao.get(id);
	}
	
	@Override
	public List<DailyDO> list(Map<String, Object> map){
		return dailyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dailyDao.count(map);
	}
	
	@Override
	public int save(DailyDO daily){
		return dailyDao.save(daily);
	}
	
	@Override
	public int update(DailyDO daily){
		return dailyDao.update(daily);
	}
	
	@Override
	public int remove(String id){
		return dailyDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dailyDao.batchRemove(ids);
	}

	@Override
	public int getUserCount(Map<String,Object> params) {
		return dailyDao.getUserCount(params);
	}

	@Override
	public List<Map<String, Object>> getUser(Map<String,Object> params) {
		// TODO Auto-generated method stub
		return dailyDao.getUser(params);
	}
	
}
