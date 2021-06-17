package com.bootdo.support.service;

import com.bootdo.support.dto.KnowledgeInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 知识库名单表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public interface KnowledgeInfoService {
	
	KnowledgeInfoDO get(String id);
	
	List<KnowledgeInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(KnowledgeInfoDO knowledgeInfo);
	
	int update(KnowledgeInfoDO knowledgeInfo);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int addKnowledge(String info, String detail, String danger, String emergency, String physical, String protect);

	void updateKnowledge(String info, String detail, String danger, String emergency, String physical, String protect);

	int deleteKnowledge(String id);

	int changeStatus(String id, String enabled);

	Map<String, Object> getknowledgeDetails(String id);
	
	List<Map<String, Object>> getknowledgeByParam(String code,String name ,String otherName, String dangerType);
}
