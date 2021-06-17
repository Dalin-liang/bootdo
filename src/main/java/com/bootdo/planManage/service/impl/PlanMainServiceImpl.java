package com.bootdo.planManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.planManage.dao.PlanMainDao;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.planManage.service.PlanMainService;
import com.bootdo.planManage.service.PlanRespDeptService;
import com.bootdo.planManage.service.PlanTaskService;


@Service
@Transactional(readOnly = true)
public class PlanMainServiceImpl implements PlanMainService {
	@Autowired
	private PlanMainDao planMainDao;
	@Autowired
	private PlanRespDeptService planRespDeptService;
	@Autowired
	private PlanTaskService planTaskService;
	
	public PlanMainDO get(String id){
		return planMainDao.get(id);
	}
	
	public List<PlanMainDO> list(Map<String, Object> map){
		return planMainDao.list(map);
	}
	
	public int count(Map<String, Object> map){
		return planMainDao.count(map);
	}
	
	@Transactional(readOnly = false)
	public int save(PlanMainDO planMain){
		return planMainDao.save(planMain);
	}
	
	@Transactional(readOnly = false)
	public int update(PlanMainDO planMain){
		return planMainDao.update(planMain);
	}
	
	@Transactional(readOnly = false)
	public int remove(String id){
		return planMainDao.remove(id);
	}
	
	@Transactional(readOnly = false)
	public int batchRemove(String[] ids){
		return planMainDao.batchRemove(ids);
	}

	@Transactional(readOnly = false)
	public void addPlan(String planMain, String respDept, String task) {
		String userId = String.valueOf(ShiroUtils.getUser().getUserId());
		Date dateTime = new Date() ;
		String planMainId = null;
		
		PlanMainDO planMainDO = JSON.parseObject(planMain, PlanMainDO.class);
		planMainDO.setId(UUID.randomUUID().toString().replace("-",""));
		planMainDO.setCreateBy(userId);
		planMainDO.setCreateDate(dateTime);
		if(planMainDao.save(planMainDO)>0){
			planMainId = planMainDO.getId();
			//响应部门
			if(respDept!=null &&!"".equals(respDept) ) {
				List<Map<String,Object>> respDeptList = JSON.parseObject(respDept, List.class);
				for (Map<String, Object> map : respDeptList) {
					PlanRespDeptDO planRespDept = JSON.parseObject(JSON.toJSONString(map), PlanRespDeptDO.class);
					planRespDept.setId(UUID.randomUUID().toString().replace("-",""));
					planRespDept.setPlanMainId(planMainId);
					planRespDept.setCreateBy(userId);
					planRespDept.setCreateDate(dateTime);
					planRespDeptService.save(planRespDept);
				}
			}
			
			//任务
			if(task!=null &&!"".equals(task) ) {
				List<Map<String,Object>> taskList = JSON.parseObject(task, List.class);
				for (Map<String, Object> map : taskList) {
					PlanTaskDO planTask = JSON.parseObject(JSON.toJSONString(map), PlanTaskDO.class);
					planTask.setId(UUID.randomUUID().toString().replace("-",""));
					planTask.setPlanMainId(planMainId);
					planTask.setCreateBy(userId);
					planTask.setCreateDate(dateTime);
					planTaskService.save(planTask);
				}
			}
		}
		
	}

