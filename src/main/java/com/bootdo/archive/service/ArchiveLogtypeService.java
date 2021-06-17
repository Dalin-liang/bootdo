package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchiveLogtypeDO;

import java.util.List;
import java.util.Map;

/**
 * 应急调度日志归档类别表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
public interface ArchiveLogtypeService {
	
	ArchiveLogtypeDO get(String id);
	
	List<ArchiveLogtypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchiveLogtypeDO archiveLogtype);
	
	int update(ArchiveLogtypeDO archiveLogtype);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
