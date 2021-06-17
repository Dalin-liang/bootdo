package com.bootdo.support.service;

import com.bootdo.support.entity.AppSignDO;

import java.util.List;
import java.util.Map;

/**
 * 签到表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-15 09:44:23
 */
public interface AppSignService {
	
	AppSignDO get(String id);
	
	List<AppSignDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AppSignDO appSign);
	
	int update(AppSignDO appSign);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
