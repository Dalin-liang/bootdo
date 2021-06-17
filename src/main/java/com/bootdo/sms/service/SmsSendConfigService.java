package com.bootdo.sms.service;

import com.bootdo.sms.domain.SmsSendConfigDO;

import java.util.List;
import java.util.Map;

/**
 * 短信发送配置表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:51:01
 */
public interface SmsSendConfigService {
	
	SmsSendConfigDO get(Long id);
	
	List<SmsSendConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SmsSendConfigDO smsSendConfig);
	
	int update(SmsSendConfigDO smsSendConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    int changeStatus(String id,String type,String targetid, String issend);

	int changeStatusByType(String type, String issend);

	boolean getIsSendBySourceMenuType(String SourceMenuType);
	boolean getIsSendByPlanmainId(String planmainId);
	boolean getIsSendByActionprogramMainId(String actionprogramMainId);

    int changeStatusByParam(String type, String targetid, String Sourceid, String issend);

    boolean getIsSendBySourceType(String source_type);
}
