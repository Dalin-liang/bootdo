package com.bootdo.support.dao;

import com.bootdo.support.entity.SourceDO;
import com.bootdo.support.entity.SourceMenuDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 接报来源详细类目表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:12:25
 */
@Mapper
public interface SourceMenuDao {

	SourceMenuDO get(Long id);
	
	List<SourceMenuDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SourceMenuDO sourceMenu);
	
	int update(SourceMenuDO sourceMenu);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<SourceDO> getWatchSource();

	int changeStatus(@Param("id") String id,@Param("enabled") String enabled);

    List<SourceMenuDO> getBySourceId(@Param("id") String id);

    List<SourceMenuDO> getLastBySourceIdForSms(String id);
}
