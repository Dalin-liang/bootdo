package com.bootdo.support.service;

import com.bootdo.common.utils.Query;
import com.bootdo.support.dto.DailyDO;

import java.util.List;
import java.util.Map;

/**
 * 值班日报/要情编辑
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-09 17:24:25
 */
public interface DailyService {
	
	DailyDO get(String id);
	
	List<Map<String,Object>> getUser(Map<String,Object> params);

	List<DailyDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DailyDO daily);
	
	int update(DailyDO daily);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

    int getUserCount(Map<String,Object> params);
}
