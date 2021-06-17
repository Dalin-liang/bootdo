package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveTaskFeedbackDao;
import com.bootdo.archive.domain.ArchiveTaskFeedbackDO;
import com.bootdo.archive.service.ArchiveTaskFeedbackService;



@Service
public class ArchiveTaskFeedbackServiceImpl implements ArchiveTaskFeedbackService {
	@Autowired
	private ArchiveTaskFeedbackDao archiveTaskFeedbackDao;
	
	@Override
	public ArchiveTaskFeedbackDO get(String id){
		return archiveTaskFeedbackDao.get(id);
	}
	
	@Override
	public List<ArchiveTaskFeedbackDO> list(Map<String, Object> map){
		return archiveTaskFeedbackDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveTaskFeedbackDao.count(map);
	}
	
	@Override
	public int save(ArchiveTaskFeedbackDO archiveTaskFeedback){
		return archiveTaskFeedbackDao.save(archiveTaskFeedback);
	}
	
	@Override
	public int update(ArchiveTaskFeedbackDO archiveTaskFeedback){
		return archiveTaskFeedbackDao.update(archiveTaskFeedback);
	}
	
	@Override
	public int remove(String id){
		return archiveTaskFeedbackDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveTaskFeedbackDao.batchRemove(ids);
	}
	
}
