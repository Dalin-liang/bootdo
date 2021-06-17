package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgeDetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 基本属性表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
@Mapper
public interface KnowledgeDetailDao {

	KnowledgeDetailDO get(String id);
	
	List<KnowledgeDetailDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgeDetailDO knowledgeDetail);
	
	int update(KnowledgeDetailDO knowledgeDetail);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByKnowledgeInfoId(String id);

	KnowledgeDetailDO getByKnowledgeInfoId(String id);
}
