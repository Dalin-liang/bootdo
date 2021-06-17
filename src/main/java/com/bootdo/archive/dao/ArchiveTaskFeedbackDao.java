package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchiveTaskFeedbackDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务反馈表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchiveTaskFeedbackDao {

	ArchiveTaskFeedbackDO get(String id);
	
	List<ArchiveTaskFeedbackDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchiveTaskFeedbackDO archiveTaskFeedback);
	
	int update(ArchiveTaskFeedbackDO archiveTaskFeedback);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFromDispatchByTask(String actionprogram_id);
	
	int saveFromDispatchByRespdept(String actionprogram_id);
	
	
}
