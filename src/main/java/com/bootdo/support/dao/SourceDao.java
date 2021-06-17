package com.bootdo.support.dao;

import com.bootdo.support.entity.SourceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 信息来源表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-25 08:50:54
 */
@Mapper
public interface SourceDao {

	SourceDO get(String id);
	
	List<SourceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SourceDO source);
	
	int update(SourceDO source);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

    List<SourceDO> getAllSource();

    List<SourceDO> getSourceInMenu();
    
	List<SourceDO> getSourceByType(Map<String, Object> map);

    List<SourceDO> getSourceNotInSendConfig();
}
