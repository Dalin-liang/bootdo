package com.bootdo.baiduyuyin;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;

public class TtsUtils {
	
	//设置APPID/AK/SK
    public static final String APP_ID = "1234567JAVA";
    public static final String API_KEY = "4E1BG9lTnlSeIf1NQFlrSq6h";
    public static final String SECRET_KEY = "544ca4657ba8002e3dea3ac2f5fdd241";

    public static String txtToYuyin(String txt,String filePath,String fileName) throws JSONException {
    	// 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(5000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        
        // 调用接口
        TtsResponse res = client.synthesis(txt, "zh", 1, null);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
            	 File file = new File(filePath);
            	  if (!file.exists()) {
            		  file.mkdir();
            	  }
                Util.writeBytesToFileSystem(data,filePath+fileName);
                System.out.println("audio file write to " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
        return filePath;
    }
    
    public static void main(String[] args) throws JSONException {
    	String txt = "设备告警提示：系统识别到【7531909020001】发生了告警，该设备位于浪拨村探测点，警指标是【PM10】，告警值是【54.0】，请及时协调人员处理。";
        String fileName = "D:\\bootdo\\baiduyuyin\\";
    	TtsUtils.txtToYuyin(txt , fileName, "2019112711.mp3");
	}

}
