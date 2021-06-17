package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.dao.KnowledgeDangerDao;
import com.bootdo.support.dao.KnowledgeDetailDao;
import com.bootdo.support.dao.KnowledgeEmergencyDao;
import com.bootdo.support.dao.KnowledgeInfoDao;
import com.bootdo.support.dao.KnowledgePhysicalDao;
import com.bootdo.support.dao.KnowledgeProtectDao;
import com.bootdo.support.dto.KnowledgeDangerDO;
import com.bootdo.support.dto.KnowledgeDetailDO;
import com.bootdo.support.dto.KnowledgeEmergencyDO;
import com.bootdo.support.dto.KnowledgeInfoDO;
import com.bootdo.support.dto.KnowledgePhysicalDO;
import com.bootdo.support.dto.KnowledgeProtectDO;
import com.bootdo.support.service.KnowledgeInfoService;



@Service
@Transactional(readOnly = true)
public class KnowledgeInfoServiceImpl implements KnowledgeInfoService {
	@Autowired
	private KnowledgeInfoDao knowledgeInfoDao;
	@Autowired
	private KnowledgeDetailDao knowledgeDetailDao ;
	@Autowired
	private KnowledgeDangerDao knowledgeDangerDao ;
	@Autowired
	private KnowledgeEmergencyDao knowledgeEmergencyDao;
	@Autowired
	private KnowledgePhysicalDao knowledgePhysicalDao;
	@Autowired
	private KnowledgeProtectDao knowledgeProtectDao;
	
	@Override
	public KnowledgeInfoDO get(String id){
		return knowledgeInfoDao.get(id);
	}
	
	@Override
	public List<KnowledgeInfoDO> list(Map<String, Object> map){
		return knowledgeInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return knowledgeInfoDao.count(map);
	}
	
	@Transactional(readOnly = false)
	public int save(KnowledgeInfoDO knowledgeInfo){
		return knowledgeInfoDao.save(knowledgeInfo);
	}
	
	@Transactional(readOnly = false)
	public int update(KnowledgeInfoDO knowledgeInfo){
		return knowledgeInfoDao.update(knowledgeInfo);
	}
	
	@Transactional(readOnly = false)
	public int remove(String id){
		return knowledgeInfoDao.remove(id);
	}
	
	@Transactional(readOnly = false)
	public int batchRemove(String[] ids){
		return knowledgeInfoDao.batchRemove(ids);
	}

	@Transactional(readOnly = false)
	public int addKnowledge(String info, String detail, String danger, String emergency, String physical,String protect) {
		String userId = String.valueOf(ShiroUtils.getUser().getUserId());
		Date dateTime = new Date() ;
		String infoId = null;
		
		KnowledgeInfoDO knowledgeInfoDO = JSON.parseObject(info, KnowledgeInfoDO.class);
		knowledgeInfoDO.setId(UUID.randomUUID().toString().replace("-",""));
		knowledgeInfoDO.setCreateBy(userId);
		knowledgeInfoDO.setCreateDate(dateTime);
		if(knowledgeInfoDao.save(knowledgeInfoDO)>0){
			infoId = knowledgeInfoDO.getId();
			if(detail!=null &&!"".equals(detail)) {
				KnowledgeDetailDO knowledgeDetailDO = JSON.parseObject(detail, KnowledgeDetailDO.class);
				knowledgeDetailDO.setId(UUID.randomUUID().toString().replace("-",""));
				knowledgeDetailDO.setKnowledgeInfoId(infoId);
				knowledgeDetailDO.setCreateDate(dateTime);
				knowledgeDetailDao.save(knowledgeDetailDO);
			}
			if(danger!=null &&!"".equals(danger)) {
				KnowledgeDangerDO knowledgeDangerDO = JSON.parseObject(danger, KnowledgeDangerDO.class);
				knowledgeDangerDO.setId(UUID.randomUUID().toString().replace("-",""));
				knowledgeDangerDO.setKnowledgeInfoId(infoId);
				knowledgeDangerDO.setCreateDate(dateTime);
				knowledgeDangerDao.save(knowledgeDangerDO);
			}
			if(emergency!=null &&!"".equals(emergency)) {
				KnowledgeEmergencyDO knowledgeEmergencyDO = JSON.parseObject(emergency, KnowledgeEmergencyDO.class);
				knowledgeEmergencyDO.setId(UUID.randomUUID().toString().replace("-",""));
				knowledgeEmergencyDO.setKnowledgeInfoId(infoId);
				knowledgeEmergencyDO.setCreateDate(dateTime);
				knowledgeEmergencyDao.save(knowledgeEmergencyDO);
			}
			if(physical!=null &&!"".equals(physical)) {
				KnowledgePhysicalDO knowledgePhysicalDO = JSON.parseObject(physical, KnowledgePhysicalDO.class);
				knowledgePhysicalDO.setId(UUID.randomUUID().toString().replace("-",""));
				knowledgePhysicalDO.setKnowledgeInfoId(infoId);
				knowledgePhysicalDO.setCreateDate(dateTime);
				knowledgePhysicalDao.save(knowledgePhysicalDO);
			}
			if(protect!=null &&!"".equals(protect)) {
				KnowledgeProtectDO knowledgeProtectDO = JSON.parseObject(protect, KnowledgeProtectDO.class);
				knowledgeProtectDO.setId(UUID.randomUUID().toString().replace("-",""));
				knowledgeProtectDO.setKnowledgeInfoId(infoId);
				knowledgeProtectDO.setCreateDate(dateTime);
				knowledgeProtectDao.save(knowledgeProtectDO);
			}
			return 1;
		}
		return 0;
	}

