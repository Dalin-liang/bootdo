package com.bootdo.support.service.impl;

import com.bootdo.support.dao.PresonWayPointDao;
import com.bootdo.support.entity.PresonWayPointDO;
import com.bootdo.support.service.PresonWayPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class PresonWayPointServiceImpl implements PresonWayPointService {
	@Autowired
	private PresonWayPointDao presonWayPointDao;
	
	@Override
	public PresonWayPointDO get(String id){
		return presonWayPointDao.get(id);
	}
	
	@Override
	public List<PresonWayPointDO> list(Map<String, Object> map){
		return presonWayPointDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return presonWayPointDao.count(map);
	}
	
	@Override
	public int save(PresonWayPointDO presonWayPoint){
		return presonWayPointDao.save(presonWayPoint);
	}
	
	@Override
	public int update(PresonWayPointDO presonWayPoint){
		return presonWayPointDao.update(presonWayPoint);
	}
	
	@Override
	public int remove(String id){
		return presonWayPointDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return presonWayPointDao.batchRemove(ids);
	}
	
}
