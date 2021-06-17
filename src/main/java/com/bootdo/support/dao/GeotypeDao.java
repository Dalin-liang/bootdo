package com.bootdo.support.dao;

import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportGeoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 地理信息类别表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-13 11:04:28
 */
@Mapper
public interface GeotypeDao {

	int insert(GeotypeDO geotypeDO);

	List<Map<String,Object>> get(GeotypeDO geotypeDO);

	int update(GeotypeDO geotypeDO);

	int delete(@Param("id")String id);
	
	int batchRemove(String[] ids);

	List<SupportGeoInfo> getGeoInfoByTypeId(@Param("typeId")String typeId);
}
