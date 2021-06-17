package com.bootdo.appraise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.appraise.dao.AppraiseManageDao;
import com.bootdo.appraise.domain.AppraiseManageDO;
import com.bootdo.appraise.service.AppraiseManageService;



@Service
public class AppraiseManageServiceImpl implements AppraiseManageService {
	@Autowired
	private AppraiseManageDao appraiseManageDao;
	
	@Override
	public AppraiseManageDO get(String id){
		return appraiseManageDao.get(id);
	}
	
	@Override
	public List<Map<String,Object>> list(AppraiseManageDO appraiseManageDO){
		return appraiseManageDao.list(appraiseManageDO);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return appraiseManageDao.count(map);
	}
	
	@Override
	public int save(AppraiseManageDO appraiseManage){
		return appraiseManageDao.save(appraiseManage);
	}
	
	@Override
	public int update(AppraiseManageDO appraiseManage){
		return appraiseManageDao.update(appraiseManage);
	}
	
	@Override
	public int remove(String id){
		return appraiseManageDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return appraiseManageDao.batchRemove(ids);
	}
	
}
