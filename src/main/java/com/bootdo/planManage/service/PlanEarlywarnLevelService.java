package com.bootdo.planManage.service;

import com.bootdo.planManage.domain.PlanAccidentTypeDO;
import com.bootdo.planManage.domain.PlanEarlywarnLevelDO;

import java.util.List;
import java.util.Map;

/**
 * 预警级别
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:49
 */
public interface PlanEarlywarnLevelService {
	
	PlanEarlywarnLevelDO get(String id);
	
	List<PlanEarlywarnLevelDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanEarlywarnLevelDO planEarlywarnLevel);
	
	int update(PlanEarlywarnLevelDO planEarlywarnLevel);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<PlanEarlywarnLevelDO> getEarlywarnLevel();

	int changeStatus(String id, String status);

	String getIdByName(String name);

	String getIdByNames(String accidentTypeName, String earlywarnTypeName, String earlywarnLevelName);
}
