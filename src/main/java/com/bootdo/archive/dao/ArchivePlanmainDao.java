package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchivePlanmainDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 执行方案的预案归档主表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchivePlanmainDao {

	ArchivePlanmainDO get(String id);
	
	List<ArchivePlanmainDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchivePlanmainDO archivePlanmain);
	
	int update(ArchivePlanmainDO archivePlanmain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFromDispatch(String actionprogram_id);

    ArchivePlanmainDO getByActionprogramId(String actionprogramId);
}
