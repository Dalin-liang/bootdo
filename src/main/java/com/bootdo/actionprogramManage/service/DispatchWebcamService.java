package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchWebcamDO;

import java.util.List;
import java.util.Map;

/**
 * 摄像头调度表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:17
 */
public interface DispatchWebcamService {
	
	DispatchWebcamDO get(String id);
	
	List<DispatchWebcamDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchWebcamDO dispatchWebcam);
	
	int update(DispatchWebcamDO dispatchWebcam);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
