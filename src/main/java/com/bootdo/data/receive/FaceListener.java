package com.bootdo.data.receive;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.data.config.RabbitMqConfigToFace;
import com.bootdo.data.config.SendSMSConfigType;
import com.bootdo.sms.service.SmsSendConfigService;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class FaceListener {

    private static final Logger log = LoggerFactory.getLogger(AqiListener.class);

    @Autowired
    private ReceiveinfoService receiveinfoService;
    @Autowired
    SmsSendConfigService smsSendConfigService;

    //告警模板
    private static String warnTemplate = "设备告警提示：人脸识别设备识别到%s%s[匹配度：%s]，该设备位于%s，该人员信息如下【姓名：%s，性别：%s，身份证号码：%s，手机号码：%s】。";



//    @RabbitListener(queues = RabbitMqConfigToFace.QUEUE_FACE)
//    public void faceInfoDataListen(Message message, Channel channel) throws IOException {
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        //参数：队列名称、是否自动ACK、Consumer
//        channel.basicConsume(RabbitMqConfigToFace.QUEUE_FACE, true, consumer);
//        String msg=new String(message.getBody(),"utf-8");
//        System.out.println("收到face实时数据"+msg);
//        log.error("收到face实时数据"+msg);
//
////        saveMsg(msg, SendSMSConfigType.faceInfoData);
//    }

    @RabbitListener(queues = RabbitMqConfigToFace.QUEUE_FACE)
    public void faceInfoDataListen(Message message, Channel channel) throws IOException {
        String msg = null;
        try{
            msg = new String(message.getBody(),"utf-8");
            System.out.println("收到face实时数据"+msg);
            log.error("收到face实时数据："+msg);
            saveMsg(msg, SendSMSConfigType.faceInfoData);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            e.printStackTrace();
            log.error("收到face实时异常信息："+e.getMessage());
            log.error("收到face实时异常数据："+msg);
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);//会死循环，弃用
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    public void saveMsg(String msg,String sourceMenu) {
        if(StringUtils.isNotEmpty(msg)) {
            try {
                ReceiveInfo receiveInfo = JSON.parseObject(msg, ReceiveInfo.class);
                String Lat_lon = receiveInfo.getLat_lon();
                if(StringUtils.isNotEmpty(Lat_lon) && StringUtils.isNotEmpty(Lat_lon.trim())){
                    log.error("==入库前判断是否报警==");
                    if(isAbnormalWarn(receiveInfo.getEventdesc())){
                        receiveInfo.setLat_lon(changeLatLon(Lat_lon.trim()));
                        receiveInfo.setId(getCode());
                        receiveInfo.setCreateDate(new Date());
                        receiveInfo.setSource_type("34");
                        receiveInfo.setEventdesc(getlWarnModel(receiveInfo));
                        receiveInfo.setSourceMenu(sourceMenu);
                        receiveinfoService.insert(receiveInfo);
                        log.error("==入库receiveInfo表"+receiveInfo.getId()+",成功");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAbnormalWarn(String dataContent){
        boolean isAbnormalWarn = false ;
        if(StringUtils.isNotEmpty(dataContent)){
            JSONObject data = JSONObject.parseObject(dataContent);
            String type = data.getString("type");
            int match = data.getInteger("match") !=null?data.getInteger("match"):0;
            if("0".equals(type) && match >= 80){
                isAbnormalWarn = true ;
            }
        }
        return isAbnormalWarn;
    }

    private String getlWarnModel(ReceiveInfo receiveInfo){
        JSONObject data = JSONObject.parseObject(receiveInfo.getEventdesc());
        String description = data.getString("description");
        String personName = data.getString("name");
        String match = data.getString("match")!=null?(data.getString("match")+"%"):"";
        String address = receiveInfo.getEventaddr();
        String sex = data.getInteger("gender")==0?"男":(data.getInteger("gender")==1?"女":"");
        String idCard = data.getString("zjhm");
        String phone = data.getString("phone");
        String describe = String.format(warnTemplate, description,personName,match,address,personName,sex,idCard,phone);
        return describe;
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
