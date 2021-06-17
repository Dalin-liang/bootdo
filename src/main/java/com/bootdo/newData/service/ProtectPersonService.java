package com.bootdo.newData.service;

import com.bootdo.newData.domain.ProtectPersonDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:53:35
 */
public interface ProtectPersonService {
	
	ProtectPersonDO get(Integer id);
	
	List<ProtectPersonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProtectPersonDO protectPerson);
	
	int update(ProtectPersonDO protectPerson);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
