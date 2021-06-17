package com.bootdo.planManage.service.impl;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.dao.PlanEarlywarnTypeDao;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.planManage.service.PlanEarlywarnTypeService;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Service
public class PlanEarlywarnTypeServiceImpl implements PlanEarlywarnTypeService {
	@Autowired
	private PlanEarlywarnTypeDao planEarlywarnTypeDao;
	
	@Override
	public PlanEarlywarnTypeDO get(String id){
		return planEarlywarnTypeDao.get(id);
	}
	
	@Override
	public List<PlanEarlywarnTypeDO> list(Map<String, Object> map){
		return planEarlywarnTypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return planEarlywarnTypeDao.count(map);
	}
	
	@Override
	public int save(PlanEarlywarnTypeDO planEarlywarnType){
		planEarlywarnType.setId(UUID.randomUUID().toString().replace("-",""));
		UserDO userDo= ShiroUtils.getUser();
		planEarlywarnType.setCreateBy(String.valueOf(userDo.getUserId()));
		planEarlywarnType.setCreateDate(new Date());
		return planEarlywarnTypeDao.save(planEarlywarnType);
	}
	
	@Override
	public int update(PlanEarlywarnTypeDO planEarlywarnType){
		UserDO userDo= ShiroUtils.getUser();
		planEarlywarnType.setUpdateBy(String.valueOf(userDo.getUserId()));
		planEarlywarnType.setUpdateDate(new Date());
		return planEarlywarnTypeDao.update(planEarlywarnType);
	}
	
	@Override
	public int remove(String id){
		return planEarlywarnTypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return planEarlywarnTypeDao.batchRemove(ids);
	}

	@Override
	public List<PlanEarlywarnTypeDO> getEarlywarnType() {
		return planEarlywarnTypeDao.getEarlywarnType();
	}

	@Override
	public int changeStatus(String id, String status) {
		return planEarlywarnTypeDao.changeStatus(id, status);
	}

	@Override
	public List<PlanEarlywarnTypeDO> getEarlyWarnTypeAndExpertInfo() {
		return planEarlywarnTypeDao.getEarlyWarnTypeAndExpertInfo();
	}

	@Override
	public String getIdByName(String name) {
		return planEarlywarnTypeDao.getIdByName(name);
	}

	@Override
	public String getIdByNames(String accidentTypeName, String earlywarnTypeName) {
		return planEarlywarnTypeDao.getIdByNames( accidentTypeName,  earlywarnTypeName);
	}

}
