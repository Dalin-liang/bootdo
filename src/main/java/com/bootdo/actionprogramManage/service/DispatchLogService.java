package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchLogDO;

import java.util.List;
import java.util.Map;

/**
 * 应急调度日志表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:16
 */
public interface DispatchLogService {
	
	DispatchLogDO get(String id);
	
	List<DispatchLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchLogDO dispatchLog);
	
	int update(DispatchLogDO dispatchLog);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int logicalDelete(String id);

	List<Map<String,Object>> getDispatchActionprogram();


	/**
	 * 查询条件参数 #{actionId} 执行方案id
	 * @param map
	 * @return
	 */
	List<DispatchLogDO> getDispatchLogByActionprogramId(Map<String, Object> map);


	/**
	 * 插叙参数
	 *  Date #{startTime} 开始时间
	 * 	Date #{endTime}  结束时间
	 * 	String #{actionId}	 执行方案id
	 * @param map
	 * @return
	 */
	List<DispatchLogDO> getDispatchLogByTimeRange(Map<String, Object> map);
}
