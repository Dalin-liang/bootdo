package com.bootdo.dispatch.center.res.group;

import com.bootdo.dispatch.center.res.GoodDistributionRes;
import com.bootdo.dispatch.center.vo.GoodInfoBaseVO;
import com.bootdo.dispatch.center.vo.GoodInfoVO;
import com.bootdo.dispatch.center.vo.GoodLevel1VO;
import com.bootdo.dispatch.center.vo.GoodLevel2VO;
import com.bootdo.support.dao.GoodsInfoMapper;
import com.bootdo.support.dao.GoodsstorehouseDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/9/2
 */
@Service
public class GoodResGroup {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsstorehouseDao goodsstorehouseDao;

    public List<GoodLevel1VO> bulidGoodTree(){
        List<GoodInfoVO> goodInfoVOList = goodsInfoMapper.getAllGoods();
        if(CollectionUtils.isEmpty(goodInfoVOList)) return Collections.emptyList();
        Map<String,GoodInfoBaseVO> mapping = new HashMap<>(goodInfoVOList.size());
        Map<String,GoodInfoVO> mapping2 = new HashMap<>(goodInfoVOList.size());
        List<GoodLevel1VO> result = new ArrayList<>();
        Map<String,GoodLevel1VO> level1Mapping = new HashMap<>();
        Map<String,GoodLevel2VO> level2Mapping = new HashMap<>();
        GoodLevel1VO level1VO;
        GoodLevel2VO level2VO;
        GoodInfoBaseVO goodInfoBaseVO;
        for (GoodInfoVO goodInfoVO : goodInfoVOList) {
            String level1Id = goodInfoVO.getLevel1Id();
            if(!level1Mapping.containsKey(level1Id)){
                level1VO = new GoodLevel1VO(level1Id,goodInfoVO.getLevel1Name());
                level1VO.setLevel2(new ArrayList<>());
                result.add(level1VO);
                level1Mapping.put(level1Id,level1VO);
            }else{
                level1VO = level1Mapping.get(level1Id);
            }
            String level2Id = goodInfoVO.getLevel2Id();
            if(!level2Mapping.containsKey(level1Id+level2Id)){
                level2VO = new GoodLevel2VO(level2Id,goodInfoVO.getLevel2Name());
                level2VO.setGoogs(new ArrayList<>());
                level1VO.getLevel2().add(level2VO);
                level2Mapping.put(level1Id+level2Id,level2VO);
            }else{
                level2VO = level2Mapping.get(level1Id+level2Id);
            }
            goodInfoBaseVO = new GoodInfoBaseVO(goodInfoVO.getGoodId(),goodInfoVO.getGoodName(),goodInfoVO.getUnit());
            level2VO.getGoogs().add(goodInfoBaseVO);
            mapping.put(goodInfoVO.getGoodId(),goodInfoBaseVO);
            mapping2.put(goodInfoVO.getGoodId(),goodInfoVO);
        }
        List<GoodDistributionRes> distributionResList = goodsInfoMapper.getAllResByGoodIds(mapping.keySet());
        if(CollectionUtils.isEmpty(distributionResList)) return result;
        for (GoodDistributionRes goodDistributionRes : distributionResList) {
            String goodId = goodDistributionRes.getGoodId();
            goodInfoBaseVO = mapping.get(goodId);
            goodDistributionRes.ready();
            goodDistributionRes.setGoodInfo(mapping2.get(goodId));
            goodInfoBaseVO.getRes().add(goodDistributionRes);
        }
        return result;
    }

    public List<Map<String, Object>> bulidGoodHouseTree(){
        List<Map<String, Object>> houseList = goodsstorehouseDao.getGoodsAllHouse();

        if(CollectionUtils.isEmpty(houseList)) return Collections.emptyList();

        //获取所有的一级装备
        for(Map<String, Object> houseMap : houseList){
            String houseId = houseMap.get("houseId") != null ? houseMap.get("houseId").toString() : "";
            //根据houseId获取levelone
            List<Map<String, Object>> houseLevelOneList = goodsstorehouseDao.getGoodsLevelOneByHouseId(houseId);
            if(CollectionUtils.isNotEmpty(houseLevelOneList)){
                //houseMap.put("level1", houseLevelOneList);
                for(Map<String, Object> houseLevelMap : houseLevelOneList){
                    //获取leveltwo
                    String levelOneId = houseLevelMap.get("level1Id") != null ? houseLevelMap.get("level1Id").toString() : "";
                    List<Map<String, Object>> houseLevelTwoList = goodsstorehouseDao.getGoodsLevelTwoByHouseIdAndOneId(houseId, levelOneId);
                    if(CollectionUtils.isNotEmpty(houseLevelTwoList)){
                        for(Map<String, Object> twoLevelMap : houseLevelTwoList){
                            String twoLevelId = twoLevelMap.get("level2Id") != null ? twoLevelMap.get("level2Id").toString() : "";
                            //获取goods
                            List<Map<String, Object>> goodsList = goodsstorehouseDao.getGoodsByTwoLevelIdAndHouseId(houseId, twoLevelId);
                            twoLevelMap.put("goods", goodsList);
                        }
                    }
                    houseLevelMap.put("level2", houseLevelTwoList);

                }
            }
            houseMap.put("level1", houseLevelOneList);
        }
        return houseList;
    }

}
