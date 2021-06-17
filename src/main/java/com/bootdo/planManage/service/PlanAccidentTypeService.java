package com.bootdo.planManage.service;

import com.bootdo.planManage.domain.PlanAccidentTypeDO;

import java.util.List;
import java.util.Map;

/**
 * 事故类型管理
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:49
 */
public interface PlanAccidentTypeService {
	
	PlanAccidentTypeDO get(String id);
	
	List<PlanAccidentTypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PlanAccidentTypeDO planAccidentType);
	
	int update(PlanAccidentTypeDO planAccidentType);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<PlanAccidentTypeDO> getAccidentType();

	int changeStatus(String id, String status);

	List<Map<String, Object>> getDpetList();


	String getIdByName(String name);


}
