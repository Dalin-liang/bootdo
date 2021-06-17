package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchLogtypeDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 应急调度日志类别表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:16
 */
@Mapper
public interface DispatchLogtypeDao {

	DispatchLogtypeDO get(String id);
	
	List<DispatchLogtypeDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchLogtypeDO dispatchLogtype);
	
	int update(DispatchLogtypeDO dispatchLogtype);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
