package com.bootdo.support.service;

import com.bootdo.common.utils.Query;
import com.bootdo.support.dto.ImportantthingDO;

import java.util.List;
import java.util.Map;

/**
 * 要情表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-24 09:27:55
 */
public interface ImportantthingService {
	
	ImportantthingDO get(String id);
	
	List<ImportantthingDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ImportantthingDO importantthing);
	
	int update(ImportantthingDO importantthing);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<Map<String, Object>> getUser(Map<String, Object> params);

	int getUserCount(Map<String, Object> params);
}
