package com.bootdo.support.service;


import com.bootdo.support.entity.SupportDeptDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 应急部门表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 11:34:54
 */
public interface SupportDeptService {

    int insert(SupportDeptDO supportDeptDO);

    List<Map<String,Object>> get(SupportDeptDO supportDeptDO);

    int update(SupportDeptDO supportDeptDO);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    int getDeptPersonCountById(String id);

    List<SupportDeptDO> getDeptAndPersons();

    List<Map<String,Object>> getMaintenancePerson(String name);

    List<String> getDeptIdsByUserId(String userId);

    String getIdByName(String name);
}
