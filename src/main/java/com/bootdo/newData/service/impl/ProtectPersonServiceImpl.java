package com.bootdo.newData.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.newData.dao.ProtectPersonDao;
import com.bootdo.newData.domain.ProtectPersonDO;
import com.bootdo.newData.service.ProtectPersonService;



@Service
public class ProtectPersonServiceImpl implements ProtectPersonService {
	@Autowired
	private ProtectPersonDao protectPersonDao;
	
	@Override
	public ProtectPersonDO get(Integer id){
		return protectPersonDao.get(id);
	}
	
	@Override
	public List<ProtectPersonDO> list(Map<String, Object> map){
		return protectPersonDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return protectPersonDao.count(map);
	}
	
	@Override
	public int save(ProtectPersonDO protectPerson){
		return protectPersonDao.save(protectPerson);
	}
	
	@Override
	public int update(ProtectPersonDO protectPerson){
		return protectPersonDao.update(protectPerson);
	}
	
	@Override
	public int remove(Integer id){
		return protectPersonDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return protectPersonDao.batchRemove(ids);
	}
	
}
