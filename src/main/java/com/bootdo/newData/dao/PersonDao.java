package com.bootdo.newData.dao;

import com.bootdo.newData.domain.PersonDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:11:07
 */
@Mapper
public interface PersonDao {

	PersonDO get(Integer id);
	
	List<PersonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PersonDO person);
	
	int update(PersonDO person);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
