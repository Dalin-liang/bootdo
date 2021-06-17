package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgeDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-13 10:21:50
 */
@Mapper
public interface KnowledgeDao {

	KnowledgeDO get(String id);
	
	List<Map<String,Object>> list(Map<String,Object> map);
	
	List<KnowledgeDO> getListByParams(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgeDO knowledge);
	
	int update(KnowledgeDO knowledge);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
