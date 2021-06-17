package com.bootdo.support.service;

import com.bootdo.support.dto.KnowledgeProtectDO;

import java.util.List;
import java.util.Map;

/**
 * 防护措施表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public interface KnowledgeProtectService {
	
	KnowledgeProtectDO get(String id);
	
	List<KnowledgeProtectDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(KnowledgeProtectDO knowledgeProtect);
	
	int update(KnowledgeProtectDO knowledgeProtect);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
