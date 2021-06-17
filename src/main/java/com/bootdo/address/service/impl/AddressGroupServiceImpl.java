package com.bootdo.address.service.impl;

import com.alibaba.fastjson.JSON;
import com.bootdo.address.dao.AddressGroupBookDao;
import com.bootdo.address.domain.AddressGroupBookDO;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.bootdo.address.dao.AddressGroupDao;
import com.bootdo.address.domain.AddressGroupDO;
import com.bootdo.address.service.AddressGroupService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AddressGroupServiceImpl implements AddressGroupService {
	@Autowired
	private AddressGroupDao addressGroupDao;
	@Autowired
	private AddressGroupBookDao addressGroupBookDao;
	
	@Override
	public AddressGroupDO get(String id){
		return addressGroupDao.get(id);
	}
	
	@Override
	public List<AddressGroupDO> list(Map<String, Object> map){
		return addressGroupDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return addressGroupDao.count(map);
	}
	
	@Override
	public int save(AddressGroupDO addressGroup){
		addressGroup.setId(UUID.randomUUID().toString().replace("-",""));
		UserDO userDo= ShiroUtils.getUser();
		addressGroup.setCreateBy(String.valueOf(userDo.getUserId()));
		addressGroup.setCreateDate(new Date());
		return addressGroupDao.save(addressGroup);
	}
	
	@Override
	public int update(AddressGroupDO addressGroup){
		return addressGroupDao.update(addressGroup);
	}
	
	@Override
	public int remove(String id){
		return addressGroupDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return addressGroupDao.batchRemove(ids);
	}

	@Override
	public int changeStatus(String id, String enabled) {
		return addressGroupDao.changeStatus(id,enabled);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveGroupAndPerson(String group, String personList) {
		int row = 0;
		if(StringUtils.isNotEmpty(group)){
			AddressGroupDO addressGroup = JSON.parseObject(group, AddressGroupDO.class);
			addressGroup.setId(UUID.randomUUID().toString().replace("-",""));
			UserDO userDo= ShiroUtils.getUser();
			addressGroup.setCreateBy(String.valueOf(userDo.getUserId()));
			addressGroup.setCreateDate(new Date());
			row = addressGroupDao.save(addressGroup);

			if(row>0 && StringUtils.isNotEmpty(personList)){
				List<Map<String,Object>> persons = JSON.parseObject(personList, List.class);
				for (Map<String, Object> map : persons) {
					AddressGroupBookDO addressGroupBookDO = new AddressGroupBookDO();
					addressGroupBookDO.setId(UUID.randomUUID().toString().replace("-",""));
					addressGroupBookDO.setGroupid(addressGroup.getId());
					addressGroupBookDO.setBookid(map.get("id").toString());
					addressGroupBookDao.save(addressGroupBookDO);
				}
			}
		}
		return row;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateGroupAndPerson(String group, String personList) {
		if(StringUtils.isNotEmpty(group)){
			AddressGroupDO addressGroup = JSON.parseObject(group, AddressGroupDO.class);
			addressGroupDao.update(addressGroup);

			if(StringUtils.isNotEmpty(personList)){
				ArrayList<String> personIds = new ArrayList();
				String personId = null;

				List<Map<String,Object>> persons = JSON.parseObject(personList, List.class);
				for (Map<String, Object> map : persons) {
					personId = map.get("id").toString();
					Map<String,Object> param = new HashMap<>();
					param.put("groupid",addressGroup.getId());
					param.put("bookid",personId);
					List<AddressGroupBookDO> list = addressGroupBookDao.list(param);
					if(list==null || list.size()==0){
						AddressGroupBookDO addressGroupBookDO = new AddressGroupBookDO();
						addressGroupBookDO.setId(UUID.randomUUID().toString().replace("-",""));
						addressGroupBookDO.setGroupid(addressGroup.getId());
						addressGroupBookDO.setBookid(personId);
						addressGroupBookDao.save(addressGroupBookDO);
					}
					personIds.add(personId);
				}

				if(personIds.size() == 0){
					addressGroupBookDao.deleteByGroupId(addressGroup.getId());
				}else{
					String[] personIdsArr = (String[])personIds.toArray(new String[personIds.size()]);
					addressGroupBookDao.deleteByNotInpersonIds(addressGroup.getId(),personIdsArr);
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int removeGroupAndPerson(String id) {
		addressGroupBookDao.deleteByGroupId(id);
		int row = addressGroupDao.remove(id);
		return row;
	}

	@Override
	public List<AddressGroupDO> getGroupAndPerson() {
		return addressGroupDao.getGroupAndPerson();
	}

}
