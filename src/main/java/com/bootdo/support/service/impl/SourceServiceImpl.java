package com.bootdo.support.service.impl;

import com.bootdo.support.dao.SourceDao;
import com.bootdo.support.entity.SourceDO;
import com.bootdo.support.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class SourceServiceImpl implements SourceService {
	@Autowired
	private SourceDao sourceDao;
	
	@Override
	public SourceDO get(String id){
		return sourceDao.get(id);
	}
	
	@Override
	public List<SourceDO> list(Map<String, Object> map){
		return sourceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sourceDao.count(map);
	}
	
	@Override
	public int save(SourceDO source){
		return sourceDao.save(source);
	}
	
	@Override
	public int update(SourceDO source){
		return sourceDao.update(source);
	}
	
	@Override
	public int remove(String id){
		return sourceDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return sourceDao.batchRemove(ids);
	}

	@Override
	public List<SourceDO> getAllSource() {
		return sourceDao.getAllSource();
	}

	@Override
	public List<SourceDO> getSourceInMenu() {
		return sourceDao.getSourceInMenu();
	}
	
	@Override
	public List<SourceDO> getSourceByType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sourceDao.getSourceByType(map);
	}

	@Override
	public List<SourceDO> getSourceNotInSendConfig() {
		return sourceDao.getSourceNotInSendConfig();
	}

}
