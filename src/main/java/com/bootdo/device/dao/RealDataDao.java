package com.bootdo.device.dao;

import com.bootdo.device.domain.RealDataDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 监测实时
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
@Mapper
public interface RealDataDao {

	RealDataDO get(Integer rdId);
	
	List<RealDataDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(RealDataDO realData);
	
	int update(RealDataDO realData);
	
	int remove(Integer rd_id);
	
	int batchRemove(Integer[] rdIds);
}
