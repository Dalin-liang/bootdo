package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgeEmergencyDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 应急行动表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
@Mapper
public interface KnowledgeEmergencyDao {

	KnowledgeEmergencyDO get(String id);
	
	List<KnowledgeEmergencyDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgeEmergencyDO knowledgeEmergency);
	
	int update(KnowledgeEmergencyDO knowledgeEmergency);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByKnowledgeInfoId(String id);

	KnowledgeEmergencyDO getByKnowledgeInfoId(String id);
}
