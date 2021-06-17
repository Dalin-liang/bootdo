package com.bootdo.support.service;

import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportGeoInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 地理信息类别表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-13 11:04:28
 */
public interface GeotypeService {

	int insert(GeotypeDO geotypeDO);

	List<Map<String,Object>> get(GeotypeDO geotypeDO);

	int update(GeotypeDO geotypeDO);

	int delete(@Param("id")String id);

	int batchRemove(String[] ids);

	List<SupportGeoInfo> getGeoInfoByTypeId(String typeId);
}
