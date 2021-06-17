package com.bootdo.common.dao;

import com.bootdo.common.domain.CommonFileDO;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-14 14:32:54
 */
@Mapper
public interface CommonFileDao {

	CommonFileDO get(String id);
	
	List<CommonFileDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CommonFileDO commonFile);
	
	int update(CommonFileDO commonFile);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	List<Map<String,Object>> getMappingFile(String relationId);
}
