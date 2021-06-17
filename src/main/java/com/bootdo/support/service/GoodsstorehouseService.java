package com.bootdo.support.service;


import com.bootdo.support.dto.GoodsstorehouseDO;

import java.util.List;
import java.util.Map;

/**
 * 保障库(储备库)
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-15 15:08:27
 */
public interface GoodsstorehouseService {
	
	GoodsstorehouseDO get(String id);
	
	List<GoodsstorehouseDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GoodsstorehouseDO goodsstorehouse);
	
	int update(GoodsstorehouseDO goodsstorehouse);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int logicalDelete(String id);
}
