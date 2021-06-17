package com.bootdo.planManage.dao;

import com.bootdo.planManage.domain.PlanMainDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预案表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
@Mapper
public interface PlanMainDao {

	PlanMainDO get(String id);
	
	List<PlanMainDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PlanMainDO planMain);
	
	int update(PlanMainDO planMain);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	int changeStatus(@Param("id") String id, @Param("enabled") String enabled);

	List<PlanMainDO> getByLevelId(@Param("levelId") String levelId);

	List<PlanMainDO> getByParamName(@Param("accidentName") String accidentName, @Param("warnTypeName") String warnTypeName, @Param("warnLevelName") String warnLevelName);

	int updatePlanMainUseTime(@Param("planMainUseTime") int planMainUseTime,@Param("planMainId") String planMainId);

    List<PlanMainDO> actionRecordlist(Map<String, Object> map);

	int actionRecordlistCount(Map<String, Object> map);

	List<Map<String, Object>> getAllName();

    List<Map<String, Object>> getLastNameForSms();
}
