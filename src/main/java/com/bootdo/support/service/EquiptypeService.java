package com.bootdo.support.service;



import java.util.List;
import java.util.Map;

import com.bootdo.support.dto.EquiptypeDO;

/**
 * 装备类型表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-08 09:56:36
 */
public interface EquiptypeService {
	
	EquiptypeDO get(String id);
	
	List<Map<String,Object>> getAll();
	
	List<EquiptypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EquiptypeDO equiptype);
	
	int update(EquiptypeDO equiptype);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
