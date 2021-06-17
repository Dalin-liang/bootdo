package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.EquipstorehouseDao;
import com.bootdo.support.dto.EquipstorehouseDO;
import com.bootdo.support.service.EquipstorehouseService;



@Service
public class EquipstorehouseServiceImpl implements EquipstorehouseService {
	@Autowired
	private EquipstorehouseDao equipstorehouseDao;
	
	@Override
	public EquipstorehouseDO get(String id){
		return equipstorehouseDao.get(id);
	}
	
	@Override
	public List<EquipstorehouseDO> list(Map<String, Object> map){
		return equipstorehouseDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return equipstorehouseDao.count(map);
	}
	
	@Override
	public int save(EquipstorehouseDO equipstorehouse){
		return equipstorehouseDao.save(equipstorehouse);
	}
	
	@Override
	public int update(EquipstorehouseDO equipstorehouse){
		return equipstorehouseDao.update(equipstorehouse);
	}
	
	@Override
	public int remove(String id){
		return equipstorehouseDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return equipstorehouseDao.batchRemove(ids);
	}

	@Override
	public int logicalDelete(String id) {
		return equipstorehouseDao.logicalDelete(id);
	}

	@Override
	public List<Map<String, Object>> getAll() {
		// TODO Auto-generated method stub
		return equipstorehouseDao.getAll();
	}

	
}
