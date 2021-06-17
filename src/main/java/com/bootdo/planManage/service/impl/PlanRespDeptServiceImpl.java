package com.bootdo.planManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.dao.PlanRespDeptDao;
import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.planManage.service.PlanRespDeptService;
import com.bootdo.system.domain.UserDO;



@Service
public class PlanRespDeptServiceImpl implements PlanRespDeptService {
	@Autowired
	private PlanRespDeptDao planRespDeptDao;
	
	@Override
	public PlanRespDeptDO get(String id){
		return planRespDeptDao.get(id);
	}
	
	@Override
	public List<PlanRespDeptDO> list(Map<String, Object> map){
		return planRespDeptDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return planRespDeptDao.count(map);
	}
	
	@Override
	public int save(PlanRespDeptDO planRespDept){
		planRespDept.setId(UUID.randomUUID().toString().replace("-",""));
		UserDO userDo= ShiroUtils.getUser();
		planRespDept.setCreateBy(String.valueOf(userDo.getUserId()));
		planRespDept.setCreateDate(new Date());
		return planRespDeptDao.save(planRespDept);
	}
	
	@Override
	public int update(PlanRespDeptDO planRespDept){
		UserDO userDo= ShiroUtils.getUser();
		planRespDept.setUpdateBy(String.valueOf(userDo.getUserId()));
		planRespDept.setUpdateDate(new Date());
		return planRespDeptDao.update(planRespDept);
	}
	
	@Override
	public int remove(String id){
		return planRespDeptDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return planRespDeptDao.batchRemove(ids);
	}

	@Override
	public void deleteByPlanMainId(String planId) {
		 planRespDeptDao.deleteByPlanMainId(planId);
	}

	@Override
	public List<PlanRespDeptDO> getByPlanId(String planId) {
		return planRespDeptDao.getByPlanId(planId);
	}

	@Override
	public void deleteByNotInRespDeptIds(String[] planRespDeptIds, String planMainId) {
		planRespDeptDao.deleteByNotInRespDeptIds(planRespDeptIds,planMainId);
	}

}
