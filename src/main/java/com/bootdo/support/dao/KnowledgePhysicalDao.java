package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgePhysicalDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 理化性质表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
@Mapper
public interface KnowledgePhysicalDao {

	KnowledgePhysicalDO get(String id);
	
	List<KnowledgePhysicalDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgePhysicalDO knowledgePhysical);
	
	int update(KnowledgePhysicalDO knowledgePhysical);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByKnowledgeInfoId(String id);

	KnowledgePhysicalDO getByKnowledgeInfoId(String id);
}
