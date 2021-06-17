package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.ImportantthingDao;
import com.bootdo.support.dto.ImportantthingDO;
import com.bootdo.support.service.ImportantthingService;



@Service
public class ImportantthingServiceImpl implements ImportantthingService {
	@Autowired
	private ImportantthingDao importantthingDao;
	
	@Override
	public ImportantthingDO get(String id){
		return importantthingDao.get(id);
	}
	
	@Override
	public List<ImportantthingDO> list(Map<String, Object> map){
		return importantthingDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return importantthingDao.count(map);
	}
	
	@Override
	public int save(ImportantthingDO importantthing){
		return importantthingDao.save(importantthing);
	}
	
	@Override
	public int update(ImportantthingDO importantthing){
		return importantthingDao.update(importantthing);
	}
	
	@Override
	public int remove(String id){
		return importantthingDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return importantthingDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getUser(Map<String, Object> params) {
		return importantthingDao.getUser(params);
	}

	@Override
	public int getUserCount(Map<String, Object> params) {
		return importantthingDao.getUserCount(params);
	}

}
