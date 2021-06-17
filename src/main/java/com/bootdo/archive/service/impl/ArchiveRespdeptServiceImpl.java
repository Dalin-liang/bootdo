package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveRespdeptDao;
import com.bootdo.archive.domain.ArchiveRespdeptDO;
import com.bootdo.archive.service.ArchiveRespdeptService;



@Service
public class ArchiveRespdeptServiceImpl implements ArchiveRespdeptService {
	@Autowired
	private ArchiveRespdeptDao archiveRespdeptDao;
	
	@Override
	public ArchiveRespdeptDO get(String id){
		return archiveRespdeptDao.get(id);
	}
	
	@Override
	public List<ArchiveRespdeptDO> list(Map<String, Object> map){
		return archiveRespdeptDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveRespdeptDao.count(map);
	}
	
	@Override
	public int save(ArchiveRespdeptDO archiveRespdept){
		return archiveRespdeptDao.save(archiveRespdept);
	}
	
	@Override
	public int update(ArchiveRespdeptDO archiveRespdept){
		return archiveRespdeptDao.update(archiveRespdept);
	}
	
	@Override
	public int remove(String id){
		return archiveRespdeptDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveRespdeptDao.batchRemove(ids);
	}

	@Override
	public List<ArchiveRespdeptDO> getByPlanMainId(String planMainId) {
		return archiveRespdeptDao.getByPlanMainId(planMainId);
	}

}
