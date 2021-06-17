package com.bootdo.support.dao;

import com.bootdo.dispatch.center.vo.TeamMemberVO;
import com.bootdo.support.dto.SupportDeptPersonDTO;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportDeptPerson;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DeptPersonMapper {
    int insert(SupportDeptPerson supportDeptPerson);

    List<Map<String,Object>> get(SupportDeptPersonDTO supportDeptPersonDTO);

    int update(SupportDeptPerson supportDeptPerson);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
    
    List<Map<String,Object>> getEmergencyDept();

    List<Map<String,Object>> getSysUser();

    List<Map<String,Object>> getUnrelatedSysUser();

    List<Map<String,Object>> getUnrelatedAndOneSysUser(@Param("personId")String personId);

    int getSysUserCountById(String id);

    int updateSysUserPersoinId(@Param("deptpersonId")String deptpersonId, @Param("userId")String userId);

    int clearSysUserPersonId(String id);

    List<Map<String, Object>> getDeptpersonByDeptId(String id);

    SupportDeptDO getDeptByDeptPersonId(String id);

	Map<String, Object> getByIdAndName(@Param("id") String id, @Param("name") String name);

    List<SupportDeptPerson> getDeptPersonByTeam(String teamId);

    List<TeamMemberVO> getDeptPersonByTeamIds(@Param("teamIds") Collection<String> teamIds);

    List<SupportDeptPerson> getDeptPersonByUserName(@Param("userName") String userName);

    Map<String, Object> getDeptPersonInfoByUserId(@Param("userId") Long userId);

    List<Map<String, Object>> getPersonList();
}
