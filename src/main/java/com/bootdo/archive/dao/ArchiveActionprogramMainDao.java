package com.bootdo.archive.dao;

import com.bootdo.archive.domain.ArchiveActionprogramMainDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 应急执行方案归档主表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
@Mapper
public interface ArchiveActionprogramMainDao {

	ArchiveActionprogramMainDO get(String id);
	
	List<Map<String,Object>> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArchiveActionprogramMainDO archiveActionprogramMain);
	
	int update(ArchiveActionprogramMainDO archiveActionprogramMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);


	List<Map<String, Object>> getByParamName(@Param("accidentName") String accidentName, @Param("warnTypeName") String warnTypeName, @Param("warnLevelName") String warnLevelName);

	List<Map<String, Object>> getByWarnTypeId(@Param("warnTypeId") String warnTypeId);

	
	int saveFromDispatch(String actionprogram_id);

	List<Map<String, Object>> getProgramMainDetailByWarnTypeId(String eventId);
	
	List<Map<String, Object>> getByLostParam(
			@Param("accidentName") String accidentName, 
			@Param("warnTypeName") String warnTypeName, 
			@Param("warnLevelName") String warnLevelName, 
			@Param("code") String code, 
			@Param("name") String name, 
			@Param("beginTime") String beginTime, 
			@Param("endTime") String endTime,
			@Param("receiveBtime") String receiveBtime,
			@Param("receiveEtime") String receiveEtime);
	
	Map<String,Object>getCountByCase();

	List<Map<String, Object>> getEventType(@Param("flag")String flag);

	List<Map<String, Object>> getEventLevel(@Param("flag")String flag);

	List<Map<String, Object>> getEventImport(@Param("flag")String flag);
	List<Map<String, Object>> getEventDayData();
	List<Map<String, Object>> getEventCurMonth();
	Integer getEventYoYCompared(@Param("eventType") String eventType);
	Integer getEventMoMCompared(@Param("eventType") String eventType);

	List<Map<String, Object>> getEventDataCompared(@Param("flag")String flag);

    List<Map<String, Object>> getEventMonthsData();
}
