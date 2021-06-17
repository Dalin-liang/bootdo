package com.bootdo.support.service;

import com.bootdo.support.dto.SupportGoodsPopDTO;
import com.bootdo.support.entity.SupportGoodsPop;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsPopService {

    int insert(SupportGoodsPop supportGoodsPop);

    List<Map<String,Object>> get(SupportGoodsPopDTO supportGoodsPopDTO);

    int update(SupportGoodsPop supportGoodsPop);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
}
