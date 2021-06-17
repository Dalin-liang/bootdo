package com.bootdo.planManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.planManage.dao.PlanEarlywarnLevelDao;
import com.bootdo.planManage.domain.PlanEarlywarnLevelDO;
import com.bootdo.planManage.service.PlanEarlywarnLevelService;



@Service
public class PlanEarlywarnLevelServiceImpl implements PlanEarlywarnLevelService {
	@Autowired
	private PlanEarlywarnLevelDao planEarlywarnLevelDao;
	
	@Override
	public PlanEarlywarnLevelDO get(String id){
		return planEarlywarnLevelDao.get(id);
	}
	
	@Override
	public List<PlanEarlywarnLevelDO> list(Map<String, Object> map){
		return planEarlywarnLevelDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return planEarlywarnLevelDao.count(map);
	}
	
	@Override
	public int save(PlanEarlywarnLevelDO planEarlywarnLevel){
		planEarlywarnLevel.setId(UUID.randomUUID().toString().replace("-",""));
		planEarlywarnLevel.setUpdateDate(new Date());
		return planEarlywarnLevelDao.save(planEarlywarnLevel);
	}
	
	@Override
	public int update(PlanEarlywarnLevelDO planEarlywarnLevel){
		planEarlywarnLevel.setUpdateDate(new Date());
		return planEarlywarnLevelDao.update(planEarlywarnLevel);
	}
	
	@Override
	public int remove(String id){
		return planEarlywarnLevelDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return planEarlywarnLevelDao.batchRemove(ids);
	}

	@Override
	public List<PlanEarlywarnLevelDO> getEarlywarnLevel() {
		return planEarlywarnLevelDao.getEarlywarnLevel();
	}

	@Override
	public int changeStatus(String id, String status) {
		return planEarlywarnLevelDao.changeStatus(id,status);
	}

	@Override
	public String getIdByName(String name) {
		return planEarlywarnLevelDao.getIdByName(name);
	}

	@Override
	public String getIdByNames(String accidentTypeName, String earlywarnTypeName, String earlywarnLevelName) {
		return planEarlywarnLevelDao.getIdByNames( accidentTypeName,  earlywarnTypeName,  earlywarnLevelName);
	}

}
