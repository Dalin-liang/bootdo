package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchiveEarlywarnDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 执行方案的预警信息归档表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchiveEarlywarnDao {

	ArchiveEarlywarnDO get(String id);
	
	List<ArchiveEarlywarnDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchiveEarlywarnDO archiveEarlywarn);
	
	int update(ArchiveEarlywarnDO archiveEarlywarn);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFromDispatch(String actionprogram_id);

}
