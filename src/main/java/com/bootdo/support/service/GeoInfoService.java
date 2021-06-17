package com.bootdo.support.service;

import com.bootdo.support.dto.SupportGeoInfoDTO;
import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportArroundInfo;
import com.bootdo.support.entity.SupportGeoInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GeoInfoService {
    int insert(SupportGeoInfo supportGeoInfo);

    List<Map<String,Object>> get(SupportGeoInfoDTO supportGeoInfoDTO);

    int update(SupportGeoInfo supportGeoInfo);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<GeotypeDO> getGeoTypeList();

    List<Map<String, Object>> getGeoInfo(@Param("name")String name);

    List<Map<String, Object>> getArroundInfoData();

    List<SupportArroundInfo> getArroundGEOInfoData();

    List<Map<String, Object>> getGeoTypeInfo();

    List<Map<String, Object>> getByGeotypeId(String geotypeId);

    List<Map<String, Object>> getTypeChart();
}
