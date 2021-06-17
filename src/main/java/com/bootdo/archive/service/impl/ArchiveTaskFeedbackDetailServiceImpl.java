package com.bootdo.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.archive.dao.ArchiveTaskFeedbackDetailDao;
import com.bootdo.archive.domain.ArchiveTaskFeedbackDetailDO;
import com.bootdo.archive.service.ArchiveTaskFeedbackDetailService;



@Service
public class ArchiveTaskFeedbackDetailServiceImpl implements ArchiveTaskFeedbackDetailService {
	@Autowired
	private ArchiveTaskFeedbackDetailDao archiveTaskFeedbackDetailDao;
	
	@Override
	public ArchiveTaskFeedbackDetailDO get(String id){
		return archiveTaskFeedbackDetailDao.get(id);
	}
	
	@Override
	public List<ArchiveTaskFeedbackDetailDO> list(Map<String, Object> map){
		return archiveTaskFeedbackDetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveTaskFeedbackDetailDao.count(map);
	}
	
	@Override
	public int save(ArchiveTaskFeedbackDetailDO archiveTaskFeedbackDetail){
		return archiveTaskFeedbackDetailDao.save(archiveTaskFeedbackDetail);
	}
	
	@Override
	public int update(ArchiveTaskFeedbackDetailDO archiveTaskFeedbackDetail){
		return archiveTaskFeedbackDetailDao.update(archiveTaskFeedbackDetail);
	}
	
	@Override
	public int remove(String id){
		return archiveTaskFeedbackDetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveTaskFeedbackDetailDao.batchRemove(ids);
	}
	
}
