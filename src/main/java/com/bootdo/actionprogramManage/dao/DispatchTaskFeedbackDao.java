package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 任务反馈表
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-23 09:02:06
 */
@Mapper
public interface DispatchTaskFeedbackDao {

	DispatchTaskFeedbackDO get(String id);

	DispatchTaskFeedbackDO getFeedBackByTaskId(String relationId);
	
	List<DispatchTaskFeedbackDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchTaskFeedbackDO dispatchTaskFeedback);
	
	int update(DispatchTaskFeedbackDO dispatchTaskFeedback);

	int updateByrelationId(DispatchTaskFeedbackDO dispatchTaskFeedback);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int saveFeedbackAndDetail(DispatchTaskFeedbackDO dispatchTaskFeedback,List<DispatchTaskFeedbackDetailDO> detailDO);
	
	int logicalDelete(String id);
	
	List<Map<String,Object>> getfeedbackAndDetail(String id);
	
	int removeByTask(String actionprogramId);
	
	int removeByRespdept(String actionprogramId);
	
	List<Map<String,Object>> getfeedbackAndDetail(Map<String,Object> map);
	
	List<Map<String,Object>> getFeedBackByRelationId(@Param("relationId")String relationId);
	
	
	DispatchTaskFeedbackDO getUnique(@Param("relationId")String relationId);
	

	
}
