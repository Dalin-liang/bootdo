package com.bootdo.support.service;

import com.bootdo.support.dto.KnowledgePhysicalDO;

import java.util.List;
import java.util.Map;

/**
 * 理化性质表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public interface KnowledgePhysicalService {
	
	KnowledgePhysicalDO get(String id);
	
	List<KnowledgePhysicalDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(KnowledgePhysicalDO knowledgePhysical);
	
	int update(KnowledgePhysicalDO knowledgePhysical);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
