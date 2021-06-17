package com.bootdo.support.dao;

import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.vo.BaseEventVO;
import com.bootdo.support.dto.ReceiveInfoDTO;
import com.bootdo.support.entity.ReceiveInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 信息接报管理
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 09:07:54
 */
public interface ReceiveInfoMapper {

	List<Map<String,Object>> get(ReceiveInfoDTO receiveInfoDTO);

	Map<String,Object> getUniqueById(@RequestParam("id") String id);

	int insert(ReceiveInfo receiveinfo);
	
	int update(ReceiveInfo receiveinfo);
	
	int delete(String id);
	
	int batchRemove(String[] ids);

	List<Map<String,Object>> getSource();

	List<Map<String,Object>> getAccidentType(String id);

	List<BaseEventVO> getProcessingEvent();

	List<Map<String, Object>> getCurrentDutyPerson(Map<String,Object> map);

	List<Map<String, Object>> getDeptContactPerson();

	List<Map<String, Object>> getNowDutyPreson(Map<String, Object> map);

	List<Map<String,Object>> queryExamine(ReceiveInfoDTO receiveInfoDTO);

	int updateReceiveExamineType(String id);

    CenterEvent getEventById(@Param("eventId") String eventId);

	List<Map<String,Object>> getEarlyWarnTypeByAccidentId(String id);

	List<Map<String,Object>> getEarlyWarnLevelByTypeId(String id);

	int updateInHandle(String eventId);

	List<Map<String, Object>> getAPPEmergencyEvent(@Param("personId")String personId);

	List<Map<String, Object>> getAPPEmergencyEvent2(@Param("personId")String personId);

	List<Map<String, Object>> getEmergencyEventMessage(@Param("personId")String personId);

	List<Map<String, Object>> getEmergencyEventMessage2(@Param("personId")String personId);

	List<Map<String, Object>> getTaskListByEventProgramId(@Param("actionprogramId")String actionprogramId);

	List<Map<String, Object>> getReciveHandleEventInfo(@Param("personId")String personId);

	List<Map<String, Object>> getEventEmergencyPerson(@Param("liabilityId")String liabilityId);

	List<Map<String, Object>> getgetEventEmergencyPersonByRespDept(@Param("liabilityId")String liabilityId);

	List<Map<String, Object>> getReceiveEventInfo(@Param("personId")String personId);

	List<Map<String, Object>> getHandleWorkContent(@Param("personId")String personId);

	List<Map<String, Object>> getStartEventInfo(@Param("personId")String personId);
	
	List<Map<String, Object>> getMaintenancePerson(@Param("deviceType")String deviceType);
	
	List<BaseEventVO> getEventByType(@Param("sourceType")String sourceType);
	
	int updateByActionProId(String actionprogramId);
	
	List<BaseEventVO> getWarnByDevice();

    List<Map<String, Object>> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int batchEndCase(String[] ids);

	List<CommonFileDO> getAppReportEventImg(String eventId);

    List<String> getEventIdsByType(@Param("sourceType")String sourceType);

	List<BaseEventVO> getEventByIds(List<String> dataIdList);

    List<Map<String, Object>> getChartSourceType(@Param("year")String year);

	List<Map<String, Object>> getChartLevel(@Param("year")String year);

	List<Map<String, Object>> getChartMonth(@Param("year")String year);

	List<Map<String,Object>> taskList(Map<String,Object> map);

	int countTaskList(Map<String,Object> map);
}
