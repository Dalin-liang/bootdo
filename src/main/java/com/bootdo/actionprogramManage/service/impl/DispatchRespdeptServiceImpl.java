package com.bootdo.actionprogramManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.dao.DispatchRespdeptDao;
import com.bootdo.actionprogramManage.domain.DispatchRespdeptDO;
import com.bootdo.actionprogramManage.service.DispatchRespdeptService;



@Service
public class DispatchRespdeptServiceImpl implements DispatchRespdeptService {
	@Autowired
	private DispatchRespdeptDao dispatchRespdeptDao;
	
	@Override
	public DispatchRespdeptDO get(String id){
		return dispatchRespdeptDao.get(id);
	}
	
	@Override
	public List<DispatchRespdeptDO> list(Map<String, Object> map){
		return dispatchRespdeptDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchRespdeptDao.count(map);
	}
	
	@Override
	public int save(DispatchRespdeptDO dispatchRespdept){
		return dispatchRespdeptDao.save(dispatchRespdept);
	}
	
	@Override
	public int update(DispatchRespdeptDO dispatchRespdept){
		return dispatchRespdeptDao.update(dispatchRespdept);
	}
	
	@Override
	public int remove(String id){
		return dispatchRespdeptDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchRespdeptDao.batchRemove(ids);
	}

	@Override
	public List<DispatchRespdeptDO> getByPlanMainId(String planMainId) {
		return dispatchRespdeptDao.getByPlanMainId(planMainId);
	}
	
	@Override
	public List<Map<String, Object>> getByActionprogramId(String actionprogramId) {
		return dispatchRespdeptDao.getByActionprogramId(actionprogramId);
	}

	@Override
	public int logicDelete(String id) {
		return dispatchRespdeptDao.logicDelete(id);
	}

}
