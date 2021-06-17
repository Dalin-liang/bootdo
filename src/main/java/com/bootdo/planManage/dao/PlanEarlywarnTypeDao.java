package com.bootdo.planManage.dao;

import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预警类别
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
@Mapper
public interface PlanEarlywarnTypeDao {

	PlanEarlywarnTypeDO get(String id);
	
	List<PlanEarlywarnTypeDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PlanEarlywarnTypeDO planEarlywarnType);
	
	int update(PlanEarlywarnTypeDO planEarlywarnType);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<PlanEarlywarnTypeDO> getEarlywarnType();

	int changeStatus(@Param("id") String id, @Param("status") String status);

	List<PlanEarlywarnTypeDO> getEarlyWarnTypeAndExpertInfo();

    String getIdByName(@Param("name") String name);

    String getIdByNames(@Param("accidentTypeName") String accidentTypeName, @Param("earlywarnTypeName") String earlywarnTypeName);
}
