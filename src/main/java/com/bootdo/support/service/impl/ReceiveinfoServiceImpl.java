package com.bootdo.support.service.impl;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bootdo.baiduyuyin.TtsUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.actionprogramManage.service.impl.AsyncActionDetailService;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.dispatch.center.vo.BaseEventVO;
import com.bootdo.planManage.dao.PlanMainDao;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.sms.service.SmsSendConfigService;
import com.bootdo.support.dao.ReceiveInfoMapper;
import com.bootdo.support.dto.ReceiveInfoDTO;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.entity.SourceDO;
import com.bootdo.support.service.ReceiveinfoService;
import com.bootdo.support.service.SourceService;

@Service
@Transactional(readOnly = true)
public class ReceiveinfoServiceImpl implements ReceiveinfoService {

	@Autowired
	private ReceiveInfoMapper receiveInfoMapper;
	@Autowired
	private BaseEventService baseEventService;
	@Value("${bootdo.mappingPath}")
	private String mappingUrl;
	@Value("${bootdo.uploadPath}")
	private String uploadPath;
	@Autowired
	private SmsSendConfigService sendConfigService;
	@Autowired
	private AsyncActionDetailService asyncActionDetailService;
    @Autowired
    private DispatchActionprogramMainService dispatchActionprogramMainService;
    @Autowired
    private PlanMainDao planMainDao;
    @Autowired
    private SourceService sourceService;
	private static final Logger logger = LoggerFactory.getLogger(ReceiveinfoServiceImpl.class);

    @Value("classpath:static/data/lotConfig.json")
    private Resource lotConfig;
    
