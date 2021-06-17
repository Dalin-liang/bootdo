package com.bootdo.support.service;

import com.bootdo.support.dto.EquipstorehouseDO;

import java.util.List;
import java.util.Map;

/**
 * 装备存储库
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-08 11:31:42
 */
public interface EquipstorehouseService {
	
	EquipstorehouseDO get(String id);
	
	List<Map<String,Object>> getAll();
	
	List<EquipstorehouseDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EquipstorehouseDO equipstorehouse);
	
	int update(EquipstorehouseDO equipstorehouse);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int logicalDelete(String id);
}
