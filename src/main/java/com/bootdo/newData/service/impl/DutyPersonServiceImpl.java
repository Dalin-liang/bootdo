package com.bootdo.newData.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.newData.dao.DutyPersonDao;
import com.bootdo.newData.domain.DutyPersonDO;
import com.bootdo.newData.service.DutyPersonService;



@Service
public class DutyPersonServiceImpl implements DutyPersonService {
	@Autowired
	private DutyPersonDao dutyPersonDao;
	
	@Override
	public DutyPersonDO get(Integer id){
		return dutyPersonDao.get(id);
	}
	
	@Override
	public List<DutyPersonDO> list(Map<String, Object> map){
		return dutyPersonDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dutyPersonDao.count(map);
	}
	
	@Override
	public int save(DutyPersonDO dutyPerson){
		return dutyPersonDao.save(dutyPerson);
	}
	
	@Override
	public int update(DutyPersonDO dutyPerson){
		return dutyPersonDao.update(dutyPerson);
	}
	
	@Override
	public int remove(Integer id){
		return dutyPersonDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return dutyPersonDao.batchRemove(ids);
	}
	
}
