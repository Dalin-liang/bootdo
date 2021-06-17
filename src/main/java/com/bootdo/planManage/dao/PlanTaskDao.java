package com.bootdo.planManage.dao;

import com.bootdo.planManage.domain.PlanTaskDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预案任务表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
@Mapper
public interface PlanTaskDao {

	PlanTaskDO get(String id);
	
	List<PlanTaskDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PlanTaskDO planTask);
	
	int update(PlanTaskDO planTask);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByPlanMainId(String planId);

	List<PlanTaskDO> getByPlanId(String planId);

    void deleteByNotInTaskIds(@Param("planTaskIds") String[] planTaskIds, @Param("planMainId") String planMainId);
}
