package com.bootdo.support.service;

import com.bootdo.support.dto.KnowledgeDangerDO;

import java.util.List;
import java.util.Map;

/**
 * 危险性表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:45
 */
public interface KnowledgeDangerService {
	
	KnowledgeDangerDO get(String id);
	
	List<KnowledgeDangerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(KnowledgeDangerDO knowledgeDanger);
	
	int update(KnowledgeDangerDO knowledgeDanger);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
