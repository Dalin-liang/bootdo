package com.bootdo.actionprogramManage.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.actionprogramManage.dao.DispatchTaskFeedbackDao;
import com.bootdo.actionprogramManage.dao.DispatchTaskFeedbackDetailDao;
import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.actionprogramManage.domain.DispatchRespdeptDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;
import com.bootdo.actionprogramManage.service.DispatchLogService;
import com.bootdo.actionprogramManage.service.DispatchRespdeptService;
import com.bootdo.actionprogramManage.service.DispatchTaskFeedbackDetailService;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.dispatch.center.service.BaseEventService;



@Service
public class DispatchTaskFeedbackDetailServiceImpl implements DispatchTaskFeedbackDetailService {
	@Autowired
	private DispatchTaskFeedbackDetailDao dispatchTaskFeedbackDetailDao;
	@Autowired
	private DispatchLogService logService;
	@Autowired
	private DispatchTaskFeedbackDao dispatchTaskFeedbackDao;
	@Autowired
	private DispatchTaskFeedbackDetailDao detailDao;
	@Autowired
	private DispatchTaskService taskService;
	@Autowired
	private DispatchRespdeptService respdeptService;
	@Autowired
	private BaseEventService eventService;
	
	
	
	@Override
	public DispatchTaskFeedbackDetailDO get(String id){
		return dispatchTaskFeedbackDetailDao.get(id);
	}
	
	@Override
	public List<DispatchTaskFeedbackDetailDO> list(Map<String, Object> map){
		return dispatchTaskFeedbackDetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchTaskFeedbackDetailDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetail,String actionprogramId){
		DispatchTaskFeedbackDO feedBack=dispatchTaskFeedbackDao.get(dispatchTaskFeedbackDetail.getFeedbackId());
		saveLog(actionprogramId,feedBack,dispatchTaskFeedbackDetail,"add");
		return dispatchTaskFeedbackDetailDao.save(dispatchTaskFeedbackDetail);
	}
	
	@Override
	public int update(DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetail,String actionprogramId){
		DispatchTaskFeedbackDO feedBack=dispatchTaskFeedbackDao.get(dispatchTaskFeedbackDetail.getFeedbackId());
		saveLog(actionprogramId,feedBack,dispatchTaskFeedbackDetail,"update");
	
		return dispatchTaskFeedbackDetailDao.update(dispatchTaskFeedbackDetail);
	}
	
	@Override
	public int remove(String id,String actionprogramId){
		DispatchTaskFeedbackDetailDO detail=dispatchTaskFeedbackDetailDao.get(actionprogramId);
		DispatchTaskFeedbackDO feedBack=dispatchTaskFeedbackDao.get(detail.getFeedbackId());
		saveLog(actionprogramId,feedBack,detail,"delete");
		return dispatchTaskFeedbackDetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids,String actionprogramId){

		return dispatchTaskFeedbackDetailDao.batchRemove(ids);
	}

	@Override
	public int logicalDelete(String id,String actionprogramId) {
		// TODO Auto-generated method stub
		DispatchTaskFeedbackDetailDO detail=dispatchTaskFeedbackDetailDao.get(actionprogramId);
		DispatchTaskFeedbackDO feedBack=dispatchTaskFeedbackDao.get(detail.getFeedbackId());
		saveLog(actionprogramId,feedBack,detail,"delete");
		return dispatchTaskFeedbackDetailDao.logicalDelete(id);
	}
	
	public void saveLog(String actionprogramId,DispatchTaskFeedbackDO feedBack ,DispatchTaskFeedbackDetailDO detail,String operation) {
		String name="";
		String summary="";
		if(StringUtils.isNotEmpty(feedBack.getFromTable())&&feedBack.getFromTable().equals("dispatch_task")) {
			DispatchTaskDO task=taskService.get(feedBack.getId());
			name=task.getName();
		}else if(StringUtils.isNotEmpty(feedBack.getFromTable())&&feedBack.getFromTable().equals("dispatch_respdept")) {
			DispatchRespdeptDO dept=respdeptService.get(feedBack.getId());
			name=dept.getLiabilityMan();
		}
		switch (operation) {
		case "add": summary=name+"新增任务反馈"; break ;
		case "update": summary=name+"修改任务反馈";break;
		case "delete": summary=name+"删除任务反馈";break;
		}
		String userName = ShiroUtils.getUser().getUsername();
		Date now = new Date() ;
		DispatchLogDO dispatchLog=new DispatchLogDO();
		dispatchLog.setId(UUID.randomUUID().toString().replace("-", ""));
		dispatchLog.setActionprogramId(actionprogramId);
		dispatchLog.setCreateBy(userName);
		dispatchLog.setCreateDate(now);
		dispatchLog.setTime(now);
		dispatchLog.setShowBigscreen(1);
		dispatchLog.setIsDel(1);
		dispatchLog.setContent(detail.getContent());
		dispatchLog.setSummary(summary);
		int i=logService.save(dispatchLog);
		if(i>0) {
			eventService.publishLog(dispatchLog);
	
		}
	}
	
}
