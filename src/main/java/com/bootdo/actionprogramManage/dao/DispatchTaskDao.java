package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchTaskDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 应急执行方案任务表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
@Mapper
public interface DispatchTaskDao {

	DispatchTaskDO get(String id);
	
	List<DispatchTaskDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchTaskDO dispatchTask);
	
	int update(DispatchTaskDO dispatchTask);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	List<Map<String, Object>> getByActionprogramIdAndPlanMainId(@Param("actionprogramId") String actionprogramId, @Param("planMainId") String planMainId);

	List<Map<String, Object>> getDpetList();

	List<Map<String, Object>> getPersonList();

	List<Map<String, Object>> getExpertList();

	List<Map<String, Object>> getTeamList();

	int logicDelete(@Param("id") String id);

	List<Map<String, Object>> getByActionprogramId(@Param("actionprogramId") String actionProgramId);
	
	int removeByActionprogramId(String actionprogramId);

	List<Map<String, Object>> getTaskByParams(@Param("actionprogramId") String actionProgramId, @Param("name") String name,@Param("content") String content);

}
