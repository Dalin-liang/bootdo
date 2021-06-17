package com.bootdo.planManage.service;

import com.bootdo.planManage.domain.PlanTaskDO;

import java.util.List;
import java.util.Map;

/**
 * 预案任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public interface PlanTaskService {
	
	PlanTaskDO get(String id);
	
	List<PlanTaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanTaskDO planTask);
	
	int update(PlanTaskDO planTask);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByPlanMainId(String PlanId);

	List<PlanTaskDO> getByPlanId(String id);

	void deleteByNotInTaskIds(String[] planTaskIds, String planMainId);
}
