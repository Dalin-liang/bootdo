package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchiveWebcamDO;

import java.util.List;
import java.util.Map;

/**
 * 摄像头调度表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:08
 */
public interface ArchiveWebcamService {
	
	ArchiveWebcamDO get(String id);
	
	List<ArchiveWebcamDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchiveWebcamDO archiveWebcam);
	
	int update(ArchiveWebcamDO archiveWebcam);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
