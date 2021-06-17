package com.bootdo.newData.dao;

import com.bootdo.newData.domain.DutyPersonDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 14:45:32
 */
@Mapper
public interface DutyPersonDao {

	DutyPersonDO get(Integer id);
	
	List<DutyPersonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DutyPersonDO dutyPerson);
	
	int update(DutyPersonDO dutyPerson);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
