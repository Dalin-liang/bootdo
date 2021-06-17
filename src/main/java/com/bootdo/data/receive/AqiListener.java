package com.bootdo.data.receive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.bootdo.data.config.RabbitMqConfig;
import com.bootdo.data.config.SendSMSConfigType;
import com.bootdo.sms.service.SmsSendConfigService;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.data.config.RabbitMqConfigToAqi;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
import com.rabbitmq.client.Channel;

@Component
public class AqiListener {

	private static final Logger log = LoggerFactory.getLogger(AqiListener.class);

	@Autowired
	private ReceiveinfoService receiveinfoService;
	@Autowired
	SmsSendConfigService smsSendConfigService;

	private StringBuffer eventdescModel ;

//	@RabbitListener(queues =RabbitMqConfigToAqi.QUEUE_aqiData)
//	public void aqiDataListen(Message message, Channel channel) throws IOException {
//		String msg=new String(message.getBody(),"utf-8");
//		System.out.println("收到aqi设备数据"+msg);
//		log.error("收到aqi设备数据"+msg);
//		saveMsg(1,"aqi设备数据",msg);
//		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//	}

//	@RabbitListener(queues =RabbitMqConfigToAqi.QUEUE_aqiDayData)
//	public void aqiDayDataListen(Message message, Channel channel) throws IOException {
//		String msg=new String(message.getBody(),"utf-8");
//		System.out.println("收到aqi日数据"+msg);
//		log.error("收到aqi日数据"+msg);
//		saveMsg(2,"aqi日数据",msg);
// 		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//	}

