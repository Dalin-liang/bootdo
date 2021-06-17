package com.bootdo.actionprogramManage.dao;

import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 任务反馈明细表
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-22 16:48:17
 */
@Mapper
public interface DispatchTaskFeedbackDetailDao {

	DispatchTaskFeedbackDetailDO get(String id);
	
	List<DispatchTaskFeedbackDetailDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetail);
	
	int update(DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetail);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int logicalDelete(String feedBackId);
	
	int logicalDeleteById(String id);
	
	int removeByTask(String actionprogramId);
	
	int removeByRespdept(String actionprogramId);



}
