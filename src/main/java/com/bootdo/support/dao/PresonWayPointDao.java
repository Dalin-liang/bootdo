package com.bootdo.support.dao;

import com.bootdo.support.entity.PresonWayPointDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 执法人轨迹表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-15 09:44:23
 */
@Mapper
public interface PresonWayPointDao {

	PresonWayPointDO get(String id);
	
	List<PresonWayPointDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PresonWayPointDO presonWayPoint);
	
	int update(PresonWayPointDO presonWayPoint);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
