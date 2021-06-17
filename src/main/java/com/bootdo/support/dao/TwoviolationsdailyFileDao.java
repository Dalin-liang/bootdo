package com.bootdo.support.dao;

import com.bootdo.support.dto.TwoviolationsdailyFileDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 两违日志附件表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 14:13:35
 */
@Mapper
public interface TwoviolationsdailyFileDao {

	TwoviolationsdailyFileDO get(String id);
	
	List<TwoviolationsdailyFileDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TwoviolationsdailyFileDO twoviolationsdailyFile);
	
	int update(TwoviolationsdailyFileDO twoviolationsdailyFile);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

    List<TwoviolationsdailyFileDO> getByTwoviolationsdailyId(@Param("twoviolationsdailyId") String twoviolationsdailyId);
}
