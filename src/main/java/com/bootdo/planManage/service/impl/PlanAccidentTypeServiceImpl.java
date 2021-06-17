package com.bootdo.planManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.dao.PlanAccidentTypeDao;
import com.bootdo.planManage.domain.PlanAccidentTypeDO;
import com.bootdo.planManage.service.PlanAccidentTypeService;
import com.bootdo.system.domain.UserDO;



@Service
public class PlanAccidentTypeServiceImpl implements PlanAccidentTypeService {
	@Autowired
	private PlanAccidentTypeDao planAccidentTypeDao;
	
	@Override
	public PlanAccidentTypeDO get(String id){
		return planAccidentTypeDao.get(id);
	}
	
	@Override
	public List<PlanAccidentTypeDO> list(Map<String, Object> map){
		return planAccidentTypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return planAccidentTypeDao.count(map);
	}
	
	@Override
	public int save(PlanAccidentTypeDO planAccidentType){
		planAccidentType.setId(UUID.randomUUID().toString().replace("-",""));
		UserDO userDo= ShiroUtils.getUser();
		planAccidentType.setCreateBy(String.valueOf(userDo.getUserId()));
		planAccidentType.setCreateDate(new Date());
		return planAccidentTypeDao.save(planAccidentType);
	}
	
	@Override
	public int update(PlanAccidentTypeDO planAccidentType){
		UserDO userDo= ShiroUtils.getUser();
		planAccidentType.setUpdateBy(String.valueOf(userDo.getUserId()));
		planAccidentType.setUpdateDate(new Date());
		return planAccidentTypeDao.update(planAccidentType);
	}
	
	@Override
	public int remove(String id){
		return planAccidentTypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return planAccidentTypeDao.batchRemove(ids);
	}

	@Override
	public List<PlanAccidentTypeDO> getAccidentType() {
		return planAccidentTypeDao.getAccidentType();
	}

	@Override
	public int changeStatus(String id ,String status) {
		return planAccidentTypeDao.changeStatus(id ,status);
	}

	@Override
	public List<Map<String, Object>> getDpetList() {
		return planAccidentTypeDao.getDpetList();
	}

	@Override
	public String getIdByName(String name) {
		return planAccidentTypeDao.getIdByName(name);
	}

}
