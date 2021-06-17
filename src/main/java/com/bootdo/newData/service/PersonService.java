package com.bootdo.newData.service;

import com.bootdo.newData.domain.PersonDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:11:07
 */
public interface PersonService {
	
	PersonDO get(Integer id);
	
	List<PersonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PersonDO person);
	
	int update(PersonDO person);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
