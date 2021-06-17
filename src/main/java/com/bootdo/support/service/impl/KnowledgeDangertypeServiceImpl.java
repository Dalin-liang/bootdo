package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dao.KnowledgeDangertypeDao;
import com.bootdo.support.dto.KnowledgeDangertypeDO;
import com.bootdo.support.service.KnowledgeDangertypeService;
import com.bootdo.system.domain.UserDO;



@Service
public class KnowledgeDangertypeServiceImpl implements KnowledgeDangertypeService {
	@Autowired
	private KnowledgeDangertypeDao knowledgeDangertypeDao;
	
	@Override
	public KnowledgeDangertypeDO get(String id){
		return knowledgeDangertypeDao.get(id);
	}
	
	@Override
	public List<KnowledgeDangertypeDO> list(Map<String, Object> map){
		return knowledgeDangertypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeDangertypeDao.count(map);
	}
	
	@Override
	public int save(KnowledgeDangertypeDO knowledgeDangertype){
		knowledgeDangertype.setId(UUID.randomUUID().toString().replace("-",""));
		knowledgeDangertype.setCreateDate(new Date());
		return knowledgeDangertypeDao.save(knowledgeDangertype);
	}
	
	@Override
	public int update(KnowledgeDangertypeDO knowledgeDangertype){
		return knowledgeDangertypeDao.update(knowledgeDangertype);
	}
	
	@Override
	public int remove(String id){
		return knowledgeDangertypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return knowledgeDangertypeDao.batchRemove(ids);
	}

	@Override
	public int changeStatus(String id, String enabled) {
		return knowledgeDangertypeDao.changeStatus(id, enabled);
	}

	@Override
	public List<Map<String, Object>> getDangerType() {
		return knowledgeDangertypeDao.getDangerType();
	}
	
}
