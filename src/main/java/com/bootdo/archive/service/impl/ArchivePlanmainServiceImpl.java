package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchivePlanmainDao;
import com.bootdo.archive.domain.ArchivePlanmainDO;
import com.bootdo.archive.service.ArchivePlanmainService;



@Service
public class ArchivePlanmainServiceImpl implements ArchivePlanmainService {
	@Autowired
	private ArchivePlanmainDao archivePlanmainDao;
	
	@Override
	public ArchivePlanmainDO get(String id){
		return archivePlanmainDao.get(id);
	}
	
	@Override
	public List<ArchivePlanmainDO> list(Map<String, Object> map){
		return archivePlanmainDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archivePlanmainDao.count(map);
	}
	
	@Override
	public int save(ArchivePlanmainDO archivePlanmain){
		return archivePlanmainDao.save(archivePlanmain);
	}
	
	@Override
	public int update(ArchivePlanmainDO archivePlanmain){
		return archivePlanmainDao.update(archivePlanmain);
	}
	
	@Override
	public int remove(String id){
		return archivePlanmainDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archivePlanmainDao.batchRemove(ids);
	}

	@Override
	public ArchivePlanmainDO getByActionprogramId(String actionprogramId) {
		return archivePlanmainDao.getByActionprogramId(actionprogramId);
	}

}
