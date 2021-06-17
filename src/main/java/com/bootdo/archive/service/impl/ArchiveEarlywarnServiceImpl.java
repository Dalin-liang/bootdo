package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveEarlywarnDao;
import com.bootdo.archive.domain.ArchiveEarlywarnDO;
import com.bootdo.archive.service.ArchiveEarlywarnService;



@Service
public class ArchiveEarlywarnServiceImpl implements ArchiveEarlywarnService {
	@Autowired
	private ArchiveEarlywarnDao archiveEarlywarnDao;
	
	@Override
	public ArchiveEarlywarnDO get(String id){
		return archiveEarlywarnDao.get(id);
	}
	
	@Override
	public List<ArchiveEarlywarnDO> list(Map<String, Object> map){
		return archiveEarlywarnDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveEarlywarnDao.count(map);
	}
	
	@Override
	public int save(ArchiveEarlywarnDO archiveEarlywarn){
		return archiveEarlywarnDao.save(archiveEarlywarn);
	}
	
	@Override
	public int update(ArchiveEarlywarnDO archiveEarlywarn){
		return archiveEarlywarnDao.update(archiveEarlywarn);
	}
	
	@Override
	public int remove(String id){
		return archiveEarlywarnDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveEarlywarnDao.batchRemove(ids);
	}
	
}