	@Transactional(readOnly = false)
	public void updatePlan(String planMain, String respDept, String task) {
		String userId = String.valueOf(ShiroUtils.getUser().getUserId());
		Date dateTime = new Date() ;
		String planMainId = null;
		
		PlanMainDO planMainDO = JSON.parseObject(planMain, PlanMainDO.class);
		planMainDO.setUpdateBy(userId);
		planMainDO.setUpdateDate(dateTime);
		planMainDao.update(planMainDO);
		planMainId = planMainDO.getId();
		
		//响应部门
		ArrayList<String> respDeptIds = new ArrayList();
		if(respDept!=null &&!"".equals(respDept) ) {
			List<Map<String,Object>> respDeptList = JSON.parseObject(respDept, List.class);
			String respDeptId = null;
			for (Map<String, Object> map : respDeptList) {
				PlanRespDeptDO planRespDept = JSON.parseObject(JSON.toJSONString(map), PlanRespDeptDO.class);
				respDeptId = planRespDept.getId();
				if(planRespDeptService.get(respDeptId) != null){
					planRespDept.setUpdateBy(userId);
					planRespDept.setUpdateDate(dateTime);
					planRespDeptService.update(planRespDept);
				}else{
					planRespDept.setPlanMainId(planMainId);
					planRespDept.setCreateBy(userId);
					planRespDept.setCreateDate(dateTime);
					planRespDept.setUpdateBy(userId);
					planRespDept.setUpdateDate(dateTime);
					planRespDeptService.save(planRespDept);

					respDeptId = planRespDept.getId();
				}
				respDeptIds.add(respDeptId);
			}
		}
		if(respDeptIds.size() == 0){
			planRespDeptService.deleteByPlanMainId(planMainId);
		}else{
			String[] planRespDeptIds = (String[])respDeptIds.toArray(new String[respDeptIds.size()]);
			planRespDeptService.deleteByNotInRespDeptIds(planRespDeptIds,planMainId);
		}
		
		//任务
		ArrayList<String> taskIds = new ArrayList();
		if(task!=null &&!"".equals(task) ) {
			List<Map<String,Object>> taskList = JSON.parseObject(task, List.class);
			for (Map<String, Object> map : taskList) {
				PlanTaskDO planTask = JSON.parseObject(JSON.toJSONString(map), PlanTaskDO.class);
				String planTaskId = planTask.getId();
				if(planTaskService.get(planTaskId) != null){
					planTask.setUpdateBy(userId);
					planTask.setUpdateDate(dateTime);
					planTaskService.update(planTask);
				}else{
					planTask.setPlanMainId(planMainId);
					planTask.setCreateBy(userId);
					planTask.setCreateDate(dateTime);
					planTask.setUpdateBy(userId);
					planTask.setUpdateDate(dateTime);
					planTaskService.save(planTask);

					planTaskId = planTask.getId();
				}
				taskIds.add(planTaskId);
			}

		}
		if(taskIds.size() == 0){
			planTaskService.deleteByPlanMainId(planMainId);
		}else{
			String[] planTaskIds = (String[])taskIds.toArray(new String[taskIds.size()]);
			planTaskService.deleteByNotInTaskIds(planTaskIds,planMainId);
		}
		
	}

	@Transactional(readOnly = false)
	public int deletePlan(String id) {
		planRespDeptService.deleteByPlanMainId(id);
		planTaskService.deleteByPlanMainId(id);
		return planMainDao.remove(id);
	}

	@Transactional(readOnly = false)
	public void batchDeletePlan(String[] ids) {
		planMainDao.batchRemove(ids);
		for(int i=0;i<ids.length;i++){
			planRespDeptService.deleteByPlanMainId(ids[i]);
			planTaskService.deleteByPlanMainId(ids[i]);
		}
		
	}

	@Transactional(readOnly = false)
	public int changeStatus(String id, String enabled) {
		return planMainDao.changeStatus(id, enabled);
	}

	public List<PlanMainDO> getPlanMainByParam(String levelId, String accidentName, String warnTypeName,String warnLevelName) {
		if(StringUtils.isNotEmpty(accidentName) || StringUtils.isNotEmpty(warnTypeName) || StringUtils.isNotEmpty(warnLevelName)){
			return planMainDao.getByParamName(accidentName, warnTypeName ,warnLevelName);
		}else if(StringUtils.isNotEmpty(levelId)){
			return planMainDao.getByLevelId(levelId);
		}
		return null;
	}

	@Override
	public List<PlanMainDO> actionRecordlist(Map<String, Object> map) {
		return planMainDao.actionRecordlist(map);
	}

	@Override
	public int actionRecordlistCount(Map<String, Object> map) {
		return planMainDao.actionRecordlistCount(map);
	}

	@Override
	public List<Map<String, Object>> getAllName() {
		return planMainDao.getAllName();
	}

	@Override
	public List<Map<String, Object>> getLastNameForSms() {
		return planMainDao.getLastNameForSms();
	}

}
