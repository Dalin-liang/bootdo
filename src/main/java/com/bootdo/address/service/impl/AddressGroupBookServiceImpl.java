package com.bootdo.address.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.address.dao.AddressGroupBookDao;
import com.bootdo.address.domain.AddressGroupBookDO;
import com.bootdo.address.service.AddressGroupBookService;



@Service
public class AddressGroupBookServiceImpl implements AddressGroupBookService {
	@Autowired
	private AddressGroupBookDao addressGroupBookDao;
	
	@Override
	public AddressGroupBookDO get(String id){
		return addressGroupBookDao.get(id);
	}
	
	@Override
	public List<AddressGroupBookDO> list(Map<String, Object> map){
		return addressGroupBookDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return addressGroupBookDao.count(map);
	}
	
	@Override
	public int save(AddressGroupBookDO addressGroupBook){
		return addressGroupBookDao.save(addressGroupBook);
	}
	
	@Override
	public int update(AddressGroupBookDO addressGroupBook){
		return addressGroupBookDao.update(addressGroupBook);
	}
	
	@Override
	public int remove(String id){
		return addressGroupBookDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return addressGroupBookDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getBookByGroupId(String groupId) {
		return addressGroupBookDao.getBookByGroupId(groupId);
	}

}
