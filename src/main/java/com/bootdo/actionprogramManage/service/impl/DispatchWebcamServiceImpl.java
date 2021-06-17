package com.bootdo.actionprogramManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.dao.DispatchWebcamDao;
import com.bootdo.actionprogramManage.domain.DispatchWebcamDO;
import com.bootdo.actionprogramManage.service.DispatchWebcamService;



@Service
public class DispatchWebcamServiceImpl implements DispatchWebcamService {
	@Autowired
	private DispatchWebcamDao dispatchWebcamDao;
	
	@Override
	public DispatchWebcamDO get(String id){
		return dispatchWebcamDao.get(id);
	}
	
	@Override
	public List<DispatchWebcamDO> list(Map<String, Object> map){
		return dispatchWebcamDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchWebcamDao.count(map);
	}
	
	@Override
	public int save(DispatchWebcamDO dispatchWebcam){
		return dispatchWebcamDao.save(dispatchWebcam);
	}
	
	@Override
	public int update(DispatchWebcamDO dispatchWebcam){
		return dispatchWebcamDao.update(dispatchWebcam);
	}
	
	@Override
	public int remove(String id){
		return dispatchWebcamDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchWebcamDao.batchRemove(ids);
	}
	
}
