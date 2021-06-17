package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.EquiptypeDao;
import com.bootdo.support.dto.EquiptypeDO;
import com.bootdo.support.service.EquiptypeService;



@Service
public class EquiptypeServiceImpl implements EquiptypeService {
	@Autowired
	private EquiptypeDao equiptypeDao;
	
	@Override
	public EquiptypeDO get(String id){
		return equiptypeDao.get(id);
	}
	
	@Override
	public List<EquiptypeDO> list(Map<String, Object> map){
		return equiptypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return equiptypeDao.count(map);
	}
	
	@Override
	public int save(EquiptypeDO equiptype){
		return equiptypeDao.save(equiptype);
	}
	
	@Override
	public int update(EquiptypeDO equiptype){
		return equiptypeDao.update(equiptype);
	}
	
	@Override
	public int remove(String id){
		return equiptypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return equiptypeDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getAll() {
		// TODO Auto-generated method stub
		return equiptypeDao.getAll();
	}
	
}
