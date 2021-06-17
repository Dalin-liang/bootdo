package com.bootdo.support.dao;

import com.bootdo.dispatch.center.res.GoodDistributionRes;
import com.bootdo.dispatch.center.vo.GoodInfoVO;
import com.bootdo.support.dto.SupportGoodsInfoDTO;
import com.bootdo.support.entity.SupportGoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GoodsInfoMapper {

    int insert(SupportGoodsInfo supportGoodsInfo);

    List<Map<String,Object>> get(SupportGoodsInfoDTO supportGoodsInfoDTO);

    int update(SupportGoodsInfo supportGoodsInfo);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);

    List<GoodInfoVO> getAllGoods();

    List<GoodDistributionRes> getAllResByGoodIds(@Param("goodIds") Collection<String> goodIds);
}
