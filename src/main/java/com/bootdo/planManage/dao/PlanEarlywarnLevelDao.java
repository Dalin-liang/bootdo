package com.bootdo.planManage.dao;

import com.bootdo.planManage.domain.PlanEarlywarnLevelDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预警级别
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:49
 */
@Mapper
public interface PlanEarlywarnLevelDao {

	PlanEarlywarnLevelDO get(String id);
	
	List<PlanEarlywarnLevelDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PlanEarlywarnLevelDO planEarlywarnLevel);
	
	int update(PlanEarlywarnLevelDO planEarlywarnLevel);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<PlanEarlywarnLevelDO> getEarlywarnLevel();

	int changeStatus(@Param("id") String id, @Param("status") String status);

    String getIdByName(@Param("name") String name);

    String getIdByNames(@Param("accidentTypeName") String accidentTypeName, @Param("earlywarnTypeName") String earlywarnTypeName, @Param("earlywarnLevelName") String earlywarnLevelName);
}
