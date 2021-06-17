package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchPlanMainDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 执行方案的预案归档主表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
@Mapper
public interface DispatchPlanMainDao {

	DispatchPlanMainDO get(String id);
	
	List<DispatchPlanMainDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchPlanMainDO dispatchPlanMain);
	
	int update(DispatchPlanMainDO dispatchPlanMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	DispatchPlanMainDO getByActionprogramId(String actionprogramId);
	
	int removeByActionprogramId(String actionprogramId);

}