	@RabbitListener(queues =RabbitMqConfigToAqi.QUEUE_aqiHourData)
	public void aqiHourDataListen(Message message, Channel channel) throws IOException {
		String msg=new String(message.getBody(),"utf-8");
		System.out.println("收到aqi小时数据"+msg);
		log.error("收到aqi小时数据"+msg);

//		boolean isSendSMS = smsSendConfigService.getIsSendBySourceMenuType(SendSMSConfigType.aqiHourData);
		saveMsg(2,"aqi小时数据",msg,SendSMSConfigType.aqiHourData);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	public void saveMsg(int dataType,String TypeDesc, String msg,String sourceMenu) {
		if(StringUtils.isNotEmpty(msg)) {
			try {
				ReceiveInfo receiveInfo = JSON.parseObject(msg, ReceiveInfo.class);
				String Lat_lon = receiveInfo.getLat_lon();
				if(StringUtils.isNotEmpty(Lat_lon) && StringUtils.isNotEmpty(Lat_lon.trim())){
					log.error("==入库前判断是否报警==");
					if(isAbnormalWarn(dataType,TypeDesc,receiveInfo.getEventdesc())){
						receiveInfo.setLat_lon(changeLatLon(Lat_lon.trim()));
						receiveInfo.setId(getCode());
						receiveInfo.setCreateDate(new Date());
						receiveInfo.setSource_type("15");
						receiveInfo.setEventdesc(getlWarnModel(receiveInfo.getRepname(),receiveInfo.getEventaddr()));
//						receiveInfo.setSendSMS(isSendSMS);
						receiveInfo.setSourceMenu(sourceMenu);
						receiveinfoService.insert(receiveInfo);
						log.error("==入库receiveInfo表"+receiveInfo.getId()+",成功");
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//	private boolean isAbnormalWarn(int dataType,String TypeDesc, String dataContent){
//		boolean isAbnormalWarn = false ;
//		eventdescModel = new StringBuffer("");
//		if(dataType == 1 && StringUtils.isNotEmpty(dataContent)){
//			JSONObject data = JSONObject.parseObject(dataContent);
//			double windSpeed = data.getDoubleValue("windSpeed");
//			double co = data.getDoubleValue("co");
//			double so2 = data.getDoubleValue("so2");
//			double no2 = data.getDoubleValue("no2");
//			double pm25 = data.getDoubleValue("pm25");
//			if(windSpeed > 8.0){
//				eventdescModel.append("警指标是【风速】，告警值是【"+windSpeed+"】，");
//				isAbnormalWarn = true;
//			}
//			if(co > 10.0){
//				eventdescModel.append("警指标是【CO】，告警值是【"+co+"】，");
//				isAbnormalWarn = true;
//			}
//			if(so2 > 500.0){
//				eventdescModel.append("警指标是【SO2】，告警值是【"+so2+"】，");
//				isAbnormalWarn = true;
//			}
//			if(no2 > 200.0){
//				eventdescModel.append("警指标是【NO2】，告警值是【"+no2+"】，");
//				isAbnormalWarn = true;
//			}
//			if(pm25 > 75.0){
//				eventdescModel.append("警指标是【PM25】，告警值是【"+pm25+"】，");
//				isAbnormalWarn = true;
//			}
//		}else if(dataType == 2 && StringUtils.isNotEmpty(dataContent)){
//			JSONObject data = JSONObject.parseObject(dataContent);
//			String type = data.getString("type");
//			double dataValue = data.getDoubleValue("data");
//			switch(type){
//				case "CO" :
//					if(dataValue > 10.0){
//						isAbnormalWarn = true;
//					}
//					break;
//				case "SO2" :
//					if(dataValue > 500.0){
//						isAbnormalWarn = true;
//					}
//					break;
//			 	case "NO2" :
//					if(dataValue > 200.0){
//						isAbnormalWarn = true;
//					}
//					break;
//				case "PM25" :
//					if(dataValue > 75.0){
//						isAbnormalWarn = true;
//					}
//					break;
//				default:
//					if(isAbnormalWarn){
//						eventdescModel.append("警指标是【"+type+"】，告警值是【"+dataValue+"】，");
//					}
//			}
//		}
//		return isAbnormalWarn;
//	}
//
	private boolean isAbnormalWarn(int dataType,String TypeDesc, String dataContent){
		boolean isAbnormalWarn = false ;
		eventdescModel = new StringBuffer("");
		if(StringUtils.isNotEmpty(dataContent)){
			JSONObject data = JSONObject.parseObject(dataContent);
			double aqi = data.getDoubleValue("AQI");
			if(aqi >200){
				eventdescModel.append("告警指标是【AQI】，告警值是【"+aqi+"】，");
				JSONArray arrayList = data.getJSONArray("list");
				if(arrayList !=null && !arrayList.isEmpty()){
					double iaqiData = 0;
					String chiefly = "";
					String aqiDataType;
					boolean hasChiefly = false ;
					for(int j=0;j<arrayList.size();j++){
						iaqiData = arrayList.getJSONObject(j).getDoubleValue("iaqi");
						aqiDataType = arrayList.getJSONObject(j).getString("type");
						if(iaqiData > 200 && !"AQI".equals(aqiDataType)){
							chiefly += aqiDataType+"、";
							hasChiefly = true;
						}
					}
					if(hasChiefly){
						chiefly = chiefly.substring(0,chiefly.length()-1);
						eventdescModel.append("首要污染物是【"+chiefly+"】，");
					}
				}
				isAbnormalWarn = true;
			}
		}
		return isAbnormalWarn;
	}

	private String getlWarnModel(String deviceSn,String eventAddr){
		StringBuffer eventdesc = new StringBuffer("设备告警提示：系统识别到【");
		eventdesc.append(deviceSn);
		eventdesc.append("】发生了告警，该设备位于");
		eventdesc.append(eventAddr+"，");
		eventdesc.append(eventdescModel.toString());
		eventdesc.append("请及时协调人员处理。");
		return eventdesc.toString();
	}


	private String getCode(){
		String date =  new SimpleDateFormat("yyyyMMdd").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append(date);
		Random random=new Random();
		int len = 16-date.length();
		for(int i=0;i<len;i++){
			str.append(random.nextInt(10));
		}
		return str.toString();
	}

	private String changeLatLon(String latLon){
		String [] latLons= latLon.split("[,\\，]");
		if(latLons.length == 2){
			double lat = Double.valueOf(latLons[0].toString());
			double lon = Double.valueOf(latLons[1].toString());
			if(lat > lon){
				return lon+","+lat;
			}
		}
		return latLon;
	}

}
