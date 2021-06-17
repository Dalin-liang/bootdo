package com.bootdo.support.service;

import com.bootdo.support.dto.SupportGoodsInfoDTO;
import com.bootdo.support.entity.SupportGoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService {

    int insert(SupportGoodsInfo supportGoodsInfo);

    List<Map<String,Object>> get(SupportGoodsInfoDTO supportGoodsInfoDTO);

    int update(SupportGoodsInfo supportGoodsInfo);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
}
