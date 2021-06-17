package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.KnowledgeDao;
import com.bootdo.support.dto.KnowledgeDO;
import com.bootdo.support.service.KnowledgeService;



@Service
public class KnowledgeServiceImpl implements KnowledgeService {
	@Autowired
	private KnowledgeDao knowledgeDao;
	
	@Override
	public KnowledgeDO get(String id){
		return knowledgeDao.get(id);
	}
	
	@Override
	public List<Map<String,Object>> list(Map<String, Object> map){
		return knowledgeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeDao.count(map);
	}
	
	@Override
	public int save(KnowledgeDO knowledge){
		return knowledgeDao.save(knowledge);
	}
	
	@Override
	public int update(KnowledgeDO knowledge){
		return knowledgeDao.update(knowledge);
	}
	
	@Override
	public int remove(String id){
		return knowledgeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgeDao.batchRemove(ids);
	}

	@Override
	public List<KnowledgeDO> getListByParams(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return knowledgeDao.getListByParams(map);
	}
	
}
