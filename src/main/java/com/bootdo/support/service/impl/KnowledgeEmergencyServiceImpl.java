package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.KnowledgeEmergencyDao;
import com.bootdo.support.dto.KnowledgeEmergencyDO;
import com.bootdo.support.service.KnowledgeEmergencyService;



@Service
public class KnowledgeEmergencyServiceImpl implements KnowledgeEmergencyService {
	@Autowired
	private KnowledgeEmergencyDao knowledgeEmergencyDao;
	
	@Override
	public KnowledgeEmergencyDO get(String id){
		return knowledgeEmergencyDao.get(id);
	}
	
	@Override
	public List<KnowledgeEmergencyDO> list(Map<String, Object> map){
		return knowledgeEmergencyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeEmergencyDao.count(map);
	}
	
	@Override
	public int save(KnowledgeEmergencyDO knowledgeEmergency){
		return knowledgeEmergencyDao.save(knowledgeEmergency);
	}
	
	@Override
	public int update(KnowledgeEmergencyDO knowledgeEmergency){
		return knowledgeEmergencyDao.update(knowledgeEmergency);
	}
	
	@Override
	public int remove(String id){
		return knowledgeEmergencyDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgeEmergencyDao.batchRemove(ids);
	}
	
}
