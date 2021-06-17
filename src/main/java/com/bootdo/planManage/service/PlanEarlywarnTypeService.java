package com.bootdo.planManage.service;

import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;

import java.util.List;
import java.util.Map;

/**
 * 预警类别
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
public interface PlanEarlywarnTypeService {
	
	PlanEarlywarnTypeDO get(String id);
	
	List<PlanEarlywarnTypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanEarlywarnTypeDO planEarlywarnType);
	
	int update(PlanEarlywarnTypeDO planEarlywarnType);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<PlanEarlywarnTypeDO> getEarlywarnType();

	int changeStatus(String id, String status);

	List<PlanEarlywarnTypeDO> getEarlyWarnTypeAndExpertInfo();

	String getIdByName(String name);
	String getIdByNames(String accidentTypeName,String earlywarnTypeName);
}
