package com.bootdo.actionprogramManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.dao.DispatchPlanMainDao;
import com.bootdo.actionprogramManage.domain.DispatchPlanMainDO;
import com.bootdo.actionprogramManage.service.DispatchPlanMainService;



@Service
public class DispatchPlanMainServiceImpl implements DispatchPlanMainService {
	@Autowired
	private DispatchPlanMainDao dispatchPlanMainDao;
	
	@Override
	public DispatchPlanMainDO get(String id){
		return dispatchPlanMainDao.get(id);
	}
	
	@Override
	public List<DispatchPlanMainDO> list(Map<String, Object> map){
		return dispatchPlanMainDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchPlanMainDao.count(map);
	}
	
	@Override
	public int save(DispatchPlanMainDO dispatchPlanMain){
		return dispatchPlanMainDao.save(dispatchPlanMain);
	}
	
	@Override
	public int update(DispatchPlanMainDO dispatchPlanMain){
		return dispatchPlanMainDao.update(dispatchPlanMain);
	}
	
	@Override
	public int remove(String id){
		return dispatchPlanMainDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchPlanMainDao.batchRemove(ids);
	}

	@Override
	public DispatchPlanMainDO getByActionprogramId(String actionprogramId) {
		return dispatchPlanMainDao.getByActionprogramId(actionprogramId);
	}
	
}
