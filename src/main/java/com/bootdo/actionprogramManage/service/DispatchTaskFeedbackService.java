package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;

import java.util.List;
import java.util.Map;

/**
 * 任务反馈表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-23 09:02:06
 */
public interface DispatchTaskFeedbackService {
	
	DispatchTaskFeedbackDO get(String id);
	
	/**
	 * 根据参数查询任务反馈信息
	 * @param relationId 任务id
	 * @param feedbackDate 实际反馈时间yyyy-MM-dd
	 * @param deptpesonName 实际反馈人
	 * @return List
	 */
	List<DispatchTaskFeedbackDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchTaskFeedbackDO dispatchTaskFeedback,String actionprogramId);
	
	int update(DispatchTaskFeedbackDO dispatchTaskFeedback,String actionprogramId);
	
	int remove(String id,String actionprogramId);
	
	int batchRemove(String[] ids,String actionprogramId);
	/**
	 * 新增任务反馈和反馈明细
	 * @param DispatchTaskFeedbackDO 任务反馈
	 * @param List<DispatchTaskFeedbackDetailDO> 任务反馈明细
	 * @param actionprogramId 应急执行方案Id
	 * @return int
	 */
	int saveFeedbackAndDetail(DispatchTaskFeedbackDO dispatchTaskFeedback,List<DispatchTaskFeedbackDetailDO> detailDO,String actionprogramId);
	
	/**
	 * 逻辑删除任务反馈和反馈明细
	 * @param id 任务反馈表id
	 * @param actionprogramId 应急执行方案Id
	 * @return int
	 */
	int logicalDelete(String id,String actionprogramId);
	
	/**
	 * 根据参数查询任务反馈信息
	 * @param relationId 任务id
	 * @param feedbackDate 实际反馈时间yyyy-MM-dd
	 * @param deptpesonName 实际反馈人
	 * @return List
	 */
	List<Map<String,Object>> getfeedbackAndDetail(Map<String,Object> map);

	List<Map<String,Object>> getFeedBackByRelationId(String relationId);


}
