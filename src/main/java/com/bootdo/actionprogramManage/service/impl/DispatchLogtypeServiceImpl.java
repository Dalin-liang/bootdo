package com.bootdo.actionprogramManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.dao.DispatchLogtypeDao;
import com.bootdo.actionprogramManage.domain.DispatchLogtypeDO;
import com.bootdo.actionprogramManage.service.DispatchLogtypeService;



@Service
public class DispatchLogtypeServiceImpl implements DispatchLogtypeService {
	@Autowired
	private DispatchLogtypeDao dispatchLogtypeDao;
	
	@Override
	public DispatchLogtypeDO get(String id){
		return dispatchLogtypeDao.get(id);
	}
	
	@Override
	public List<DispatchLogtypeDO> list(Map<String, Object> map){
		return dispatchLogtypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchLogtypeDao.count(map);
	}
	
	@Override
	public int save(DispatchLogtypeDO dispatchLogtype){
		return dispatchLogtypeDao.save(dispatchLogtype);
	}
	
	@Override
	public int update(DispatchLogtypeDO dispatchLogtype){
		return dispatchLogtypeDao.update(dispatchLogtype);
	}
	
	@Override
	public int remove(String id){
		return dispatchLogtypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchLogtypeDao.batchRemove(ids);
	}
	
}
