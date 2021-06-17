package com.bootdo.support.service;

import com.bootdo.support.dto.DailyFileDO;

import java.util.List;
import java.util.Map;

/**
 * 值班日志附件表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-07 15:39:51
 */
public interface DailyFileService {
	
	DailyFileDO get(String id);
	
	List<DailyFileDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DailyFileDO dailyFile);
	
	int update(DailyFileDO dailyFile);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<DailyFileDO> getByDailyId(String dailyId);

	int removeByDailyId(String dailyId);
}
