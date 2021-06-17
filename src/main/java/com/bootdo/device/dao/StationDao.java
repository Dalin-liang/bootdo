package com.bootdo.device.dao;

import com.bootdo.device.domain.StationDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 物联设备表
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
@Mapper
public interface StationDao {

	StationDO get(Integer stId);
	
	List<StationDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StationDO station);
	
	int update(StationDO station);
	
	int remove(Integer st_id);
	
	int batchRemove(Integer[] stIds);
}
