package com.bootdo.data.receive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
@Service
public class MsgService {
	@Autowired
	private ReceiveinfoService receiveinfoService;
	private static final Logger logger = LoggerFactory.getLogger(MsgService.class);

	public void saveMsg(String msg,String sourceType,String sourceMenu) {
		logger.warn("MQ插入接报信息:{}",msg);
		if(StringUtils.isNotEmpty(msg)) {
			try {
				JSONArray dataArray=JSONArray.parseArray(msg);

				for(int i=0;i<dataArray.size();i++) {
				ReceiveInfo receiveInfo=new ReceiveInfo();

				receiveInfo = JSON.parseObject(dataArray.getString(i), ReceiveInfo.class);
				receiveInfo.setId(getCode());
				receiveInfo.setSource_type(sourceType);
				receiveInfo.setCreateDate(new Date());
					receiveInfo.setEventaddr(
							receiveInfo.getEventaddr().replace("广东省", "").replace("广州市", "").replace("增城区", ""));
				String lat_lon[]=receiveInfo.getLat_lon().split(",");
//				receiveInfo.setSendSMS(isSendSMS);
				receiveInfo.setSourceMenu(sourceMenu);
				if(lat_lon.length==2) {
					if(receiveInfo.getLat_lon().indexOf("-")>-1) {
						receiveInfo.setLat_lon("23.411491,113.892511");
					}
				receiveinfoService.insert(receiveInfo);
				logger.warn("插入信息接报成功:{}",msg);
				}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public static void main(String[] args) {
		String str="-0.009627789048941284,0.016045665456451";
		System.out.println(str.indexOf("-")>-1);
		
	}


}
