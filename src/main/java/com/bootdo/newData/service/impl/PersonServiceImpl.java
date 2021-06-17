package com.bootdo.newData.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.newData.dao.PersonDao;
import com.bootdo.newData.domain.PersonDO;
import com.bootdo.newData.service.PersonService;



@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonDao personDao;
	
	@Override
	public PersonDO get(Integer id){
		return personDao.get(id);
	}
	
	@Override
	public List<PersonDO> list(Map<String, Object> map){
		return personDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return personDao.count(map);
	}
	
	@Override
	public int save(PersonDO person){
		return personDao.save(person);
	}
	
	@Override
	public int update(PersonDO person){
		return personDao.update(person);
	}
	
	@Override
	public int remove(Integer id){
		return personDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return personDao.batchRemove(ids);
	}
	
}
