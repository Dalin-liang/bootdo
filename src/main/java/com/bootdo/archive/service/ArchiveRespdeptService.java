package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchiveRespdeptDO;

import java.util.List;
import java.util.Map;

/**
 * 执行方案的预案响应部门归档表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-28 09:37:52
 */
public interface ArchiveRespdeptService {
	
	ArchiveRespdeptDO get(String id);
	
	List<ArchiveRespdeptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchiveRespdeptDO archiveRespdept);
	
	int update(ArchiveRespdeptDO archiveRespdept);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

    List<ArchiveRespdeptDO> getByPlanMainId(String planMainId);
}
