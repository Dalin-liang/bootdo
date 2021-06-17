package com.bootdo.data.water;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.GenUtils;
import com.bootdo.common.utils.HttpClientUtil;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;

@Component
public class WaterService {
	@Autowired
	private ReceiveinfoService receiveinfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterService.class);


	public void getData(String path) {
		System.out.println("===execute==" + path + "==");
		String baseUrl = (String) GenUtils.getConfig().getProperty("big_data_center_baseUrl");
		String url = path;

		Map<String, Object> map = new HashMap<String, Object>();
		String fileUrl = (String) GenUtils.getConfig().getProperty("big_data_center_fileUrl");
		Properties pro = new Properties();
		String proName = ".getAlarmInfo";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			// 获取上次调用的最后记录时间
			pro.load(new FileReader(fileUrl));

			String beginTime = pro.getProperty(this.getClass().getName() + proName);
			if (StringUtils.isNotEmpty(beginTime)) {
				// beginTime=sdf.format(Long.parseLong(beginTime));
				map.put("startTime", beginTime);

				Date endTime = sdf.parse(beginTime);
				// 增加6分钟
				endTime.setTime(endTime.getTime() + 6 * 60 * 1000);
				// map.put("endTime", sdf.format(endTime));
			}
			String result = HttpClientUtil.httpPostRequest(url, map);
			if (StringUtils.isNotEmpty(result)) {
				JSONArray jsonResult = JSONArray.parseArray(result);
				System.out.println(jsonResult.size());
				if (jsonResult != null) {
					// JSONArray records = data.getJSONArray("records");

					ReceiveInfo receiveInfo = new ReceiveInfo();
					String receiveInfoId;
					// 入库
					String lastRecordTime = "";

					for (int i = 0; i < jsonResult.size(); i++) {
						JSONObject recordData = JSONArray.parseObject(jsonResult.get(i).toString());
						if (i == 0) {
							lastRecordTime = recordData.getString("time");
						}

						int ends = new Random().nextInt(100);// 产生两位随机数
						receiveInfoId = getCode();
						receiveInfo.setRepname(recordData.getString("mn"));
						receiveInfo.setEventaddr(recordData.getString("cdAddress"));
						receiveInfo.setId(receiveInfoId);
						receiveInfo.setLat_lon(
								recordData.getString("cdLatitude") + "," + recordData.getString("cdLongitude"));
						receiveInfo.setRepdate(sdf.parse(recordData.getString("time")));
						receiveInfo.setSource_type("12");
						receiveInfo.setCreateDate(new Date());
						String unit = "";
						switch (recordData.getString("ename")) {
						case "溶解氧":
							unit = "mg/L";
							break;
						case "氨氮":
							unit = "mg/L";
							break;
						}
						receiveInfo.setEventdesc("设备告警提示：系统识别到【:" + recordData.getString("cdName") + "】发生了告警，该设备位于"
								+ recordData.getString("cdAddress") + ",告警指标是【监测因子名称,监测值,预警阈值】，告警值是【"
								+ recordData.getString("ename") + "," + recordData.getString("val") + unit + ","
								+ recordData.getString("warningVal") + "】，请及时协调人员处理。");
						// XX监测站】发生了告警，该设备位于XXX（地点），告警指标是【xxx（氨氮、溶解氧等等相关参数）】，告警值是【xxx（加单位）】，请及时协调人员处理。
						if (getDistanceTime(sdf.parse(recordData.getString("time")), new Date()) < 12) {
							LOGGER.info("水质监测推送：{}", receiveInfo.toString());
							receiveinfoService.insert(receiveInfo);
						} else {
							LOGGER.info("水质监测超标超过12小时不推送：{}", receiveInfo.toString());
						}
					}
					// 保存调用的最后记录时间

					if (StringUtils.isNotEmpty(lastRecordTime)) {
						pro.setProperty(this.getClass().getName() + proName, lastRecordTime);
						pro.store(new FileOutputStream(fileUrl), this.getClass().getName() + "上次调用接口数据的最新记录时间");
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getCode() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		StringBuilder str = new StringBuilder();
		str.append(date);
		Random random = new Random();
		int len = 16 - date.length();
		for (int i = 0; i < len; i++) {
			str.append(random.nextInt(10));
		}
		return str.toString();
	}

	public static long getDistanceTime(Date one, Date two) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {

			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hour;
	}

}
