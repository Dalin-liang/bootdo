package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchTaskDO;

import io.lettuce.core.output.StreamingChannel;

import java.util.List;
import java.util.Map;

/**
 * 应急执行方案任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
public interface DispatchTaskService {
	
	DispatchTaskDO get(String id);
	
	List<DispatchTaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchTaskDO dispatchTask);
	
	int logicDelete(String id);
	
	int update(DispatchTaskDO dispatchTask);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	List<Map<String, Object>> getByActionprogramId(String actionProgramId);

	List<Map<String, Object>> getByActionprogramIdAndPlanMainId(String actionprogramId, String planMainId);

	List<Map<String, Object>> getDpetList();

	List<Map<String, Object>> getPersonList();

	List<Map<String, Object>> getExpertList();

	List<Map<String, Object>> getTeamList();
	/**
	 * 根据参数查询负责人联系方式
	 * @param map
	 * @param flag 任务或响应部门
	 * @param liabilityId 负责人Id （flag为task时必传）
	 * @param type   接收任务的对象类型(个人、应急队伍、部门、专家)(flag为task时必传)
	 * @param respdeptId 响应部门Id
	 * @return Map
	 */
	Map<String,Object>getPersonContact(Map<String,Object>params);

	/**
	 * 
	 * @param actionProgramId 方案id
	 * @param name 任务名称
	 * @param content 任务内容
	 * @param liabilityMan 任务负责人
	 * @return
	 */
	List<Map<String, Object>> getTaskByParams(String actionProgramId, String name, String content,String liabilityMan);
}
