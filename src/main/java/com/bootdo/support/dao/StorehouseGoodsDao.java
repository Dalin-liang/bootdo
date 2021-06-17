package com.bootdo.support.dao;

import com.bootdo.support.dto.StorehouseGoodsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 保障库-物资关联表
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-21 08:54:16
 */
@Mapper
public interface StorehouseGoodsDao {

	StorehouseGoodsDO get(String goodsstorehouseId);
	
	List<StorehouseGoodsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StorehouseGoodsDO storehouseGoods);
	
	int update(StorehouseGoodsDO storehouseGoods);
	
	int remove(String goodsstorehouse_id);
	
	int batchRemove(String[] goodsstorehouseIds);
	
	int reduceStock(StorehouseGoodsDO storehouseGoods);
	
	int addStock(StorehouseGoodsDO storehouseGoods);
	
}
