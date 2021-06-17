package com.bootdo.device.service;

import com.bootdo.device.domain.HistorydataDO;

import java.util.List;
import java.util.Map;

/**
 * 历史数据
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
public interface HistorydataService {
	
	HistorydataDO get(Integer hdId);
	
	List<HistorydataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(HistorydataDO historydata);
	
	int update(HistorydataDO historydata);
	
	int remove(Integer hdId);
	
	int batchRemove(Integer[] hdIds);
}
