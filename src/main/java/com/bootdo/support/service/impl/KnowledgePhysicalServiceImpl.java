package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.KnowledgePhysicalDao;
import com.bootdo.support.dto.KnowledgePhysicalDO;
import com.bootdo.support.service.KnowledgePhysicalService;



@Service
public class KnowledgePhysicalServiceImpl implements KnowledgePhysicalService {
	@Autowired
	private KnowledgePhysicalDao knowledgePhysicalDao;
	
	@Override
	public KnowledgePhysicalDO get(String id){
		return knowledgePhysicalDao.get(id);
	}
	
	@Override
	public List<KnowledgePhysicalDO> list(Map<String, Object> map){
		return knowledgePhysicalDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgePhysicalDao.count(map);
	}
	
	@Override
	public int save(KnowledgePhysicalDO knowledgePhysical){
		return knowledgePhysicalDao.save(knowledgePhysical);
	}
	
	@Override
	public int update(KnowledgePhysicalDO knowledgePhysical){
		return knowledgePhysicalDao.update(knowledgePhysical);
	}
	
	@Override
	public int remove(String id){
		return knowledgePhysicalDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgePhysicalDao.batchRemove(ids);
	}
	
}
