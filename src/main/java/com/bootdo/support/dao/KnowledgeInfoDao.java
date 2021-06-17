package com.bootdo.support.dao;

import com.bootdo.support.dto.KnowledgeInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 知识库名单表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
@Mapper
public interface KnowledgeInfoDao {

	KnowledgeInfoDO get(String id);
	
	List<KnowledgeInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(KnowledgeInfoDO knowledgeInfo);
	
	int update(KnowledgeInfoDO knowledgeInfo);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int changeStatus(@Param("id") String id, @Param("enabled") String enabled);
	
	List<String> getKnowledgeInfoIdsByParam(@Param("code") String code,@Param("name") String name,@Param("otherName") String otherName,@Param("dangerType") String dangerType);
}
