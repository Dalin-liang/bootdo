package com.bootdo.device.dao;

import com.bootdo.device.domain.HistorydataDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 历史数据
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
@Mapper
public interface HistorydataDao {

	HistorydataDO get(Integer hdId);
	
	List<HistorydataDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(HistorydataDO historydata);
	
	int update(HistorydataDO historydata);
	
	int remove(Integer hd_id);
	
	int batchRemove(Integer[] hdIds);
}
