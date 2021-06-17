package com.bootdo.support.dao;

import com.bootdo.support.dto.SupportGoodsTransDTO;
import com.bootdo.support.entity.SupportGoodsTrans;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsTransMapper {

    int insert(SupportGoodsTrans supportGoodsTrans);

    List<Map<String,Object>> get(SupportGoodsTransDTO supportGoodsTransDTO);

    int update(SupportGoodsTrans supportGoodsTrans);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
}
