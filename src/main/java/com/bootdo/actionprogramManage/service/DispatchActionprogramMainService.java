package com.bootdo.actionprogramManage.service;

import com.bootdo.actionprogramManage.domain.DispatchActionprogramMainDO;
import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.support.entity.ReceiveInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 应急执行方案主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
public interface DispatchActionprogramMainService {
	
	DispatchActionprogramMainDO get(String id);
	
	List<DispatchActionprogramMainDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DispatchActionprogramMainDO dispatchActionprogramMain);
	
	int update(DispatchActionprogramMainDO dispatchActionprogramMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void updateProgram(String actionprogramMain, String planMain, String respdept, String task);
	
	String productProgram(String receiveinfoId ,String planmainId) throws Exception;
	
//	void actionProgram(List<PlanRespDeptDO> planRespDeptDOList, List<PlanTaskDO> planTaskList, String actionprogramId, Date now);

	/**
	 * @param entityJsonStr 实体类的JSON字符串，传入时，记得加上，标识符flag：值只能是  task 和 respdept 两种
	 * @return
	 */
	int addTaskORRespDept(String entityJsonStr);
	
	/**
	 * @param entityId 实体类id
	 * @param entityFlag 实体类的标识符flag：值只能是  task 和 respdept 两种
	 * @return
	 */
	int logicDeleteTaskORRespDept(String entityId, String entityFlag);
	
	/**
	 * @param entityJsonStr 实体类的JSON字符串
	 * @return
	 */
	int updateTaskORRespDept(String entityJsonStr);
	
	/**
	 * @param actionProgramId 方案的id
	 * @return
	 */
	List<Map<String, Object>> getTaskAndRespDept(String actionProgramId);
	
	/**
	 * 
	 * @param receiveinfoId watch_receiveinfo表id
	 * @param latLon 经纬度
	 * @param eventaddr 事件地址
	 * @return
	 */
	int updateReceiveinfoAndEarlywarn(String receiveinfoId, String latLon, String eventaddr);
	/**
	 * 结案
	 * @param actionprogramId 方案id

	 * @return
	 */
	int closeCase(String actionprogramId);
	
	/**
	 * 
	 * @param actionProgramId 方案id
	 * @param name 任务名称
	 * @param content 任务内容
	 * @param liabilityMan 任务负责人
	 * @return
	 */
	List<Map<String, Object>> getTaskAndRespDeptByParams(String actionProgramId,String name,String content,String liabilityMan);

	List<Map<String, Object>> getEventProcess();
	
	List<Map<String, Object>> getTimeAxisData(String actionprogramId);
	
	List<Map<String, Object>> getArchivelog(String actionprogramId);

    List<Map<String, Object>> getAllName();

    List<Map<String, Object>> getLastNameForSms();
}
