package com.bootdo.sms.service;

import com.bootdo.sms.domain.SmsSendRecordDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-27 09:56:18
 */
public interface SmsSendRecordService {
	
	SmsSendRecordDO get(String id);
	
	List<SmsSendRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SmsSendRecordDO smsSendRecord);
	
	int update(SmsSendRecordDO smsSendRecord);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	


	/**
	 *
	 * @param mobiles 手机号（群发时，多个手机号，用逗号隔开）
	 * @param content 短信内容
	 * @param isSend 是否发送
	 * @param paramList 传一些值，用于插入短信记录表中，paramList 的 map 中包含（ mobile手机号、associationTableName关联表名称、associationTableId关联表的id、actionprogramId方案表id）
	 * @return
	 */
	String sendSMS(String mobiles,String content,List<Map<String, Object>> paramList,boolean isSend);
	String sendSMS(String mobiles,String content);
	String sendSMS(String mobiles,String content,boolean isSend);
	String sendSMS(String mobiles,String content,List<Map<String, Object>> paramList);
}
