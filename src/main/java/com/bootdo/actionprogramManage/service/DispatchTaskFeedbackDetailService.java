package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;

import java.util.List;
import java.util.Map;

/**
 * 任务反馈明细表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-22 16:48:17
 */
public interface DispatchTaskFeedbackDetailService {
	
	DispatchTaskFeedbackDetailDO get(String id);
	
	List<DispatchTaskFeedbackDetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetail,String actionprogramId);
	
	int update(DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetail,String actionprogramId);
	
	int remove(String id,String actionprogramId);
	
	int batchRemove(String[] ids,String actionprogramId);
	
	int logicalDelete(String feedBackId,String actionprogramId);
	


}
