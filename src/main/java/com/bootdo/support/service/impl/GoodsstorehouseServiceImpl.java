package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GoodsstorehouseDao;
import com.bootdo.support.dto.GoodsstorehouseDO;
import com.bootdo.support.service.GoodsstorehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service
public class GoodsstorehouseServiceImpl implements GoodsstorehouseService {
	@Autowired
	private GoodsstorehouseDao goodsstorehouseDao;
	
	@Override
	public GoodsstorehouseDO get(String id){
		return goodsstorehouseDao.get(id);
	}
	
	@Override
	public List<GoodsstorehouseDO> list(Map<String, Object> map){
		return goodsstorehouseDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return goodsstorehouseDao.count(map);
	}
	
	@Override
	public int save(GoodsstorehouseDO goodsstorehouse){
		return goodsstorehouseDao.save(goodsstorehouse);
	}
	
	@Override
	public int update(GoodsstorehouseDO goodsstorehouse){
		return goodsstorehouseDao.update(goodsstorehouse);
	}
	
	@Override
	public int remove(String id){
		return goodsstorehouseDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return goodsstorehouseDao.batchRemove(ids);
	}

	@Override
	public int logicalDelete(String id) {
		return goodsstorehouseDao.logicalDelete(id);
	}


}
