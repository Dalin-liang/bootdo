package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveLogDao;
import com.bootdo.archive.domain.ArchiveLogDO;
import com.bootdo.archive.service.ArchiveLogService;



@Service
public class ArchiveLogServiceImpl implements ArchiveLogService {
	@Autowired
	private ArchiveLogDao archiveLogDao;
	
	@Override
	public ArchiveLogDO get(String id){
		return archiveLogDao.get(id);
	}
	
	@Override
	public List<ArchiveLogDO> list(Map<String, Object> map){
		return archiveLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveLogDao.count(map);
	}
	
	@Override
	public int save(ArchiveLogDO archiveLog){
		return archiveLogDao.save(archiveLog);
	}
	
	@Override
	public int update(ArchiveLogDO archiveLog){
		return archiveLogDao.update(archiveLog);
	}
	
	@Override
	public int remove(String id){
		return archiveLogDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveLogDao.batchRemove(ids);
	}
	
}
