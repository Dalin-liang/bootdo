package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.DailyFileDao;
import com.bootdo.support.dto.DailyFileDO;
import com.bootdo.support.service.DailyFileService;



@Service
public class DailyFileServiceImpl implements DailyFileService {
	@Autowired
	private DailyFileDao dailyFileDao;
	
	@Override
	public DailyFileDO get(String id){
		return dailyFileDao.get(id);
	}
	
	@Override
	public List<DailyFileDO> list(Map<String, Object> map){
		return dailyFileDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dailyFileDao.count(map);
	}
	
	@Override
	public int save(DailyFileDO dailyFile){
		return dailyFileDao.save(dailyFile);
	}
	
	@Override
	public int update(DailyFileDO dailyFile){
		return dailyFileDao.update(dailyFile);
	}
	
	@Override
	public int remove(String id){
		return dailyFileDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dailyFileDao.batchRemove(ids);
	}

	@Override
	public List<DailyFileDO> getByDailyId(String dailyId) {
		return dailyFileDao.getByDailyId(dailyId);
	}

	@Override
	public int removeByDailyId(String dailyId) {
		return dailyFileDao.removeByDailyId(dailyId);
	}

}
