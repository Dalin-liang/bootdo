package com.bootdo.support.service;

import com.bootdo.support.dto.SupportStockTakingDTO;
import com.bootdo.support.entity.SupportStockTaking;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StockTakingService {
    int insert(SupportStockTaking supportStockTaking);

    List<Map<String,Object>> get(SupportStockTakingDTO supportStockTakingDTO);

    int update(SupportStockTaking supportStockTaking);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
}
