package com.bootdo.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.support.dao.StorehouseGoodsDao;
import com.bootdo.support.dto.StorehouseGoodsDO;
import com.bootdo.support.service.StorehouseGoodsService;



@Service
public class StorehouseGoodsServiceImpl implements StorehouseGoodsService {
	@Autowired
	private StorehouseGoodsDao storehouseGoodsDao;
	
	@Override
	public StorehouseGoodsDO get(String goodsstorehouseId){
		return storehouseGoodsDao.get(goodsstorehouseId);
	}
	
	@Override
	public List<StorehouseGoodsDO> list(Map<String, Object> map){
		return storehouseGoodsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return storehouseGoodsDao.count(map);
	}
	
	@Override
	public int save(StorehouseGoodsDO storehouseGoods){
		return storehouseGoodsDao.save(storehouseGoods);
	}
	
	@Override
	public int update(StorehouseGoodsDO storehouseGoods){
		return storehouseGoodsDao.update(storehouseGoods);
	}
	
	@Override
	public int remove(String goodsstorehouseId){
		return storehouseGoodsDao.remove(goodsstorehouseId);
	}
	
	@Override
	public int batchRemove(String[] goodsstorehouseIds){
		return storehouseGoodsDao.batchRemove(goodsstorehouseIds);
	}

	@Override
	public int reduceStock(StorehouseGoodsDO storehouseGoods) {
		// TODO Auto-generated method stub
		return storehouseGoodsDao.reduceStock(storehouseGoods);
	}

	@Override
	public int addStock(StorehouseGoodsDO storehouseGoods) {
		// TODO Auto-generated method stub
		return storehouseGoodsDao.addStock(storehouseGoods);
	}
	
}
