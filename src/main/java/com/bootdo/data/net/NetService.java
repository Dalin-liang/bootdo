package com.bootdo.data.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.GenUtils;
import com.bootdo.common.utils.HttpClientUtil;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.dao.ReceiveInfoMapper;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
@Component
public class NetService {
	@Autowired
	private ReceiveinfoService receiveinfoService;
	
	public void getData(String path)  {
		System.out.println("===execute=="+path+"==");
		String baseUrl = (String) GenUtils.getConfig().getProperty("big_data_center_baseUrl");
		String url = baseUrl+path;
	
		Map<String, Object> map = new HashMap<String, Object>();
		String fileUrl = (String) GenUtils.getConfig().getProperty("big_data_center_fileUrl");
		Properties pro = new Properties();
		String proName="."+path.substring(9, path.indexOf("/getByBeginTime"));
		try {
			//获取上次调用的最后记录时间
			pro.load(new FileReader(fileUrl));
		
			String beginTime = pro.getProperty(this.getClass().getName()+proName);
			if(StringUtils.isNotEmpty(beginTime)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				beginTime=sdf.format(Long.parseLong(beginTime));
				map.put("startDate",beginTime );
				 
				Date endTime =sdf.parse(beginTime);
				//增加6分钟
				endTime.setTime(endTime.getTime() + 6*60*1000);
				//map.put("endDate", sdf.format(endTime));
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
						//入库
						
						
						for(int i=0;i<records.size();i++){
							JSONObject recordData = JSONArray.parseObject(records.getString(i));

							receiveInfo = JSON.parseObject(records.getString(i), ReceiveInfo.class);
							int ends = new Random().nextInt(100);//产生两位随机数
							receiveInfoId = getCode();
							receiveInfo.setId(receiveInfoId);
							receiveInfo.setLat_lon(recordData.getString("lat")+","+recordData.getString("lon"));
							receiveInfo.setSource_type("7");
							receiveinfoService.insert(receiveInfo);
						}
						//保存调用的最后记录时间
						String lastRecordTime =  data.getString("lastRecordTime");
						if(StringUtils.isNotEmpty(lastRecordTime)){
							pro.setProperty(this.getClass().getName()+proName, lastRecordTime);
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
}
