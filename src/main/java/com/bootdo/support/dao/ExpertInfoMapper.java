package com.bootdo.support.dao;

import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.support.dto.SupportExpertInfoDTO;
import com.bootdo.support.entity.SupportExpertInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpertInfoMapper {
    int insert(SupportExpertInfo supportExpertInfo);

    List<Map<String,Object>> get(SupportExpertInfoDTO supportExpertInfoDTO);

    int update(SupportExpertInfo supportExpertInfo);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<PlanEarlywarnTypeDO> getAllEarlyWarningType();

    int deleteExpertPlantype(@Param("id") String id);

    int insertExpertPlantype(@Param("id") String id, @Param("expertInfoId")String expertInfoId, @Param("earlwarnTypeId")String earlwarnTypeId);
    
    List<Map<String,Object>> getExpertInfoByParams(Map<String,Object>params);

    List<ExpertRes> getAllExpert();

    List<ExpertRes> getAllExpertByWarnTypeId(@Param("warnTypeId") String warnTypeId);

	Map<String, Object> getByIdAndName(@Param("id") String id, @Param("name") String name);

}
