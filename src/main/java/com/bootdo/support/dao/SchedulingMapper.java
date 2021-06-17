package com.bootdo.support.dao;

import com.bootdo.support.dto.SupportSchedulingDTO;
import com.bootdo.support.entity.SupportScheduling;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SchedulingMapper {
    int insert(SupportScheduling supportGoodsTrans);


    List<Map<String,Object>> get(SupportSchedulingDTO supportGoodsTransDTO);
    
    List<Map<String,Object>> getUserByDeptId(Map<String,Object> paramMap);

	int count(Map<String,Object> map);

	int batchRemove(String[] ids);
	
    int update(SupportScheduling supportGoodsTrans);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
    
    List<Map<String,Object>> countByUser(Map<String,Object> paramMap);
    
    List<Map<String,Object>> countByDept(Map<String,Object> paramMap);

    int countByUserCount(Map<String, Object> paramMap);

    int countByDeptCount(Map<String, Object> paramMap);

    List<String> getSchedulingsDateByUserId(@Param("userId")String userId);

    List<String> getUserViewSchedulingDate(Map<String, Object> params);

    int workUpdate(SupportScheduling supportScheduling);
}
