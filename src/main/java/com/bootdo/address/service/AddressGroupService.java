package com.bootdo.address.service;

import com.bootdo.address.domain.AddressGroupDO;

import java.util.List;
import java.util.Map;

/**
 * 通讯录组表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-05-22 11:16:47
 */
public interface AddressGroupService {
	
	AddressGroupDO get(String id);
	
	List<AddressGroupDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AddressGroupDO addressGroup);
	
	int update(AddressGroupDO addressGroup);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	int changeStatus(String id, String enabled);

    int saveGroupAndPerson(String group, String personList);

	void updateGroupAndPerson(String group, String personList);

	int removeGroupAndPerson(String id);

	List<AddressGroupDO> getGroupAndPerson();
}
