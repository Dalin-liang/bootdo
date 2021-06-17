package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 应急调度日志表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:16
 */
@Mapper
public interface DispatchLogDao {

	DispatchLogDO get(String id);
	
	List<DispatchLogDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchLogDO dispatchLog);
	
	int update(DispatchLogDO dispatchLog);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int logicalDelete(String id);

	List<Map<String,Object>> getDispatchActionprogram();

	List<DispatchLogDO> getDispatchLogByActionprogramId(Map<String, Object> map);

	List<DispatchLogDO> getDispatchLogByTimeRange(Map<String, Object> map);
	
	int removeByActionprogramId(String actionprogramId);

 }
