package com.bootdo.support.service;

import com.bootdo.support.dto.SupportStoragePlanDTO;
import com.bootdo.support.entity.SupportStoragePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StoragePlanService {

    int insert(SupportStoragePlan supportStoragePlan);

    List<Map<String,Object>> get(SupportStoragePlanDTO supportStoragePlanDTO);

    int update(SupportStoragePlan supportStoragePlan);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<Map<String,Object>> getStoreHouse();

    List<Map<String,Object>> getGoods();
}
