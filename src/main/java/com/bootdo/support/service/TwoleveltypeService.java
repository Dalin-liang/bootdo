package com.bootdo.support.service;

import com.bootdo.support.entity.TwoleveltypeDO;

import java.util.List;
import java.util.Map;

/**
 * 物资二级分类表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 12:00:52
 */
public interface TwoleveltypeService {
	
	TwoleveltypeDO get(String id);
	
	List<TwoleveltypeDO> getAll();
	
	List<TwoleveltypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TwoleveltypeDO twoleveltype);
	
	int update(TwoleveltypeDO twoleveltype);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
