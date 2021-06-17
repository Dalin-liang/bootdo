package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchiveEarlywarnDO;

import java.util.List;
import java.util.Map;

/**
 * 执行方案的预警信息归档表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-28 09:37:52
 */
public interface ArchiveEarlywarnService {
	
	ArchiveEarlywarnDO get(String id);
	
	List<ArchiveEarlywarnDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchiveEarlywarnDO archiveEarlywarn);
	
	int update(ArchiveEarlywarnDO archiveEarlywarn);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
