package com.bootdo.actionprogramManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.dao.DispatchEarlywarnDao;
import com.bootdo.actionprogramManage.domain.DispatchEarlywarnDO;
import com.bootdo.actionprogramManage.service.DispatchEarlywarnService;



@Service
public class DispatchEarlywarnServiceImpl implements DispatchEarlywarnService {
	@Autowired
	private DispatchEarlywarnDao dispatchEarlywarnDao;
	
	@Override
	public DispatchEarlywarnDO get(String id){
		return dispatchEarlywarnDao.get(id);
	}
	
	@Override
	public List<DispatchEarlywarnDO> list(Map<String, Object> map){
		return dispatchEarlywarnDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchEarlywarnDao.count(map);
	}
	
	@Override
	public int save(DispatchEarlywarnDO dispatchEarlywarn){
		return dispatchEarlywarnDao.save(dispatchEarlywarn);
	}
	
	@Override
	public int update(DispatchEarlywarnDO dispatchEarlywarn){
		return dispatchEarlywarnDao.update(dispatchEarlywarn);
	}
	
	@Override
	public int remove(String id){
		return dispatchEarlywarnDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchEarlywarnDao.batchRemove(ids);
	}
	
}
