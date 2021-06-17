package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.KnowledgeDetailDao;
import com.bootdo.support.dto.KnowledgeDetailDO;
import com.bootdo.support.service.KnowledgeDetailService;



@Service
public class KnowledgeDetailServiceImpl implements KnowledgeDetailService {
	@Autowired
	private KnowledgeDetailDao knowledgeDetailDao;
	
	@Override
	public KnowledgeDetailDO get(String id){
		return knowledgeDetailDao.get(id);
	}
	
	@Override
	public List<KnowledgeDetailDO> list(Map<String, Object> map){
		return knowledgeDetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeDetailDao.count(map);
	}
	
	@Override
	public int save(KnowledgeDetailDO knowledgeDetail){
		return knowledgeDetailDao.save(knowledgeDetail);
	}
	
	@Override
	public int update(KnowledgeDetailDO knowledgeDetail){
		return knowledgeDetailDao.update(knowledgeDetail);
	}
	
	@Override
	public int remove(String id){
		return knowledgeDetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgeDetailDao.batchRemove(ids);
	}
	
}
