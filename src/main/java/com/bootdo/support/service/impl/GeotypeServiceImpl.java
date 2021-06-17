package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GeotypeDao;
import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportGeoInfo;
import com.bootdo.support.service.GeotypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class GeotypeServiceImpl implements GeotypeService {
	@Autowired
	private GeotypeDao geotypeDao;


	@Override
	public int insert(GeotypeDO geotypeDO) {
		return geotypeDao.insert(geotypeDO);
	}

	@Override
	public List<Map<String, Object>> get(GeotypeDO geotypeDO) {
		return geotypeDao.get(geotypeDO);
	}

	@Override
	public int update(GeotypeDO geotypeDO) {
		return geotypeDao.update(geotypeDO);
	}

	@Override
	public int delete(String id) {
		return geotypeDao.delete(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return geotypeDao.batchRemove(ids);
	}

	@Override
	public List<SupportGeoInfo> getGeoInfoByTypeId(String typeId) {
		return geotypeDao.getGeoInfoByTypeId(typeId);
	}
}
