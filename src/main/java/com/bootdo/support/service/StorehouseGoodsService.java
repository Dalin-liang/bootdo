package com.bootdo.support.service;

import com.bootdo.support.dto.StorehouseGoodsDO;

import java.util.List;
import java.util.Map;

/**
 * 保障库-物资关联表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-21 08:54:16
 */
public interface StorehouseGoodsService {
	
	StorehouseGoodsDO get(String goodsstorehouseId);
	
	List<StorehouseGoodsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StorehouseGoodsDO storehouseGoods);
	
	int update(StorehouseGoodsDO storehouseGoods);
	
	int remove(String goodsstorehouseId);
	
	int batchRemove(String[] goodsstorehouseIds);
	
	int reduceStock(StorehouseGoodsDO storehouseGoods);
	
	int addStock(StorehouseGoodsDO storehouseGoods);
}