	@Transactional(readOnly = false)
	public void updateKnowledge(String info, String detail, String danger, String emergency, String physical,String protect) {
		
		if(info!=null &&!"".equals(info)) {
			KnowledgeInfoDO knowledgeInfoDO = JSON.parseObject(info, KnowledgeInfoDO.class);
			knowledgeInfoDao.update(knowledgeInfoDO);
		}
		
		if(detail!=null &&!"".equals(detail)) {
			KnowledgeDetailDO knowledgeDetailDO = JSON.parseObject(detail, KnowledgeDetailDO.class);
			knowledgeDetailDao.update(knowledgeDetailDO);
		}
		if(danger!=null &&!"".equals(danger)) {
			KnowledgeDangerDO knowledgeDangerDO = JSON.parseObject(danger, KnowledgeDangerDO.class);
			knowledgeDangerDao.update(knowledgeDangerDO);
		}
		if(emergency!=null &&!"".equals(emergency)) {
			KnowledgeEmergencyDO knowledgeEmergencyDO = JSON.parseObject(emergency, KnowledgeEmergencyDO.class);
			knowledgeEmergencyDao.update(knowledgeEmergencyDO);
		}
		if(physical!=null &&!"".equals(physical)) {
			KnowledgePhysicalDO knowledgePhysicalDO = JSON.parseObject(physical, KnowledgePhysicalDO.class);
			knowledgePhysicalDao.update(knowledgePhysicalDO);
		}
		if(protect!=null &&!"".equals(protect)) {
			KnowledgeProtectDO knowledgeProtectDO = JSON.parseObject(protect, KnowledgeProtectDO.class);
			knowledgeProtectDao.update(knowledgeProtectDO);
		}
		
	}

	@Transactional(readOnly = false)
	public int deleteKnowledge(String id) {
		knowledgeDetailDao.deleteByKnowledgeInfoId(id);
		knowledgeDangerDao.deleteByKnowledgeInfoId(id);
		knowledgeEmergencyDao.deleteByKnowledgeInfoId(id);
		knowledgePhysicalDao.deleteByKnowledgeInfoId(id);
		knowledgeProtectDao.deleteByKnowledgeInfoId(id);
		return knowledgeInfoDao.remove(id);
	}

	@Transactional(readOnly = false)
	public int changeStatus(String id, String enabled) {
		return knowledgeInfoDao.changeStatus(id, enabled);
	}

	@Override
	public Map<String, Object> getknowledgeDetails(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", knowledgeInfoDao.get(id));
		map.put("detail", knowledgeDetailDao.getByKnowledgeInfoId(id));
		map.put("danger", knowledgeDangerDao.getByKnowledgeInfoId(id));
		map.put("emergency", knowledgeEmergencyDao.getByKnowledgeInfoId(id));
		map.put("physical", knowledgePhysicalDao.getByKnowledgeInfoId(id));
		map.put("protect", knowledgeProtectDao.getByKnowledgeInfoId(id));
		return map;
	}

	@Override
	public List<Map<String, Object>> getknowledgeByParam(String code, String name, String otherName,String dangerType) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<String> InfoIds = knowledgeInfoDao.getKnowledgeInfoIdsByParam(code, name, otherName, dangerType);
		if(InfoIds.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			for (String InfoId : InfoIds) {
				map = getknowledgeDetails(InfoId);
				if(map !=null){
					list.add(map);
				}
	        }
		}
		return list;
	}
	
}
