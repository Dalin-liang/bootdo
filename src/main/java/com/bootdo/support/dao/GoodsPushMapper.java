package com.bootdo.support.dao;

import com.bootdo.support.dto.SupportGoodsPushDTO;
import com.bootdo.support.entity.SupportGoodsPush;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsPushMapper {
    int insert(SupportGoodsPush supportGoodsPush);

    List<Map<String,Object>> get(SupportGoodsPushDTO supportGoodsPushDTO);

    int update(SupportGoodsPush supportGoodsPush);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<Map<String,Object>> getStoreHouse();

    List<Map<String,Object>> getGoods();

	List<Map<String, Object>> getAllStoreHouse();

	List<Map<String, Object>> getAllGoods();
}
