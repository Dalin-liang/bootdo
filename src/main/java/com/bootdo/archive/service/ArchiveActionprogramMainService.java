package com.bootdo.archive.service;

import com.bootdo.archive.domain.ArchiveActionprogramMainDO;

import java.util.List;
import java.util.Map;

/**
 * 应急执行方案归档主表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-28 09:37:52
 */
public interface ArchiveActionprogramMainService {
	
	ArchiveActionprogramMainDO get(String id);
	
	List<Map<String,Object>> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArchiveActionprogramMainDO archiveActionprogramMain);
	
	int update(ArchiveActionprogramMainDO archiveActionprogramMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	/**
	 * 
	 * @param warnTypeId 预警类别ID
	 * @param accidentName 事故类型 
	 * @param warnTypeName 预警类别
	 * @param warnLevelName 预警级别
	 * @return
	 */
	List<Map<String, Object>> getProgramMainByParam(String warnTypeId, String accidentName, String warnTypeName, String warnLevelName);
	
	/**
	 * 预警类别ID
	 * @param eventId
	 * @return
	 */
	List<Map<String, Object>> getProgramMainDetailByWarnTypeId(String eventId);

	/**
	 * 案例综合查询
	 * @param accidentName 事件类型
	 * @param warnTypeName 预警类别
	 * @param warnLevelName 预警级别
	 * @param code 事件编号
	 * @param name 预案名称
	 * @param beginTime 事件发生时间起
	 * @param endTime 事件发生时间止
	 * @return
	 */
	List<Map<String, Object>> getByLostParam(String accidentName,String warnTypeName, String warnLevelName, String code, String name, String beginTime, String endTime,String receiveBtime, String receiveEtime);
	
	/**
	 * 统计未处理与已结案数量
	 * @return
	 */
	Map<String,Object>getCountByCase();

	void updateProgram(String actionprogramMain, String planMain, String respdept, String task);

	List<Map<String, Object>> getEventType(String flag);

	List<Map<String, Object>> getEventLevel(String flag);

	List<Map<String, Object>> getEventImport(String flag);
	List<Map<String, Object>> getEventDayData();
	List<Map<String, Object>> getEventCurMonth();
	Integer getEventYoYCompared(String eventType);
	Integer getEventMoMCompared(String eventType);


	int productPlan(String actionprogramMain, String planMain, String respdept, String task) throws Exception;

	List<Map<String, Object>> getEventDataCompared(String flag);

	List<Map<String, Object>> getEventMonthsData();
}
