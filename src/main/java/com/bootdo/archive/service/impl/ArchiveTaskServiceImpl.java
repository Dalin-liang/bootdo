package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveTaskDao;
import com.bootdo.archive.domain.ArchiveTaskDO;
import com.bootdo.archive.service.ArchiveTaskService;



@Service
public class ArchiveTaskServiceImpl implements ArchiveTaskService {
	@Autowired
	private ArchiveTaskDao archiveTaskDao;
	
	@Override
	public ArchiveTaskDO get(String id){
		return archiveTaskDao.get(id);
	}
	
	@Override
	public List<ArchiveTaskDO> list(Map<String, Object> map){
		return archiveTaskDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveTaskDao.count(map);
	}
	
	@Override
	public int save(ArchiveTaskDO archiveTask){
		return archiveTaskDao.save(archiveTask);
	}
	
	@Override
	public int update(ArchiveTaskDO archiveTask){
		return archiveTaskDao.update(archiveTask);
	}
	
	@Override
	public int remove(String id){
		return archiveTaskDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveTaskDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getByActionprogramIdAndPlanMainId(String actionprogramId, String planMainId) {
		return archiveTaskDao.getByActionprogramIdAndPlanMainId(actionprogramId,planMainId);
	}

}
