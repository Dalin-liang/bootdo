package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.TwoviolationsdailyFileDao;
import com.bootdo.support.dto.TwoviolationsdailyFileDO;
import com.bootdo.support.service.TwoviolationsdailyFileService;



@Service
public class TwoviolationsdailyFileServiceImpl implements TwoviolationsdailyFileService {
	@Autowired
	private TwoviolationsdailyFileDao twoviolationsdailyFileDao;
	
	@Override
	public TwoviolationsdailyFileDO get(String id){
		return twoviolationsdailyFileDao.get(id);
	}
	
	@Override
	public List<TwoviolationsdailyFileDO> list(Map<String, Object> map){
		return twoviolationsdailyFileDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return twoviolationsdailyFileDao.count(map);
	}
	
	@Override
	public int save(TwoviolationsdailyFileDO twoviolationsdailyFile){
		return twoviolationsdailyFileDao.save(twoviolationsdailyFile);
	}
	
	@Override
	public int update(TwoviolationsdailyFileDO twoviolationsdailyFile){
		return twoviolationsdailyFileDao.update(twoviolationsdailyFile);
	}
	
	@Override
	public int remove(String id){
		return twoviolationsdailyFileDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return twoviolationsdailyFileDao.batchRemove(ids);
	}

	@Override
	public List<TwoviolationsdailyFileDO> getByTwoviolationsdailyId(String twoviolationsdailyId) {
		return twoviolationsdailyFileDao.getByTwoviolationsdailyId(twoviolationsdailyId);
	}

}
