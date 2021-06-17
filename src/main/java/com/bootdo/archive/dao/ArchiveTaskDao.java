package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchiveTaskDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 应急执行方案归档任务表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchiveTaskDao {

	ArchiveTaskDO get(String id);
	
	List<ArchiveTaskDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchiveTaskDO archiveTask);
	
	int update(ArchiveTaskDO archiveTask);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFromDispatch(String actionprogram_id);

    List<Map<String, Object>> getByActionprogramIdAndPlanMainId(@Param("actionprogramId") String actionprogramId,@Param("planMainId") String planMainId);
}
