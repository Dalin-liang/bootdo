package com.bootdo.planManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.dao.PlanTaskDao;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.planManage.service.PlanTaskService;
import com.bootdo.system.domain.UserDO;



@Service
public class PlanTaskServiceImpl implements PlanTaskService {
	@Autowired
	private PlanTaskDao planTaskDao;
	
	@Override
	public PlanTaskDO get(String id){
		return planTaskDao.get(id);
	}
	
	@Override
	public List<PlanTaskDO> list(Map<String, Object> map){
		return planTaskDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return planTaskDao.count(map);
	}
	
	@Override
	public int save(PlanTaskDO planTask){
		planTask.setId(UUID.randomUUID().toString().replace("-",""));
		UserDO userDo= ShiroUtils.getUser();
		planTask.setCreateBy(String.valueOf(userDo.getUserId()));
		planTask.setCreateDate(new Date());
		return planTaskDao.save(planTask);
	}
	
	@Override
	public int update(PlanTaskDO planTask){
		UserDO userDo= ShiroUtils.getUser();
		planTask.setUpdateBy(String.valueOf(userDo.getUserId()));
		planTask.setUpdateDate(new Date());
		return planTaskDao.update(planTask);
	}
	
	@Override
	public int remove(String id){
		return planTaskDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return planTaskDao.batchRemove(ids);
	}

	@Override
	public void deleteByPlanMainId(String planId) {
		planTaskDao.deleteByPlanMainId(planId);
		
	}

	@Override
	public List<PlanTaskDO> getByPlanId(String planId) {
		return planTaskDao.getByPlanId(planId);
	}

	@Override
	public void deleteByNotInTaskIds(String[] planTaskIds, String planMainId) {
		planTaskDao.deleteByNotInTaskIds(planTaskIds,planMainId);
	}

}