	@Override
	public List<Map<String, Object>> get(ReceiveInfoDTO receiveInfoDTO) {
		return receiveInfoMapper.get(receiveInfoDTO);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(ReceiveInfo receiveinfo) {
		if(StringUtils.isNotEmpty(receiveinfo.getLat_lon())){
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(receiveinfo.getLat_lon());
			receiveinfo.setLat_lon(m.replaceAll("").trim());
		  }
		if(receiveinfo.getAcceptance_type()=="") {
			receiveinfo.setAcceptance_type(null);
		}
		if(receiveinfo.getExamine_type()=="") {
			receiveinfo.setExamine_type(null);
		}

		if(receiveinfo.getRepdate() !=null && StringUtils.isNotEmpty(receiveinfo.getEventdesc())){
			String repDateStr =  new SimpleDateFormat("MM月dd日 HH时mm分").format(receiveinfo.getRepdate());
			StringBuilder eventDesc = new StringBuilder(receiveinfo.getEventdesc());
			int index = eventDesc.indexOf("系统识别到");
			if(index > 0){
				eventDesc.insert(index,repDateStr);
			}else if(eventDesc.indexOf("人脸识别设备识别到") > 0){
				index = eventDesc.indexOf("人脸识别设备识别到");
				eventDesc.insert(index,repDateStr);
			}else{
				eventDesc.insert(0,repDateStr);
			}
			receiveinfo.setEventdesc(eventDesc.toString());
		}

		int res=	receiveInfoMapper.insert(receiveinfo);
		try {
			if(res>0&&sourceType(receiveinfo)) {
			
				boolean flag=startAction2(receiveinfo);//推送到指挥调度
				
				if(!flag) {
					//没推送到指挥调度则推送到综合态势
			      CenterEvent centerEvent = baseEventService.getEventById(receiveinfo.getId());
			     
			    	  //将事件描述转为语音
			    	String path=uploadPath+"baiduAudio/";
					String descAudio= TtsUtils.txtToYuyin(centerEvent.getEventDesc(), path,centerEvent.getEventId()+".mp3");
					centerEvent.setEventdescAudio(mappingUrl+"/baiduAudio/"+centerEvent.getEventId()+".mp3");
					  baseEventService.newUntreatedEvent(centerEvent);	  

				
				}
				  
				  
				//发送短信到设备维护人
				boolean isSendSMS = true;
				if(StringUtils.isNotEmpty(receiveinfo.getSourceMenu())){
					isSendSMS = sendConfigService.getIsSendBySourceMenuType(receiveinfo.getSourceMenu()) && sendConfigService.getIsSendBySourceType(receiveinfo.getSource_type());
				}else{
					isSendSMS = sendConfigService.getIsSendBySourceType(receiveinfo.getSource_type());
				}
				List<Map<String,Object>>personList=receiveInfoMapper.getMaintenancePerson(receiveinfo.getSource_type());
				if(personList!=null&&personList.size()>0) {
					for(int i=0;i<personList.size();i++) {
						asyncActionDetailService.asyncSendSMS(personList.get(i).get("mobile").toString(), receiveinfo.getEventdesc(),personList.get(i).get("name").toString(), isSendSMS);
					}
				}
			  }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			 System.out.println("e.msg() = " + e.getMessage());
			logger.error("insert：{}", e.getMessage());
		}
		return res;
	}

	//暂时屏蔽行为分析和特殊人群的短信发送
//	private boolean isSendSMS(ReceiveInfo receiveinfo){
//		boolean flag=true;
//		if("7".equals(receiveinfo.getSource_type())){
//			flag=false;
//		}else if("9".equals(receiveinfo.getSource_type())){
//			if(receiveinfo.getEventdesc().indexOf("跌倒") !=-1){
//				flag=false;
//			}
//		}
//		return flag;
//	}
	
	private boolean sourceType(ReceiveInfo receiveinfo) {
		boolean flag=false;
		if(StringUtils.isNotEmpty(receiveinfo.getSource_type())){
			switch(receiveinfo.getSource_type()) {
			case "6" :flag=true;break;
			case "7" :flag=true;break;
			case "8" :flag=true;break;
			case "9" :flag=true;break;
			case "10" :flag=true;break;
			case "11" :flag=true;break;
			case "12" :flag=true;break;
			case "14" :flag=true;break;
			case "15" :flag=true;break;
			case "16" :flag=true;break;
			case "17" :flag=true;break;
			case "18" :flag=true;break;
			case "19" :flag=true;break;
			case "20" :flag=true;break;
			case "21" :flag=true;break;
			case "22" :flag=true;break;
			case "23" :flag=true;break;
			case "24" :flag=true;break;
			case "25" :flag=true;break;
			case "26" :flag=true;break;
			case "27" :flag=true;break;
			case "28" :flag=true;break;
			case "29" :flag=true;break;
			case "30" :flag=true;break;
			case "31" :flag=true;break;
			case "32" :flag=true;break;
			case "33" :flag=true;break;
			case "34" :flag=true;break;
			case "35" :flag=true;break;
			case "36" :flag=true;break;
			case "37" :flag=true;break;
			case "38" :flag=true;break;
			case "39" :flag=true;break;
			}
		}if(StringUtils.isNotEmpty(receiveinfo.getExamine_type())) {
			flag=false;
		}
		
		return flag;
	}


	//物联设备启动预案，无自动启动预案
	@Transactional(readOnly = false, propagation = Propagation.NESTED)
	public boolean startAction2(ReceiveInfo receiveinfo) {
		boolean flag=false;
		Map<String,Object>paramMap=new HashMap<>();

		paramMap.put("id", receiveinfo.getSource_type());
		//查询是否自启动类型
		List<SourceDO> sourceList = sourceService.getSourceByType(paramMap);
		if(sourceList!=null&&sourceList.size()>0) {
			if(sourceList.get(0).getIsStart()!=null&&sourceList.get(0).getIsStart()==1) {
				String lotData="";
				try {
					lotData = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("lotConfig.json"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					logger.error("IOUtils.toString：{}", e1.getMessage());
				}
				if(StringUtils.isNotEmpty(lotData)) {
					JSONObject jsonObject = JSONObject.parseObject(lotData);
					JSONObject data =(JSONObject) jsonObject.get(receiveinfo.getSource_type());//获取物联设备默认配置
					JSONObject user =(JSONObject) jsonObject.get("user");//获取配置默认用户
					if(data!=null) {
						if(!receiveinfo.getSource_type().equals("14")||!receiveinfo.getSource_type().equals("9")) {
							receiveinfo.setRepname("超级管理员");
							receiveinfo.setSex(user.getInteger("sex"));
							receiveinfo.setRepphone(user.getString("mobile"));

						}
						Date time=new Date();
						receiveinfo.setDept_id(data.getString("dept"));
						receiveinfo.setAccident_type_id(data.getString("accident"));
						receiveinfo.setEarlywarn_id(data.getString("earlywarn"));
						receiveinfo.setEventlevel(data.getString("earlylevel"));
						receiveinfo.setIspush(5);
						receiveinfo.setIspush_time(time);
						receiveinfo.setExamine_type("5");
						receiveinfo.setExamine_time(time);
						receiveinfo.setPush_handle_time(time);
						receiveinfo.setIs_acceptance("1");
						receiveinfo.setAcceptance_time(time);
						receiveinfo.setAcceptance_type("5");
						receiveinfo.setTempsaveFlag(0);
						if (receiveInfoMapper.update(receiveinfo) <= 0) {
							return false;
						}
						//获取预案表Id
//						List<PlanMainDO>plan=planMainDao.getByLevelId(data.getString("earlylevel"));
//						if(plan!=null&&plan.size()>0) {
//							String planId=plan.get(0).getId();
							try {
								//启动预案
//								String actionId = dispatchActionprogramMainService.productProgram(receiveinfo.getId(),planId);
//								if (actionId == null || actionId.equals("")) {
//									return flag;
//								}

								//推送事件到指挥调度
//								CenterEvent centerEvent = baseEventService.getEventById(receiveinfo.getId());
//								baseEventService.newSituationEvent(centerEvent);
//								baseEventService.startProcessEventByLot(receiveinfo.getId());
								flag=true;
								return  false;
							} catch (Exception e) {
								flag=false;
								// TODO Auto-generated catch block
								e.printStackTrace();
								logger.error("推送：{}", e.getMessage());
							}

//						}
					}
				}
			}
		}
		return false;


	}

	//物联设备启动预案
	@Transactional(readOnly = false, propagation = Propagation.NESTED)
	public boolean startAction(ReceiveInfo receiveinfo) {
		boolean flag=false;
		Map<String,Object>paramMap=new HashMap<>();
		
		paramMap.put("id", receiveinfo.getSource_type());
		//查询是否自启动类型
		List<SourceDO> sourceList = sourceService.getSourceByType(paramMap);
		if(sourceList!=null&&sourceList.size()>0) {
			if(sourceList.get(0).getIsStart()!=null&&sourceList.get(0).getIsStart()==1) {
				String lotData="";
				try {
					lotData = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("lotConfig.json"));
				} catch (IOException e1) { 
					// TODO Auto-generated catch block
					e1.printStackTrace();
					logger.error("IOUtils.toString：{}", e1.getMessage());
				}
				if(StringUtils.isNotEmpty(lotData)) {
				JSONObject jsonObject = JSONObject.parseObject(lotData);
				JSONObject data =(JSONObject) jsonObject.get(receiveinfo.getSource_type());//获取物联设备默认配置
				JSONObject user =(JSONObject) jsonObject.get("user");//获取配置默认用户
				if(data!=null) {
				if(!receiveinfo.getSource_type().equals("14")||!receiveinfo.getSource_type().equals("9")) {
					receiveinfo.setRepname("超级管理员");
					receiveinfo.setSex(user.getInteger("sex"));
					receiveinfo.setRepphone(user.getString("mobile"));
					
				}
				Date time=new Date();
				receiveinfo.setDept_id(data.getString("dept"));
				receiveinfo.setAccident_type_id(data.getString("accident"));
				receiveinfo.setEarlywarn_id(data.getString("earlywarn"));
				receiveinfo.setEventlevel(data.getString("earlylevel"));
				receiveinfo.setIspush(5);
				receiveinfo.setIspush_time(time);
				receiveinfo.setExamine_type("5");
				receiveinfo.setExamine_time(time);
				receiveinfo.setPush_handle_time(time);
				receiveinfo.setIs_acceptance("1");
				receiveinfo.setAcceptance_time(time);
				receiveinfo.setAcceptance_type("5");
				receiveinfo.setTempsaveFlag(0);
				if (receiveInfoMapper.update(receiveinfo) <= 0) {
					return flag;
				}
				//获取预案表Id
				List<PlanMainDO>plan=planMainDao.getByLevelId(data.getString("earlylevel"));
				if(plan!=null&&plan.size()>0) {
					String planId=plan.get(0).getId();
					   try {
						//启动预案
						String actionId = dispatchActionprogramMainService.productProgram(receiveinfo.getId(),planId);
						if (actionId == null || actionId.equals("")) {
							return flag;
						}

						//推送事件到指挥调度
	                    CenterEvent centerEvent = baseEventService.getEventById(receiveinfo.getId());
						baseEventService.newSituationEvent(centerEvent);
	                    baseEventService.startProcessEventByLot(receiveinfo.getId());
	                    flag=true;
	                    return  flag;
					} catch (Exception e) {
						  flag=false;
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("推送：{}", e.getMessage());
					}

				}
				}
			}
			}
		}
		return flag;
		
		
	}
	
	

	@Override
	@Transactional(readOnly = false)
	public int update(ReceiveInfo receiveinfo) {
		if(receiveinfo.getAcceptance_type()=="") {
			receiveinfo.setAcceptance_type(null);
		}
		if(receiveinfo.getExamine_type()=="") {
			receiveinfo.setExamine_type(null);
		}
		return receiveInfoMapper.update(receiveinfo);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return receiveInfoMapper.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int batchRemove(String[] ids) {
		return receiveInfoMapper.batchRemove(ids);
	}

	@Override
	public Map<String, Object> getUniqueById(String id) {
		return receiveInfoMapper.getUniqueById(id);
	}

	@Override
	public List<Map<String, Object>> getSource() {
		return receiveInfoMapper.getSource();
	}

	@Override
	public List<Map<String, Object>> getAccidentType(String id) {
		return receiveInfoMapper.getAccidentType(id);
	}

	@Override
	public List<Map<String, Object>> getCurrentDutyPerson(Map<String, Object> map) {return receiveInfoMapper.getCurrentDutyPerson(map);}

	@Override
	public List<Map<String, Object>> getDeptContactPerson() {
		return receiveInfoMapper.getDeptContactPerson();
	}

	@Override
	public List<Map<String, Object>> getNowDutyPreson(Map<String, Object> map) {
		return receiveInfoMapper.getNowDutyPreson(map);
	}

	@Override
	public List<Map<String, Object>> queryExamine(ReceiveInfoDTO receiveInfoDTO) {
		return receiveInfoMapper.queryExamine(receiveInfoDTO);
	}

	@Override
	@Transactional(readOnly = false)
	public int updateReceiveExamineType(String id) {
		return receiveInfoMapper.updateReceiveExamineType(id);
	}

	@Override
	public List<Map<String, Object>> getEarlyWarnTypeByAccidentId(String id) {
		return receiveInfoMapper.getEarlyWarnTypeByAccidentId(id);
	}

	@Override
	public List<Map<String, Object>> getEarlyWarnLevelByTypeId(String id) {
		return receiveInfoMapper.getEarlyWarnLevelByTypeId(id);
	}

	@Override
	public int updateByActionProId(String actionprogramId) {
		// TODO Auto-generated method stub
		return receiveInfoMapper.updateByActionProId(actionprogramId);
	}

	@Override
	public List<BaseEventVO> getWarnByDevice() {
		// TODO Auto-generated method stub
		return receiveInfoMapper.getWarnByDevice();
	}

	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return receiveInfoMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return receiveInfoMapper.count(map);
	}

	@Override
	public List<Map<String, Object>> getMaintenancePerson(String deviceType) {
		// TODO Auto-generated method stub
		return receiveInfoMapper.getMaintenancePerson(deviceType);
	}

	@Override
	public List<BaseEventVO> getEventByType(String sourceType) {
		// TODO Auto-generated method stub
		return receiveInfoMapper.getEventByType(sourceType);
	}

	@Override
	@Transactional(readOnly = false)
	public int batchEndCase(String[] ids) {
		// TODO Auto-generated method stub
		return receiveInfoMapper.batchEndCase(ids);
	}

	@Override
	public List<CommonFileDO> getAppReportEventImg(String eventId) {
		return receiveInfoMapper.getAppReportEventImg(eventId);
	}

	@Override
	public List<String> getEventIdsByType(String sourceType) {
		return receiveInfoMapper.getEventIdsByType(sourceType);
	}

	@Override
	public List<BaseEventVO> getEventByIds(List<String> dataIdList) {
		return receiveInfoMapper.getEventByIds(dataIdList);
	}

	@Override
	public List<Map<String, Object>> getChartSourceType(String year) {
		return receiveInfoMapper.getChartSourceType(year);
	}

	@Override
	public List<Map<String, Object>> getChartLevel(String year) {
		return receiveInfoMapper.getChartLevel(year);
	}

	@Override
	public List<Map<String, Object>> getChartMonth(String year) {
		return receiveInfoMapper.getChartMonth(year);
	}


	@Override
	public List<Map<String,Object>> taskList(Map<String,Object> map){
		return receiveInfoMapper.taskList(map);
	}

	@Override
	public int countTaskList(Map<String,Object> map){
		return receiveInfoMapper.countTaskList(map);
	}

}
