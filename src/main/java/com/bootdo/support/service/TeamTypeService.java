package com.bootdo.support.service;

import com.bootdo.support.dto.SupportTeamTypeDTO;
import com.bootdo.support.entity.SupportTeamType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeamTypeService {

    int insert(SupportTeamType supportGoodsPop);

    List<Map<String,Object>> get(SupportTeamTypeDTO supportTeamTypeDTO);

    int update(SupportTeamType supportTeamType);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
}
