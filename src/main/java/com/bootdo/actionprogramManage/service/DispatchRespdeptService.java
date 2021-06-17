package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchRespdeptDO;

import java.util.List;
import java.util.Map;

/**
 * 执行方案的预案响应部门归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
public interface DispatchRespdeptService {
	
	DispatchRespdeptDO get(String id);
	
	List<DispatchRespdeptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchRespdeptDO dispatchRespdept);
	
	int logicDelete(String id);
	
	int update(DispatchRespdeptDO dispatchRespdept);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<DispatchRespdeptDO> getByPlanMainId(String planMainId);
	
	List<Map<String, Object>> getByActionprogramId(String actionprogramId);

}
