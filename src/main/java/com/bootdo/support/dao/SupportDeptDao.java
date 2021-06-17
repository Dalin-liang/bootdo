package com.bootdo.support.dao;


import com.bootdo.support.entity.SupportDeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 应急部门表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 11:34:54
 */
@Mapper
public interface SupportDeptDao {

	int insert(SupportDeptDO supportDeptDO);

	List<Map<String,Object>> get(SupportDeptDO supportDeptDO);

	int update(SupportDeptDO supportDeptDO);

	int delete(@Param("id")String id);

	int logicalDelete(@Param("id")String id);

	Map<String,Object> getUniqueById(@Param("id")String id);

	int getDeptPersonCountById(String id);

	List<SupportDeptDO> getDeptAndPersons();

	Map<String, Object> getByIdAndContact(@Param("id") String id, @Param("contact") String contact);


    List<Map<String, Object>> getMaintenancePerson(@Param("name") String name);

    List<String> getDeptIdsByUserId(@Param("userId") String userId);

    String getIdByName(@Param("name") String name);
}
