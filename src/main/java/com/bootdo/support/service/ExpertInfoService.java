package com.bootdo.support.service;

import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.support.dto.SupportExpertInfoDTO;
import com.bootdo.support.entity.SupportExpertInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpertInfoService {
    int insert(SupportExpertInfo supportExpertInfo);

    List<Map<String,Object>> get(SupportExpertInfoDTO supportExpertInfoDTO);
    
    int update(SupportExpertInfo supportExpertInfo);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<PlanEarlywarnTypeDO> getAllEarlyWarningType();

    int deleteExpertPlantype(@Param("id") String id);

    int insertExpertPlantype(@Param("id") String id, @Param("expertInfoId")String expertInfoId, @Param("earlwarnTypeId")String earlwarnTypeId);
    /**
	 * 根据参数查询专家列表
	 * @param name 专家姓名
	 * @param typeof 专家类别
	 * @param warn_id 预警类别Id
	 * @param warn_name 预警类别名称
	 * @return
	 */
    List<Map<String,Object>> getExpertInfoByParams(Map<String,Object>params);

    List<ExpertRes> getAllExpertByWarnTypeId(String warnTypeId);

}
