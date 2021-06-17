package com.bootdo.support.service;

import com.bootdo.common.utils.Query;
import com.bootdo.support.dto.TwoviolationsdailyDO;

import java.util.List;
import java.util.Map;

/**
 * 两违日志表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 14:13:34
 */
public interface TwoviolationsdailyService {
	
	TwoviolationsdailyDO get(String id);
	
	List<TwoviolationsdailyDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TwoviolationsdailyDO twoviolationsdaily);
	
	int update(TwoviolationsdailyDO twoviolationsdaily);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

    List<Map<String, Object>> getUser(Map<String, Object> map);

	int getUserCount(Map<String, Object> map);

	List<TwoviolationsdailyDO.TwoviolationsdailyExpDO> exportExcel(Map<String, Object> map);
}
