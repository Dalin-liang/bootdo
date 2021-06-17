package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.OneleveltypeDao;
import com.bootdo.support.entity.OneleveltypeDO;
import com.bootdo.support.service.OneleveltypeService;



@Service
public class OneleveltypeServiceImpl implements OneleveltypeService {
	@Autowired
	private OneleveltypeDao oneleveltypeDao;
	
	@Override
	public OneleveltypeDO get(String id){
		return oneleveltypeDao.get(id);
	}
	
	@Override
	public List<OneleveltypeDO> list(Map<String, Object> map){
		return oneleveltypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return oneleveltypeDao.count(map);
	}
	
	@Override
	public int save(OneleveltypeDO oneleveltype){
		return oneleveltypeDao.save(oneleveltype);
	}
	
	@Override
	public int update(OneleveltypeDO oneleveltype){
		return oneleveltypeDao.update(oneleveltype);
	}
	
	@Override
	public int remove(String id){
		return oneleveltypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return oneleveltypeDao.batchRemove(ids);
	}

	@Override
	public List<OneleveltypeDO> getAll() {
		return oneleveltypeDao.getAll();
	}
	
}
