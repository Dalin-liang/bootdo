package com.bootdo.support.service.impl;

import com.bootdo.support.dao.AppSignDao;
import com.bootdo.support.entity.AppSignDO;
import com.bootdo.support.service.AppSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class AppSignServiceImpl implements AppSignService {
	@Autowired
	private AppSignDao appSignDao;
	
	@Override
	public AppSignDO get(String id){
		return appSignDao.get(id);
	}
	
	@Override
	public List<AppSignDO> list(Map<String, Object> map){
		return appSignDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return appSignDao.count(map);
	}
	
	@Override
	public int save(AppSignDO appSign){
		return appSignDao.save(appSign);
	}
	
	@Override
	public int update(AppSignDO appSign){
		return appSignDao.update(appSign);
	}
	
	@Override
	public int remove(String id){
		return appSignDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return appSignDao.batchRemove(ids);
	}
	
}
