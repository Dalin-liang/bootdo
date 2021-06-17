package com.bootdo.support.dao;

import com.bootdo.dispatch.center.res.WatchmanRes;
import com.bootdo.support.dto.DailyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 值班日报/要情编辑
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-09 17:24:25
 */
@Mapper
public interface DailyDao {

	DailyDO get(String id);
	
	List<DailyDO> list(Map<String,Object> map);
	
	List<Map<String,Object>> getUser(Map<String,Object> params);
	
	int count(Map<String,Object> map);
	
	int save(DailyDO daily);
	
	int update(DailyDO daily);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<WatchmanRes> getWatchman(@Param("start") Date start,@Param("end") Date end);

    int getUserCount(Map<String, Object> params);
}
