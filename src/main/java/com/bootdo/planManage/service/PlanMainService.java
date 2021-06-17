package com.bootdo.planManage.service;

import com.bootdo.common.utils.Query;
import com.bootdo.planManage.domain.PlanMainDO;

import java.util.List;
import java.util.Map;

/**
 * 预案表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public interface PlanMainService {
	
	PlanMainDO get(String id);
	
	List<PlanMainDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanMainDO planMain);
	
	int update(PlanMainDO planMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void addPlan(String planMain, String respDept, String task);

	void updatePlan(String planMain, String respDept, String task);

	int deletePlan(String id);

	void batchDeletePlan(String[] ids);

	int changeStatus(String id, String enabled);
	
	List<PlanMainDO> getPlanMainByParam(String levelId, String accidentName, String warnTypeName, String warnLevelName);

    List<PlanMainDO> actionRecordlist(Map<String, Object> map);

	int actionRecordlistCount(Map<String, Object> map);

	List<Map<String, Object>> getAllName();

    List<Map<String, Object>> getLastNameForSms();
}
