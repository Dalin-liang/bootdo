package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgeProtectDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 防护措施表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
@Mapper
public interface KnowledgeProtectDao {

	KnowledgeProtectDO get(String id);
	
	List<KnowledgeProtectDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgeProtectDO knowledgeProtect);
	
	int update(KnowledgeProtectDO knowledgeProtect);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByKnowledgeInfoId(String id);

	KnowledgeProtectDO getByKnowledgeInfoId(String id);
}
