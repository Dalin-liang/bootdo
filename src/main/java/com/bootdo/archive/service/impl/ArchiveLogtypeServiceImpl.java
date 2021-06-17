package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveLogtypeDao;
import com.bootdo.archive.domain.ArchiveLogtypeDO;
import com.bootdo.archive.service.ArchiveLogtypeService;



@Service
public class ArchiveLogtypeServiceImpl implements ArchiveLogtypeService {
	@Autowired
	private ArchiveLogtypeDao archiveLogtypeDao;
	
	@Override
	public ArchiveLogtypeDO get(String id){
		return archiveLogtypeDao.get(id);
	}
	
	@Override
	public List<ArchiveLogtypeDO> list(Map<String, Object> map){
		return archiveLogtypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveLogtypeDao.count(map);
	}
	
	@Override
	public int save(ArchiveLogtypeDO archiveLogtype){
		return archiveLogtypeDao.save(archiveLogtype);
	}
	
	@Override
	public int update(ArchiveLogtypeDO archiveLogtype){
		return archiveLogtypeDao.update(archiveLogtype);
	}
	
	@Override
	public int remove(String id){
		return archiveLogtypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveLogtypeDao.batchRemove(ids);
	}
	
}
