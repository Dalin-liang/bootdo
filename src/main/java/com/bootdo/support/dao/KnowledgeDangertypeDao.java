package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgeDangertypeDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author oking
 * @email 1992lcg@163.com
 * @date 2019-11-04 16:46:24
 */
@Mapper
public interface KnowledgeDangertypeDao {

	KnowledgeDangertypeDO get(String id);
	
	List<KnowledgeDangertypeDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgeDangertypeDO knowledgeDangertype);
	
	int update(KnowledgeDangertypeDO knowledgeDangertype);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int changeStatus(@Param("id") String id, @Param("enabled") String enabled);
	
	List<Map<String, Object>> getDangerType();
}
