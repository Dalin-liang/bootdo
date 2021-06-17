package com.bootdo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.common.dao.CommonFileDao;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.service.CommonFileService;



@Service
public class CommonFileServiceImpl implements CommonFileService {
	@Autowired
	private CommonFileDao commonFileDao;
	
	@Override
	public CommonFileDO get(String id){
		return commonFileDao.get(id);
	}
	
	@Override
	public List<CommonFileDO> list(Map<String, Object> map){
		return commonFileDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return commonFileDao.count(map);
	}
	
	@Override
	public int save(CommonFileDO commonFile){
		return commonFileDao.save(commonFile);
	}
	
	@Override
	public int update(CommonFileDO commonFile){
		return commonFileDao.update(commonFile);
	}
	
	@Override
	public int remove(String id){
		return commonFileDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return commonFileDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getMappingFile(String relationId) {
		// TODO Auto-generated method stub
		return commonFileDao.getMappingFile(relationId);
	}
	
}
