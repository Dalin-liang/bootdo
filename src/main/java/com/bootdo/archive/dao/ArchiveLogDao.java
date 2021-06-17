package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchiveLogDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 应急调度日志归档表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchiveLogDao {

	ArchiveLogDO get(String id);
	
	List<ArchiveLogDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchiveLogDO archiveLog);
	
	int update(ArchiveLogDO archiveLog);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFromDispatch(String actionprogram_id);

}
