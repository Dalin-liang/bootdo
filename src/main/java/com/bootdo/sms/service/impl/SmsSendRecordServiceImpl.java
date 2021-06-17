package com.bootdo.sms.service.impl;

import com.bootdo.sms.config.SmsConfig;
import org.apache.axis.client.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.sms.dao.SmsSendRecordDao;
import com.bootdo.sms.domain.SmsSendRecordDO;
import com.bootdo.sms.service.SmsSendRecordService;



@Service
public class SmsSendRecordServiceImpl implements SmsSendRecordService {

	private static final Logger logger = LoggerFactory.getLogger(SmsSendRecordServiceImpl.class);
	public static final String SMSRESULT_ONOPEN = "99999999"; //短信未开启时，设置结果为8个9

	@Autowired
	private SmsConfig smsConfig;
	@Autowired
	private SmsSendRecordDao smsSendRecordDao;
	
	@Override
	public SmsSendRecordDO get(String id){
		return smsSendRecordDao.get(id);
	}
	
	@Override
	public List<SmsSendRecordDO> list(Map<String, Object> map){
		return smsSendRecordDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return smsSendRecordDao.count(map);
	}
	
	@Override
	public int save(SmsSendRecordDO smsSendRecord){
		return smsSendRecordDao.save(smsSendRecord);
	}
	
	@Override
	public int update(SmsSendRecordDO smsSendRecord){
		return smsSendRecordDao.update(smsSendRecord);
	}
	
	@Override
	public int remove(String id){
		return smsSendRecordDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return smsSendRecordDao.batchRemove(ids);
	}

	@Override
	public String sendSMS(String mobiles, String content) {
		return sendSMS(mobiles,content,null,true);
	}

	@Override
	public String sendSMS(String mobiles, String content, boolean isSend) {
		return sendSMS(mobiles,content,null,isSend);
	}

	@Override
	public String sendSMS(String mobiles, String content, List<Map<String, Object>> paramList) {
		return sendSMS(mobiles,content,paramList,true);
	}

	public String sendSMS(String mobiles,String content,List<Map<String, Object>> paramList,boolean isSend) {
		if(StringUtils.isNotEmpty(mobiles) && StringUtils.isNotEmpty(content)){

			String result = null;
			Date now = new Date();
			String currentTime = null;
			boolean open = smsConfig.getOpen()==false?false:true;
			try {
				if(open && isSend){
					String endpoint = smsConfig.getEndpoint();
					// 直接引用远程的wsdl文件
					org.apache.axis.client.Service service = new org.apache.axis.client.Service();
					Call call = (Call) service.createCall();
					call.setTargetEndpointAddress(endpoint);
					call.setOperationName("sentMsg");// wsdl里面描述的接口名称
					call.addParameter("account", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
					call.addParameter("password", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.addParameter("msg", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.addParameter("mobiles", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.addParameter("sendtime", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型

					String uid = smsConfig.getUid();
					String password = smsConfig.getPassword();
					currentTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(now);

					//发送短信
					content = "【正果指挥调度中心】" +content;
					result = (String) call.invoke(new Object[] { uid, password, content, mobiles, currentTime });

				}else{
					result = SMSRESULT_ONOPEN;
				}				// 短信返回值

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("String sendSMS(String mobiles,String content,List<Map<String, Object>> paramList,boolean isSend)：{}", e.getMessage());
			}finally {
				String loggerContent = "【短信接口调用log】时间："+currentTime+"，手机："+mobiles+"，内容："+content+"，短信结果："+(result==null?"失败":(SMSRESULT_ONOPEN.equals(result)?"短信未开启":"成功"));
				logger.error(loggerContent);

				//插入 sms_send_record 短信记录表
				smsRecords(result, now, mobiles, content, paramList);
				return result;
			}
		}
		return null;
	}

	private void smsRecords(String result,Date now,String mobiles,String content,List<Map<String, Object>> paramList){
		String associationTableName = null;
		String associationTableId = null;
		String actionprogramId = null;
		if(mobiles.indexOf(",") < 0){ //单发
			if(paramList !=null && paramList.size()>0){
				Map<String, Object> map = paramList.get(0);
				associationTableName = map.get("associationTableName") !=null?(String)map.get("associationTableName"):"";
				associationTableId =  map.get("associationTableId") !=null?(String)map.get("associationTableId"):"";
				actionprogramId = map.get("actionprogramId") !=null?(String)map.get("actionprogramId"):"";
			}
			smsSendRecord(result,now,mobiles,content,associationTableName,associationTableId,actionprogramId);
		}else{  //群发
			String[] mobileArray = null;
			mobileArray = mobiles.split(",");
			for(int i = 0; i < mobileArray.length; i++){
				if(paramList !=null && paramList.size()>0){
					associationTableName = "";
					associationTableId = "";
					actionprogramId = "";
					if(paramList.size() == 1){
						Map<String, Object> map = paramList.get(0);
						associationTableName = (String) map.get("associationTableName")!=null?(String)map.get("associationTableName"):"";
						associationTableId = (String) map.get("associationTableId")!=null?(String)map.get("associationTableId"):"";
						actionprogramId = (String) map.get("actionprogramId")!=null?(String)map.get("actionprogramId"):"";
					}else{
						for (Map<String, Object> map : paramList) {
							String paramMobile = (String) map.get("mobile");
							if(mobileArray[i].equals(paramMobile)){
								associationTableName = (String) map.get("associationTableName")!=null?(String)map.get("associationTableName"):"";
								associationTableId = (String) map.get("associationTableId")!=null?(String)map.get("associationTableId"):"";
								actionprogramId = (String) map.get("actionprogramId")!=null?(String)map.get("actionprogramId"):"";
								break;
							}
						}
					}
				}
				smsSendRecord(result,now,mobileArray[i],content,associationTableName,associationTableId,actionprogramId);
			}
		}
	}
	
	private int smsSendRecord(String result,Date now,String mobile,String content,String associationTableName,String associationTableId,String actionprogramId) {
		SmsSendRecordDO sendRecordDO = new SmsSendRecordDO();
		sendRecordDO.setId(UUID.randomUUID().toString().replace("-",""));
		sendRecordDO.setSmsId(result);
		sendRecordDO.setTime(now);
		sendRecordDO.setMobile(mobile);
		sendRecordDO.setContent(content);
		if(StringUtils.isNotEmpty(associationTableName)){
			sendRecordDO.setAssociationTableName(associationTableName);
		}
		if(StringUtils.isNotEmpty(associationTableId)){
			sendRecordDO.setAssociationTableId(associationTableId);
		}
		if(StringUtils.isNotEmpty(actionprogramId)){
			sendRecordDO.setActionprogramId(actionprogramId);
		}
		return smsSendRecordDao.save(sendRecordDO);
	}
}
