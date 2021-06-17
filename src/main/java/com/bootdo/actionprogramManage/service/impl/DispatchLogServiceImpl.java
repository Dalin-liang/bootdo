package com.bootdo.actionprogramManage.service.impl;

import com.bootdo.actionprogramManage.dao.DispatchLogDao;
import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.actionprogramManage.service.DispatchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class DispatchLogServiceImpl implements DispatchLogService {
	@Autowired
	private DispatchLogDao dispatchLogDao;
	
	@Override
	public DispatchLogDO get(String id){
		return dispatchLogDao.get(id);
	}
	
	@Override
	public List<DispatchLogDO> list(Map<String, Object> map){
		return dispatchLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchLogDao.count(map);
	}
	
	@Override
	public int save(DispatchLogDO dispatchLog){
		return dispatchLogDao.save(dispatchLog);
	}
	
	@Override
	public int update(DispatchLogDO dispatchLog){
		return dispatchLogDao.update(dispatchLog);
	}
	
	@Override
	public int remove(String id){
		return dispatchLogDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchLogDao.batchRemove(ids);
	}

	@Override
	public int logicalDelete(String id) {
		return dispatchLogDao.logicalDelete(id);
	}

	@Override
	public List<Map<String, Object>> getDispatchActionprogram() {
		return dispatchLogDao.getDispatchActionprogram();
	}

	@Override
	public List<DispatchLogDO> getDispatchLogByActionprogramId(Map<String, Object> map) {
		return dispatchLogDao.getDispatchLogByActionprogramId(map);
	}

	@Override
	public List<DispatchLogDO> getDispatchLogByTimeRange(Map<String, Object> map) {
		return dispatchLogDao.getDispatchLogByTimeRange(map);
	}

}
