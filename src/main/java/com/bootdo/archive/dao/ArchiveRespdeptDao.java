package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchiveRespdeptDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 执行方案的预案响应部门归档表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchiveRespdeptDao {

	ArchiveRespdeptDO get(String id);
	
	List<ArchiveRespdeptDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchiveRespdeptDO archiveRespdept);
	
	int update(ArchiveRespdeptDO archiveRespdept);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFromDispatch(String actionprogram_id);

    List<ArchiveRespdeptDO> getByPlanMainId(String planMainId);
}
