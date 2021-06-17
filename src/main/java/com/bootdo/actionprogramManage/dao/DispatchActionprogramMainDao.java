package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchActionprogramMainDO;

import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.domain.DispatchEarlywarnDO ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 应急执行方案主表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
@Mapper
public interface DispatchActionprogramMainDao {

	DispatchActionprogramMainDO get(String id);
	
	List<DispatchActionprogramMainDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchActionprogramMainDO dispatchActionprogramMain);
	
	int update(DispatchActionprogramMainDO dispatchActionprogramMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int removeByActionprogramId(String actionprogramId);
	
	int updateStatusByActionprogramId(String actionprogramId);

	List<Map<String, Object>> getEarlywarnInfo();
	
	List<Map<String, Object>> getTimeAxisData(String actionprogramId);
	
	List<Map<String, Object>> getArchivelog(String actionprogramId);

	DispatchEarlywarnDO getEarlywarnById(@Param("actionprogramId") String actionprogramId);

    List<Map<String, Object>> getAllName();

    List<Map<String, Object>> getLastNameForSms();
}
