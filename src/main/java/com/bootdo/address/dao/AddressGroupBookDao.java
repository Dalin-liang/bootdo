package com.bootdo.address.dao;

import com.bootdo.address.domain.AddressGroupBookDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ADDRESS_GROUP_BOOK通讯录组和通讯录的关联表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-05-22 14:40:35
 */
@Mapper
public interface AddressGroupBookDao {

	AddressGroupBookDO get(String id);
	
	List<AddressGroupBookDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AddressGroupBookDO addressGroupBook);
	
	int update(AddressGroupBookDO addressGroupBook);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	void deleteByGroupId(String groupId);

	void deleteByNotInpersonIds(@Param("groupiId") String groupiId,@Param("personIdsArr")  String[] personIdsArr);

	List<Map<String, Object>> getBookByGroupId(String groupId);
}
