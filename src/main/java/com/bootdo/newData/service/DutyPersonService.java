package com.bootdo.newData.service;

import com.bootdo.newData.domain.DutyPersonDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 14:45:32
 */
public interface DutyPersonService {
	
	DutyPersonDO get(Integer id);
	
	List<DutyPersonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DutyPersonDO dutyPerson);
	
	int update(DutyPersonDO dutyPerson);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
