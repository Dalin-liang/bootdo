package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.KnowledgeDangerDao;
import com.bootdo.support.dto.KnowledgeDangerDO;
import com.bootdo.support.service.KnowledgeDangerService;



@Service
public class KnowledgeDangerServiceImpl implements KnowledgeDangerService {
	@Autowired
	private KnowledgeDangerDao knowledgeDangerDao;
	
	@Override
	public KnowledgeDangerDO get(String id){
		return knowledgeDangerDao.get(id);
	}
	
	@Override
	public List<KnowledgeDangerDO> list(Map<String, Object> map){
		return knowledgeDangerDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeDangerDao.count(map);
	}
	
	@Override
	public int save(KnowledgeDangerDO knowledgeDanger){
		return knowledgeDangerDao.save(knowledgeDanger);
	}
	
	@Override
	public int update(KnowledgeDangerDO knowledgeDanger){
		return knowledgeDangerDao.update(knowledgeDanger);
	}
	
	@Override
	public int remove(String id){
		return knowledgeDangerDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgeDangerDao.batchRemove(ids);
	}
	
}
