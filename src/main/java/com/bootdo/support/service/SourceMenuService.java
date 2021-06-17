package com.bootdo.support.service;

import com.bootdo.support.entity.SourceDO;
import com.bootdo.support.entity.SourceMenuDO;

import java.util.List;
import java.util.Map;

/**
 * 接报来源详细类目表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:12:25
 */
public interface SourceMenuService {
	
	SourceMenuDO get(Long id);
	
	List<SourceMenuDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SourceMenuDO sourceMenu);
	
	int update(SourceMenuDO sourceMenu);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<SourceDO> getWatchSource();

	int changeStatus(String id, String enabled);

    List<SourceMenuDO> getBySourceId(String id);

    List<SourceMenuDO> getLastBySourceIdForSms(String id);
}
