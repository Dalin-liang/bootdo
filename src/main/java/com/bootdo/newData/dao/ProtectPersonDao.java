package com.bootdo.newData.dao;

import com.bootdo.newData.domain.ProtectPersonDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:53:35
 */
@Mapper
public interface ProtectPersonDao {

	ProtectPersonDO get(Integer id);
	
	List<ProtectPersonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProtectPersonDO protectPerson);
	
	int update(ProtectPersonDO protectPerson);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
