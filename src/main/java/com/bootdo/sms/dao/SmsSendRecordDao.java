package com.bootdo.sms.dao;

import com.bootdo.sms.domain.SmsSendRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-27 09:56:18
 */
@Mapper
public interface SmsSendRecordDao {

	SmsSendRecordDO get(String id);
	
	List<SmsSendRecordDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SmsSendRecordDO smsSendRecord);
	
	int update(SmsSendRecordDO smsSendRecord);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
