package com.bootdo.support.dao;


import com.bootdo.support.dto.GoodsstorehouseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 保障库(储备库)
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-15 15:08:27
 */
@Mapper
public interface GoodsstorehouseDao {

	GoodsstorehouseDO get(String id);
	
	List<GoodsstorehouseDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(GoodsstorehouseDO goodsstorehouse);
	
	int update(GoodsstorehouseDO goodsstorehouse);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int logicalDelete(String id);

	List<Map<String, Object>> getGoodsAllHouse();

	List<Map<String, Object>> getGoodsLevelOneByHouseId(@Param("houseId")String houseId);

	List<Map<String, Object>> getGoodsLevelTwoByHouseIdAndOneId(@Param("houseId")String houseId,@Param("levelOneId")String levelOneId);

	List<Map<String, Object>> getGoodsByTwoLevelIdAndHouseId(@Param("houseId")String houseId,@Param("levelTwoId")String levelOneId);
}
