package com.bootdo.support.service;

import com.bootdo.support.entity.OneleveltypeDO;

import java.util.List;
import java.util.Map;

/**
 * 物资一级分类表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-30 10:01:07
 */
public interface OneleveltypeService {
	
	OneleveltypeDO get(String id);
	
	List<OneleveltypeDO> getAll();
	
	List<OneleveltypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OneleveltypeDO oneleveltype);
	
	int update(OneleveltypeDO oneleveltype);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
