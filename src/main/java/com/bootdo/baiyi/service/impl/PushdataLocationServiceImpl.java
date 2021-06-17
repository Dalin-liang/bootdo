package com.bootdo.baiyi.service.impl;

import com.bootdo.baiyi.dao.PushdataLocationDao;
import com.bootdo.baiyi.domain.PushdataLocationDO;
import com.bootdo.baiyi.service.PushdataLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PushdataLocationServiceImpl implements PushdataLocationService {
	@Autowired
	private PushdataLocationDao specialPopulationPositionDao;
	
	@Override
	public PushdataLocationDO get(String id){
		return specialPopulationPositionDao.get(id);
	}
	
	@Override
	public List<PushdataLocationDO> list(Map<String, Object> map){
		return specialPopulationPositionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return specialPopulationPositionDao.count(map);
	}
	

	
}
