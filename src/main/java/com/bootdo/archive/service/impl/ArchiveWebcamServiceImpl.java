package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveWebcamDao;
import com.bootdo.archive.domain.ArchiveWebcamDO;
import com.bootdo.archive.service.ArchiveWebcamService;



@Service
public class ArchiveWebcamServiceImpl implements ArchiveWebcamService {
	@Autowired
	private ArchiveWebcamDao archiveWebcamDao;
	
	@Override
	public ArchiveWebcamDO get(String id){
		return archiveWebcamDao.get(id);
	}
	
	@Override
	public List<ArchiveWebcamDO> list(Map<String, Object> map){
		return archiveWebcamDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveWebcamDao.count(map);
	}
	
	@Override
	public int save(ArchiveWebcamDO archiveWebcam){
		return archiveWebcamDao.save(archiveWebcam);
	}
	
	@Override
	public int update(ArchiveWebcamDO archiveWebcam){
		return archiveWebcamDao.update(archiveWebcam);
	}
	
	@Override
	public int remove(String id){
		return archiveWebcamDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveWebcamDao.batchRemove(ids);
	}
	
}
