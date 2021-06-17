package com.bootdo.actionprogramManage.service.impl;

import com.bootdo.actionprogramManage.dao.DispatchTaskFeedbackDao;
import com.bootdo.actionprogramManage.dao.DispatchTaskFeedbackDetailDao;
import com.bootdo.actionprogramManage.domain.*;
import com.bootdo.actionprogramManage.service.DispatchLogService;
import com.bootdo.actionprogramManage.service.DispatchRespdeptService;
import com.bootdo.actionprogramManage.service.DispatchTaskFeedbackService;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.system.domain.UserDO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



@Service
public class DispatchTaskFeedbackServiceImpl implements DispatchTaskFeedbackService {
	@Autowired
	private DispatchTaskFeedbackDao dispatchTaskFeedbackDao;
	@Autowired
	private DispatchTaskFeedbackDetailDao detailDao;
	@Autowired
	private DispatchLogService logService;
	@Autowired
	private DispatchTaskService taskService;
	@Autowired
	private DispatchRespdeptService respdeptService;
	@Autowired
	private BaseEventService eventService;
	
	@Override
	public DispatchTaskFeedbackDO get(String id){
		return dispatchTaskFeedbackDao.get(id);
	}
	
	@Override
	public List<DispatchTaskFeedbackDO> list(Map<String, Object> map){
		return dispatchTaskFeedbackDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchTaskFeedbackDao.count(map);
	}
	
	@Override
	public int save(DispatchTaskFeedbackDO dispatchTaskFeedback,String actionprogramId){
		return dispatchTaskFeedbackDao.save(dispatchTaskFeedback);
	}
	
	@Override
	public int update(DispatchTaskFeedbackDO dispatchTaskFeedback,String actionprogramId){

		return dispatchTaskFeedbackDao.update(dispatchTaskFeedback);
	}
	
	@Override
	public int remove(String id,String actionprogramId){
		return dispatchTaskFeedbackDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids,String actionprogramId){

		return dispatchTaskFeedbackDao.batchRemove(ids);
	}

	@Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
	public int saveFeedbackAndDetail(DispatchTaskFeedbackDO dispatchTaskFeedback,
			List<DispatchTaskFeedbackDetailDO> detailDOList,String actionprogramId) {
		// TODO Auto-generated method stub
		Map<String,Object>paramMap=new HashMap<String, Object>();
		paramMap.put("relationId", dispatchTaskFeedback.getRelationId());
		List<DispatchTaskFeedbackDO> list=dispatchTaskFeedbackDao.list(paramMap);
			int i=0;
			String id=dispatchTaskFeedback.getId();
			dispatchTaskFeedbackDao.save(dispatchTaskFeedback);
	
		if(dispatchTaskFeedback!=null) {
			//查询任务信息
			for(DispatchTaskFeedbackDetailDO detail:detailDOList) {
				detail.setFeedbackId(id);
				detailDao.save(detail);
				saveLog(actionprogramId,dispatchTaskFeedback,detail,"add");
				i=1;
			}
		}
		return 	i;
	}

	@Override 
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int logicalDelete(String id,String actionprogramId) {
		// TODO Auto-generated method stub
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("feedbackId", id);
		
		DispatchTaskFeedbackDO feedBack=dispatchTaskFeedbackDao.get(id);
		List<DispatchTaskFeedbackDetailDO> detailList=detailDao.list(map);
		for(DispatchTaskFeedbackDetailDO detail  :detailList) {
			detailDao.logicalDeleteById(detail.getId());
			saveLog(actionprogramId,feedBack,detail,"delete");
		}
		return 	dispatchTaskFeedbackDao.logicalDelete(id);
	}
	
	public void saveLog(String actionprogramId,DispatchTaskFeedbackDO feedBack ,DispatchTaskFeedbackDetailDO detail,String operation) {
	
		String summary="";
		UserDO user = ShiroUtils.getUser();
		String name=user.getName();
	/*	if(StringUtils.isNotEmpty(feedBack.getFromTable())&&feedBack.getFromTable().equals("dispatch_task")) {
			DispatchTaskDO task=taskService.get(feedBack.getRelationId());
			name=task.getName();
		}else if(StringUtils.isNotEmpty(feedBack.getFromTable())&&feedBack.getFromTable().equals("dispatch_respdept")) {
			DispatchRespdeptDO dept=respdeptService.get(feedBack.getRelationId());
			name=dept.getLiabilityMan();
		}*/
		switch (operation) {
		case "add": summary=name+"新增任务反馈"; break ;
		case "update": summary=name+"修改任务反馈";break;
		case "delete": summary=name+"删除任务反馈";break;
		}

		Date now = new Date() ;
		DispatchLogDO dispatchLog=new DispatchLogDO();
		dispatchLog.setId(UUID.randomUUID().toString().replace("-", ""));
		dispatchLog.setActionprogramId(actionprogramId);
		dispatchLog.setCreateBy(user.getUsername());
		dispatchLog.setCreateDate(now);
		dispatchLog.setTime(now);
		dispatchLog.setShowBigscreen(1);
		dispatchLog.setIsDel(1);
		dispatchLog.setContent(detail.getContent());
		dispatchLog.setSummary(summary);
		dispatchLog.setAddress(feedBack.getAddress());
		int i=logService.save(dispatchLog);
		if(i>0) {
			
				eventService.publishLog(dispatchLog);
			
		}
	
	}

	@Override
	public List<Map<String, Object>> getfeedbackAndDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dispatchTaskFeedbackDao.getfeedbackAndDetail(map);
	}

	@Override
	public List<Map<String, Object>> getFeedBackByRelationId(String relationId) {
		// TODO Auto-generated method stub
		return dispatchTaskFeedbackDao.getFeedBackByRelationId(relationId);
	}
	
}
