package com.bootdo.sms.service.impl;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.sms.config.SmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.sms.dao.SmsSendConfigDao;
import com.bootdo.sms.domain.SmsSendConfigDO;
import com.bootdo.sms.service.SmsSendConfigService;



@Service
public class SmsSendConfigServiceImpl implements SmsSendConfigService {

	@Autowired
	private SmsSendConfigDao smsSendConfigDao;
	
	@Override
	public SmsSendConfigDO get(Long id){
		return smsSendConfigDao.get(id);
	}
	
	@Override
	public List<SmsSendConfigDO> list(Map<String, Object> map){
		return smsSendConfigDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return smsSendConfigDao.count(map);
	}
	
	@Override
	public int save(SmsSendConfigDO smsSendConfig){
		if("3".equals(smsSendConfig.getType())){
			smsSendConfigDao.changeStatusBySourceTypeId(smsSendConfig.getTargetid(),smsSendConfig.getIssend().toString());
		}else if("0".equals(smsSendConfig.getType()) && smsSendConfig.getIssend() == 1){
			smsSendConfigDao.changeStatusBySourceMenuTypeId(smsSendConfig.getTargetid(),smsSendConfig.getIssend().toString());
		}
		return smsSendConfigDao.save(smsSendConfig);
	}
	
	@Override
	public int update(SmsSendConfigDO smsSendConfig){
		if("3".equals(smsSendConfig.getType())){
			smsSendConfigDao.changeStatusBySourceTypeId(smsSendConfig.getTargetid(),smsSendConfig.getIssend().toString());
		}else if("0".equals(smsSendConfig.getType()) && smsSendConfig.getIssend() == 1){
			smsSendConfigDao.changeStatusBySourceMenuTypeId(smsSendConfig.getTargetid(),smsSendConfig.getIssend().toString());
		}
		return smsSendConfigDao.update(smsSendConfig);
	}
	
	@Override
	public int remove(Long id){
		return smsSendConfigDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return smsSendConfigDao.batchRemove(ids);
	}

	@Override
	public int changeStatus(String id, String type,String targetid, String issend) {
		if(StringUtils.isNotEmpty(type) &&"3".equals(type)){
			smsSendConfigDao.changeStatusBySourceTypeId(targetid,issend);
		}else if(StringUtils.isNotEmpty(type) &&"0".equals(type) && "1".equals(issend)){
			smsSendConfigDao.changeStatusBySourceMenuTypeId(targetid,issend);
		}
		return smsSendConfigDao.changeStatus(id,issend);
	}

	@Override
	public int changeStatusByType(String type, String issend) {
		return smsSendConfigDao.changeStatusByType(type,issend);
	}

	@Override
	public int changeStatusByParam(String type, String targetid, String Sourceid, String issend) {
		if(StringUtils.isNotEmpty(type) &&"3".equals(type)){
			smsSendConfigDao.changeStatusBySourceTypeId(targetid,issend);
		}else if(StringUtils.isNotEmpty(type) &&"0".equals(type) && "1".equals(issend)){
			smsSendConfigDao.changeStatusBySourceMenuTypeId(targetid,issend);
		}
		return smsSendConfigDao.changeStatusByParam(type, targetid, Sourceid, issend);
	}

	@Override
	public boolean getIsSendBySourceType(String source_type) {
		String isSend = smsSendConfigDao.getIsSendBySourceType(source_type);
		return isSendSMS(isSend);
	}

	@Override
	public boolean getIsSendBySourceMenuType(String SourceMenuType) {
		String isSend = smsSendConfigDao.getIsSendBySourceMenuType(SourceMenuType);
		return isSendSMS(isSend);
	}

	@Override
	public boolean getIsSendByPlanmainId(String planmainId) {
		String isSend = smsSendConfigDao.getIsSendByPlanmainId(planmainId);
		return isSendSMS(isSend);
	}

	@Override
	public boolean getIsSendByActionprogramMainId(String actionprogramMainId) {
		String isSend = smsSendConfigDao.getIsSendByActionprogramMainId(actionprogramMainId);
		return isSendSMS(isSend);
	}

	private boolean isSendSMS(String isSend){
		if(StringUtils.isNotEmpty(isSend) && "0".equals(isSend)){
			return false;
		}
		return true;
	}

}
