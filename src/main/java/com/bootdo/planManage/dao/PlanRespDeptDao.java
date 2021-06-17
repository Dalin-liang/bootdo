package com.bootdo.planManage.dao;

import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.domain.PlanTaskDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预案响应部门
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
@Mapper
public interface PlanRespDeptDao {

	PlanRespDeptDO get(String id);
	
	List<PlanRespDeptDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PlanRespDeptDO planRespDept);
	
	int update(PlanRespDeptDO planRespDept);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByPlanMainId(String planId);

	List<PlanRespDeptDO> getByPlanId(String planId);

    void deleteByNotInRespDeptIds(@Param("planRespDeptIds") String[] planRespDeptIds, @Param("planMainId") String planMainId);
}
