package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dao.EquipstatusDao;
import com.bootdo.support.dto.EquipstatusDO;
import com.bootdo.support.service.EquipstatusService;
import com.bootdo.system.domain.UserDO;



@Service
public class EquipstatusServiceImpl implements EquipstatusService {
	@Autowired
	private EquipstatusDao equipstatusDao;
	
	@Override
	public EquipstatusDO get(String id){
		return equipstatusDao.get(id);
	}
	
	@Override
	public List<EquipstatusDO> list(Map<String, Object> map){
		return equipstatusDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return equipstatusDao.count(map);
	}
	
	@Override
	public int save(EquipstatusDO equipstatus){
		equipstatus.setId(UUID.randomUUID().toString().replace("-",""));
		UserDO userDo= ShiroUtils.getUser();
		equipstatus.setCreateBy(String.valueOf(userDo.getUserId()));
		equipstatus.setCreateDate(new Date());
		return equipstatusDao.save(equipstatus);
	}
	
	@Override
	public int update(EquipstatusDO equipstatus){
		UserDO userDo= ShiroUtils.getUser();
		equipstatus.setUpdateBy(String.valueOf(userDo.getUserId()));
		equipstatus.setUpdateDate(new Date());
		return equipstatusDao.update(equipstatus);
	}
	
	@Override
	public int remove(String id){
		return equipstatusDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return equipstatusDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getAll() {
		// TODO Auto-generated method stub
		return equipstatusDao.getAll();
	}

	@Override
	public int changeStatus(String id, String enabled) {
		// TODO Auto-generated method stub
		return equipstatusDao.changeStatus(id,enabled);
	}
	
}
