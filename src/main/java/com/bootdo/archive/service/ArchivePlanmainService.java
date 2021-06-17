package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchivePlanmainDO;

import java.util.List;
import java.util.Map;

/**
 * 执行方案的预案归档主表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-28 09:37:52
 */
public interface ArchivePlanmainService {
	
	ArchivePlanmainDO get(String id);
	
	List<ArchivePlanmainDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchivePlanmainDO archivePlanmain);
	
	int update(ArchivePlanmainDO archivePlanmain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	ArchivePlanmainDO getByActionprogramId(String actionprogramId);
}
