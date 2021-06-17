package com.bootdo.data.cardata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.GenUtils;
import com.bootdo.common.utils.HttpClientUtil;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.dao.ReceiveInfoMapper;
import com.bootdo.support.entity.ReceiveInfo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  根据开始时间结束时间查询汽车设备黑名单数据
 *  @author qiaodk
 */
@Component
public class CarDevBlackListJob implements Job {

    @Autowired
    private ReceiveInfoMapper receiveInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(CarDevBlackListJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("===execute==CarDevBlackListJob==");
        String baseUrl = (String) GenUtils.getConfig().getProperty("big_data_center_baseUrl");
        String url = baseUrl+"cardata/carServiceMonitor/getDevBlackList";
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
                map.put("startDate", beginTime);
                //增加6分钟
                map.put("endDate", Long.parseLong(beginTime)+ 6*60*1000);
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
                            receiveInfo = JSON.parseObject(records.getString(i), ReceiveInfo.class);
                            receiveInfoId = getCode();
                            receiveInfo.setId(receiveInfoId);
                            receiveInfoMapper.insert(receiveInfo);
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
}
