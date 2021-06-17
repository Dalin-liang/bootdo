package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchEarlywarnDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 执行方案的预警信息表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-22 19:36:57
 */
@Mapper
public interface DispatchEarlywarnDao {

	DispatchEarlywarnDO get(String id);
	
	List<DispatchEarlywarnDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchEarlywarnDO dispatchEarlywarn);
	
	int update(DispatchEarlywarnDO dispatchEarlywarn);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int removeByActionprogramId(String actionprogramId);

}
