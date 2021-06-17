package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.TwoleveltypeDao;
import com.bootdo.support.entity.TwoleveltypeDO;
import com.bootdo.support.service.TwoleveltypeService;



@Service
public class TwoleveltypeServiceImpl implements TwoleveltypeService {
	@Autowired
	private TwoleveltypeDao twoleveltypeDao;
	
	@Override
	public TwoleveltypeDO get(String id){
		return twoleveltypeDao.get(id);
	}
	
	@Override
	public List<TwoleveltypeDO> list(Map<String, Object> map){
		return twoleveltypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return twoleveltypeDao.count(map);
	}
	
	@Override
	public int save(TwoleveltypeDO twoleveltype){
		return twoleveltypeDao.save(twoleveltype);
	}
	
	@Override
	public int update(TwoleveltypeDO twoleveltype){
		return twoleveltypeDao.update(twoleveltype);
	}
	
	@Override
	public int remove(String id){
		return twoleveltypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return twoleveltypeDao.batchRemove(ids);
	}

	@Override
	public List<TwoleveltypeDO> getAll() {
		return twoleveltypeDao.getAll();
	}
	
}
