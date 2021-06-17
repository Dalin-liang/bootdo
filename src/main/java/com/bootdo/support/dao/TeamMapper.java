package com.bootdo.support.dao;

import com.bootdo.dispatch.center.res.EmergencyTeamRes;
import com.bootdo.support.dto.SupportTeamDTO;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeamMapper {
    int insert(SupportTeam supportTeam);

    List<Map<String,Object>> get(SupportTeamDTO supportTeamDTO);

    int update(SupportTeam supportTeam);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<Map<String,Object>> getDept();

    List<Map<String,Object>> getTeamMate(@Param("id")String id);

    int insertTeam(@Param("team_id")String team_id,@Param("deptperson_id")String deptperson_id);

    int deleteTeam(@Param("team_id")String team_id);

    List<EmergencyTeamRes> getAllTeam();

    SupportTeamDTO getByName(@Param("name")String name);

    List<SupportTeam> getTeamAndPerson();
}
