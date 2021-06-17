package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchiveTaskFeedbackDO;

import java.util.List;
import java.util.Map;

/**
 * 任务反馈表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
public interface ArchiveTaskFeedbackService {
	
	ArchiveTaskFeedbackDO get(String id);
	
	List<ArchiveTaskFeedbackDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchiveTaskFeedbackDO archiveTaskFeedback);
	
	int update(ArchiveTaskFeedbackDO archiveTaskFeedback);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
