package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.KnowledgeProtectDao;
import com.bootdo.support.dto.KnowledgeProtectDO;
import com.bootdo.support.service.KnowledgeProtectService;



@Service
public class KnowledgeProtectServiceImpl implements KnowledgeProtectService {
	@Autowired
	private KnowledgeProtectDao knowledgeProtectDao;
	
	@Override
	public KnowledgeProtectDO get(String id){
		return knowledgeProtectDao.get(id);
	}
	
	@Override
	public List<KnowledgeProtectDO> list(Map<String, Object> map){
		return knowledgeProtectDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeProtectDao.count(map);
	}
	
	@Override
	public int save(KnowledgeProtectDO knowledgeProtect){
		return knowledgeProtectDao.save(knowledgeProtect);
	}
	
	@Override
	public int update(KnowledgeProtectDO knowledgeProtect){
		return knowledgeProtectDao.update(knowledgeProtect);
	}
	
	@Override
	public int remove(String id){
		return knowledgeProtectDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgeProtectDao.batchRemove(ids);
	}
	
}
