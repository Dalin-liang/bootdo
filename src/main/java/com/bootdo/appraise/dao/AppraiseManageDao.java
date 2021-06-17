package com.bootdo.appraise.dao;

import com.bootdo.appraise.domain.AppraiseManageDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-04-02 10:15:36
 */
@Mapper
public interface AppraiseManageDao {

	AppraiseManageDO get(String id);
	
	List<Map<String,Object>> list(AppraiseManageDO appraiseManageDO);
	
	int count(Map<String, Object> map);
	
	int save(AppraiseManageDO appraiseManage);
	
	int update(AppraiseManageDO appraiseManage);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
