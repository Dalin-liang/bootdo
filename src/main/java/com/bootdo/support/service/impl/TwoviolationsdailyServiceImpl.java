package com.bootdo.support.service.impl;

import com.bootdo.support.dao.TwoviolationsdailyDao;
import com.bootdo.support.dto.TwoviolationsdailyDO;
import com.bootdo.support.service.TwoviolationsdailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@Service
public class TwoviolationsdailyServiceImpl implements TwoviolationsdailyService {
	@Autowired
	private TwoviolationsdailyDao twoviolationsdailyDao;
	
	@Override
	public TwoviolationsdailyDO get(String id){
		return twoviolationsdailyDao.get(id);
	}
	
	@Override
	public List<TwoviolationsdailyDO> list(Map<String, Object> map){
		return twoviolationsdailyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return twoviolationsdailyDao.count(map);
	}
	
	@Override
	public int save(TwoviolationsdailyDO twoviolationsdaily){
		return twoviolationsdailyDao.save(twoviolationsdaily);
	}
	
	@Override
	public int update(TwoviolationsdailyDO twoviolationsdaily){
		return twoviolationsdailyDao.update(twoviolationsdaily);
	}
	
	@Override
	public int remove(String id){
		return twoviolationsdailyDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return twoviolationsdailyDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getUser(Map<String, Object> map) {
		return twoviolationsdailyDao.getUser(map);
	}
	@Override
	public List<TwoviolationsdailyDO.TwoviolationsdailyExpDO> exportExcel(Map<String, Object> map) {
		List<Map<String,Object>> dailyList = twoviolationsdailyDao.getUser(map);
		List<TwoviolationsdailyDO.TwoviolationsdailyExpDO> twoviolationsdailyExpDOList =
				new ArrayList<>(dailyList.size());
		dailyList.forEach(daily->{
			TwoviolationsdailyDO.TwoviolationsdailyExpDO twoviolationsdailyExpDO =
					new TwoviolationsdailyDO().new TwoviolationsdailyExpDO();
			twoviolationsdailyExpDO.setSchedulingDate(
					Objects.nonNull(daily.get("date"))?daily.get("date").toString():"");
			twoviolationsdailyExpDO.setDeptperson(
					Objects.nonNull(daily.get("name"))?daily.get("name").toString():"");
			twoviolationsdailyExpDO.setTime(
					Objects.nonNull(daily.get("time"))?daily.get("time").toString():"");
			twoviolationsdailyExpDO.setNumber(
					Objects.nonNull(daily.get("number"))?daily.get("number").toString():"");
			twoviolationsdailyExpDO.setAddress(
					Objects.nonNull(daily.get("address"))?daily.get("address").toString():"");
			twoviolationsdailyExpDO.setDirection(
					Objects.nonNull(daily.get("direction"))?daily.get("direction").toString():"");
			twoviolationsdailyExpDO.setGoods(
					Objects.nonNull(daily.get("goods"))?daily.get("goods").toString():"");
			twoviolationsdailyExpDO.setIsNotify(
					Objects.equals("0",daily.get("isNotify").toString())?"未通知":"已通知");
			twoviolationsdailyExpDO.setTrackSituation(
					Objects.nonNull(daily.get("trackSituation"))?daily.get("trackSituation").toString():"");
			twoviolationsdailyExpDO.setRemarks(
					Objects.nonNull(daily.get("remarks"))?daily.get("remarks").toString():"");

			twoviolationsdailyExpDOList.add(twoviolationsdailyExpDO);
		});
		return twoviolationsdailyExpDOList;
	}
	@Override
	public int getUserCount(Map<String, Object> map) {
		return twoviolationsdailyDao.getUserCount(map);
	}

}
