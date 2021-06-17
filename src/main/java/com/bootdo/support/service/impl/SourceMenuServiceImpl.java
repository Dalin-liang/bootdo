package com.bootdo.support.service.impl;

import com.bootdo.support.entity.SourceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.SourceMenuDao;
import com.bootdo.support.entity.SourceMenuDO;
import com.bootdo.support.service.SourceMenuService;



@Service
public class SourceMenuServiceImpl implements SourceMenuService {
	@Autowired
	private SourceMenuDao sourceMenuDao;
	
	@Override
	public SourceMenuDO get(Long id){
		return sourceMenuDao.get(id);
	}
	
	@Override
	public List<SourceMenuDO> list(Map<String, Object> map){
		return sourceMenuDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sourceMenuDao.count(map);
	}
	
	@Override
	public int save(SourceMenuDO sourceMenu){
		return sourceMenuDao.save(sourceMenu);
	}
	
	@Override
	public int update(SourceMenuDO sourceMenu){
		return sourceMenuDao.update(sourceMenu);
	}
	
	@Override
	public int remove(Long id){
		return sourceMenuDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sourceMenuDao.batchRemove(ids);
	}

	@Override
	public List<SourceDO> getWatchSource() {
		return sourceMenuDao.getWatchSource();
	}

	@Override
	public int changeStatus(String id, String enabled) {
		return sourceMenuDao.changeStatus(id,enabled);
	}

	@Override
	public List<SourceMenuDO> getBySourceId(String id) {
		return sourceMenuDao.getBySourceId(id);
	}

	@Override
	public List<SourceMenuDO> getLastBySourceIdForSms(String id) {
		return sourceMenuDao.getLastBySourceIdForSms(id);
	}

}
