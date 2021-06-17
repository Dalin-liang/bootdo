package com.bootdo.data.healthy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.DateUtils;
import com.bootdo.common.utils.GenUtils;
import com.bootdo.common.utils.HttpClientUtil;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.dao.ReceiveInfoMapper;
import com.bootdo.support.entity.ReceiveInfo;

/**
 * 定时器：根据开始时间和结束时间获取血脂数据
 * @author wux
 *
 */
@Component
public class BloodFatService implements Job {

	@Autowired
	private ReceiveInfoMapper receiveInfoMapper;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("===execute==BloodFatService==");
		String baseUrl = (String) GenUtils.getConfig().getProperty("big_data_center_baseUrl");
		String url = baseUrl+"api/healthy/healthyUser/getBloodFat";
		Map<String, Object> map = new HashMap<String, Object>();
		String fileUrl = (String) GenUtils.getConfig().getProperty("big_data_center_fileUrl");
		Properties pro = new Properties();
		try {
			File file = new File(fileUrl);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			
			//获取上次调用的最后记录时间
			pro.load(new FileReader(fileUrl));
			String beginTime = pro.getProperty(this.getClass().getName());
			if(StringUtils.isNotEmpty(beginTime)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
				map.put("beginTime", sdf.format(new Date(Long.valueOf(beginTime))));
				//增加6分钟
				map.put("endTime", sdf.format(new Date(Long.valueOf(Long.parseLong(beginTime)+ 6*60*1000))));
			}
			System.out.println("==http==param==="+map.toString());
			String result = HttpClientUtil.httpPostRequest(url, map);
			System.out.println("===result===="+result);
			if(StringUtils.isNotEmpty(result)){
				JSONObject jsonResult = JSONObject.parseObject(result);
				JSONObject data = jsonResult.getJSONObject("data");
				if(data !=null){
					JSONArray records = data.getJSONArray("records");
					if(records !=null){
						ReceiveInfo receiveInfo = new ReceiveInfo();
						String receiveInfoId;
						String Lat_lon;
						//入库
						for(int i=0;i<records.size();i++){
							receiveInfo = JSON.parseObject(records.getString(i), ReceiveInfo.class);
							Lat_lon = receiveInfo.getLat_lon();
							if(StringUtils.isNotEmpty(Lat_lon) && StringUtils.isNotEmpty(Lat_lon.trim())){
								if(isAbnormalWarn(receiveInfo.getEventdesc())){
									receiveInfo.setLat_lon(Lat_lon.trim());
									receiveInfoId = getCode();
									receiveInfo.setId(receiveInfoId);
									receiveInfoMapper.insert(receiveInfo);
								}
								
							}
						}
						//保存调用的最后记录时间
						String lastRecordTime =  data.getString("lastRecordTime");
						if(StringUtils.isNotEmpty(lastRecordTime)){
							pro.setProperty(this.getClass().getName(), lastRecordTime);
							pro.store(new FileOutputStream(fileUrl), this.getClass().getName()+"上次调用接口数据的最新记录时间");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	private boolean isAbnormalWarn(String eventdesc) {
		if(StringUtils.isNotEmpty(eventdesc)){
			String [] descs=eventdesc.split("[,\\，]");
	    	String key;
	    	double  value;
	    	String [] data;
	        for(String desc:descs){
	        	data = desc.split("[:\\：]");
	        	key = data[0];
	        	if(StringUtils.isNotEmpty(data[1].toString())){
	        		value = Double.valueOf(data[1].toString());
		        	switch(key) {
			            case "chol"://3-5.2
			                if(value <3 ||value >5.2){
			                	return true;
			                }
			            	break;
			            case "highdensity"://0.7--2.0
			            	if(value <0.7 ||value >2.0){
			            		return true;
			                }
			                break;
			            case "trig"://<1.70
			            	if(value >1.70){
			            		return true;
			                }
			            	break;
			            case "lowdensity"://<3.12
			            	if(value >3.12){
			            		return true;
			                }
			            	break;
			        }
	        	}
	        }
		}
		return false;
	}
}
