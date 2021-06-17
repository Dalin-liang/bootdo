package com.bootdo.support.dao;

import com.bootdo.support.dto.EquipstatusDO;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 装备状态管理
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-08 10:59:20
 */
@Mapper
public interface EquipstatusDao {

	EquipstatusDO get(String id);
	
	List<Map<String,Object>> getAll();
	
	List<EquipstatusDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EquipstatusDO equipstatus);
	
	int update(EquipstatusDO equipstatus);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int changeStatus(@Param("id") String id, @Param("enabled") String enabled);
}
