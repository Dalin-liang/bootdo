package com.bootdo.data.receive;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.data.config.RabbitMqConfig;
import com.bootdo.data.config.SendSMSConfigType;
import com.bootdo.data.special.listen.SpeciaListener;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
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
public class HealthyListener {

    private static final Logger logger = LoggerFactory.getLogger(SpeciaListener.class);

    //告警模板
    private static String warnTemplate = "设备告警提示：系统识别到,【健康小屋%s】发生了告警，该设备位于%s，告警指标是【%s】，检测值分别是【%s】。";

    @Autowired
    private ReceiveinfoService receiveinfoService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_HEIGHTWEIGHT)
    public void healthyHeightWeightDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_height_weight {}", msg);
                System.out.println("收到消息：" + msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                if (jsonObject.get("weight") != null && jsonObject.get("height") != null) {
                    float weight = Float.valueOf(jsonObject.getString("weight"));
                    float height = Float.valueOf(jsonObject.getString("height"));
                    double BMI = weight / (height * height);//18.5～23.9
                    String userName = jsonObject.getString("userName");
                    String warnTarget = "身体质量指数（BMI）";//告警指标
                    String warnDesc = "身高体重："+userName+"的"+warnTarget;//告警描述
                    boolean isWarn = false;
                    if(BMI < 18.5){
                        isWarn = true;
                        warnDesc += "低于18.5";
                    }else if(BMI > 23.9){
                        isWarn = true;
                        warnDesc += "高于23.9";
                    }
                    if(isWarn){
                        String warnIndex = String.valueOf(BMI);//告警值
                        if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                            //告警描述
                            String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                    warnTarget, warnIndex);
                            receiveInfo.setId(getCode());
                            receiveInfo.setEventdesc(describe);
                            receiveInfo.setEventaddr(jsonObject.getString("address"));
                            receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                            receiveInfo.setSource_type("14");
                            receiveInfo.setRepname(jsonObject.getString("idCard"));
                            receiveInfo.setRepdate(new Date());
                            receiveInfo.setCreateDate(new Date());

                            receiveInfo.setSourceMenu(SendSMSConfigType.healthyHeightWeight);
                            receiveinfoService.insert(receiveInfo);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_BODYCOMPOSITION)
    public void healthyBodyCompositionDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_body_composition {} ", msg);
                System.out.println("收到消息：" + msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                //告警指标
                String warnTarget = "";
                String warnIndex = "";
                String userName = jsonObject.getString("userName");
                String warnDesc = "身体成分："+userName+"的";//告警描述
                if (jsonObject.get("adiposerate") != null) {
                    double adiValue = Double.valueOf(jsonObject.get("adiposerate").toString());
                    boolean isWarn = false;
                    if(adiValue < 10){
                        isWarn = true;
                        warnDesc += "脂肪率低于10,";
                    }else if(adiValue > 25){
                        isWarn = true;
                        warnDesc += "脂肪率高于25,";
                    }
                    if(isWarn){
                        isAbnormalWarn = true;
                        warnTarget += "脂肪率,";
                        warnIndex += String.valueOf(adiValue) + ",";
                    }

                }
                if (jsonObject.get("moisture") != null) {
                    double moiValue = Double.valueOf(jsonObject.get("moisture").toString());
                    boolean isWarn = false;
                    if(moiValue < 30){
                        isWarn = true;
                        warnDesc += "人体水分低于30,";
                    }else if(moiValue > 65){
                        isWarn = true;
                        warnDesc += "人体水分高于65,";
                    }
                    if(isWarn){
                        isAbnormalWarn = true;
                        warnTarget += "人体水分,";
                        warnIndex += String.valueOf(moiValue) + ",";
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());

                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyBodyComposition);
                        receiveinfoService.insert(receiveInfo);
                    }
                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_BLOODPRESSURE)
    public void healthyBloodPressureDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_blood_pressure {}", msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                String warnTarget = "";
                String warnIndex = "";
                String userName = jsonObject.getString("userName");
                String warnDesc = "血压："+userName+"的";//告警描述
                if (jsonObject.get("systolic") != null) {
                    double systolic = Double.valueOf(jsonObject.get("systolic").toString());
                    boolean isWarn = false;
                    if(systolic < 90){
                        isWarn = true;
                        warnDesc += "收缩压低于90,";
                    }else if(systolic > 139){
                        isWarn = true;
                        warnDesc += "收缩压高于139,";
                    }
                    if(isWarn){
                        warnTarget += "收缩压" + ",";
                        warnIndex += String.valueOf(systolic) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (jsonObject.get("diastolic") != null) {
                    double diastolic = Double.valueOf(jsonObject.get("diastolic").toString());
                    boolean isWarn = false;
                    if(diastolic < 60){
                        isWarn = true;
                        warnDesc += "舒张压低于60,";
                    }else if(diastolic > 89){
                        isWarn = true;
                        warnDesc += "舒张压高于89,";
                    }
                    if(isWarn){
                        warnTarget += "舒张压" + ",";
                        warnIndex += String.valueOf(diastolic) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (jsonObject.get("pulse") != null) {
                    double pulse = Double.valueOf(jsonObject.get("pulse").toString());
                    boolean isWarn = false;
                    if(pulse < 60){
                        isWarn = true;
                        warnDesc += "脉搏低于60,";
                    }else if(pulse > 100){
                        isWarn = true;
                        warnDesc += "脉搏高于100,";
                    }
                    if(isWarn){
                        warnTarget += "脉搏" + ",";
                        warnIndex += String.valueOf(pulse) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());

                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyBloodPressure);
                        receiveinfoService.insert(receiveInfo);

                    }
                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_BLOODOXYGEN)
    public void healthyBloodOxygenDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_blood_oxygen {} ", msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                String warnTarget = "";
                String warnIndex = "";
                String userName = jsonObject.getString("userName");
                String warnDesc = "血氧："+userName+"的";//告警描述
                if (jsonObject.get("bloodoxygen") != null) {
                    double bloodoxygen = Double.valueOf(jsonObject.get("bloodoxygen").toString());
                    boolean isWarn = false;
                    if(bloodoxygen < 95){
                        isWarn = true;
                        warnDesc += "血氧低于95,";
                    }else if(bloodoxygen > 97){
                        isWarn = true;
                        warnDesc += "血氧高于97,";
                    }
                    if(isWarn){
                        warnTarget += "血氧" + ",";
                        warnIndex += String.valueOf(bloodoxygen) + ",";
                        isAbnormalWarn = true;
                    }
                }

                if (jsonObject.get("pulse") != null) {
                    double pulse = Double.valueOf(jsonObject.get("pulse").toString());
                    boolean isWarn = false;
                    if(pulse < 60){
                        isWarn = true;
                        warnDesc += "脉搏低于60,";
                    }else if(pulse > 100){
                        isWarn = true;
                        warnDesc += "脉搏高于100,";
                    }
                    if(isWarn){
                        warnTarget += "脉搏" + ",";
                        warnIndex += String.valueOf(pulse) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());

                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyBloodOxygen);
                        receiveinfoService.insert(receiveInfo);
                    }

                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_TEMPERATURE)
    public void healthyTemperatureDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_temperature {} ", msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                String warnTarget = "";
                String warnIndex = "";
                String userName = jsonObject.getString("userName");
                String warnDesc = "体温："+userName+"的";//告警描述
                if (jsonObject.get("temperature") != null) {
                    double temperature = Double.valueOf(jsonObject.get("temperature").toString());
                    boolean isWarn = false;
                    if(temperature < 36.3){
                        isWarn = true;
                        warnDesc += "体温低于36.3,";
                    }else if(temperature > 37.2){
                        isWarn = true;
                        warnDesc += "体温高于37.2,";
                    }
                    if(isWarn){
                        warnTarget += "体温" + ",";
                        warnIndex += String.valueOf(temperature) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());

                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyTemperature);
                        receiveinfoService.insert(receiveInfo);
                    }

                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_ECG)
    public void healthyEcgDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_ecg {}", msg);
                System.out.println("：" + msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                String warnTarget = "";
                String warnIndex = "";
                String userName = jsonObject.getString("userName");
                String warnDesc = "心电图："+userName+"的";//告警描述
                if (jsonObject.get("hr") != null) {
                    double hr = Double.valueOf(jsonObject.get("hr").toString());
                    boolean isWarn = false;
                    if(hr < 360){
                        isWarn = true;
                        warnDesc += "心率值低于60,";
                    }else if(hr > 100){
                        isWarn = true;
                        warnDesc += "心率值高于100,";
                    }
                    if(isWarn){
                        warnTarget += "心率值" + ",";
                        warnIndex += String.valueOf(hr) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());

                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyEcg);
                        receiveinfoService.insert(receiveInfo);
                    }
                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_BLOODSUGAR)
    public void healthyBloodSugarDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_blood_sugar {} ", msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                String warnTarget = "";
                String warnIndex = "";
                double bloodsugar = 0.0d;
                String userName = jsonObject.getString("userName");
                String warnDesc = "血糖："+userName+"的";//告警描述
                if (jsonObject.get("bloodsugar") != null) {
                    bloodsugar = Double.valueOf(jsonObject.get("bloodsugar").toString());
                }
                int type = 0;
                if(jsonObject.get("type")!=null){
                   type  = Integer.valueOf(jsonObject.get("type").toString());
                }
                if(bloodsugar != 0.0d){
                    if(type == 1){
                        boolean isWarn = false;
                        if(bloodsugar < 3.9){
                            isWarn = true;
                            warnDesc += "血糖值(空腹)低于3.9,";
                        }else if(bloodsugar > 6.1){
                            isWarn = true;
                            warnDesc += "血糖值(空腹)高于6.1,";
                        }
                        if(isWarn){
                            warnTarget += "血糖值(空腹)" + ",";
                            warnIndex += String.valueOf(bloodsugar) + ",";
                            isAbnormalWarn = true;
                        }
                    }else if(type == 2){
                        boolean isWarn = false;
                        if(bloodsugar > 7.0){
                            isWarn = true;
                            warnDesc += "血糖值(餐前)高于7.0,";
                        }
                        if(isWarn){
                            warnTarget += "血糖值(餐前)" + ",";
                            warnIndex += String.valueOf(bloodsugar) + ",";
                            isAbnormalWarn = true;
                        }
                    }else if(type == 3){
                        boolean isWarn = false;
                        if(bloodsugar < 6.7){
                            isWarn = true;
                            warnDesc += "血糖值(餐后)低于6.7,";
                        }else if(bloodsugar > 9.4){
                            isWarn = true;
                            warnDesc += "血糖值(餐后)高于9.4,";
                        }
                        if(isWarn){
                            warnTarget += "血糖值(餐后)" + ",";
                            warnIndex += String.valueOf(bloodsugar) + ",";
                            isAbnormalWarn = true;
                        }
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());
                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyBloodSugar);
                        receiveinfoService.insert(receiveInfo);
                    }
                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEALTHY_BLOODFAT)
    public void healthyBloodFatDataListen(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), "utf-8");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                logger.error("收到消息 healthy_blood_fat {} ", msg);
                System.out.println("：" + msg);
                ReceiveInfo receiveInfo = new ReceiveInfo();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //判断是否满足发送条件
                boolean isAbnormalWarn = false;
                String warnTarget = "";
                String warnIndex = "";
                String userName = jsonObject.getString("userName");
                String warnDesc = "血脂："+userName+"的";//告警描述
                if (jsonObject.get("chol") != null) {
                    double chol = Double.valueOf(jsonObject.get("chol").toString());
                    boolean isWarn = false;
                    if(chol < 3){
                        isWarn = true;
                        warnDesc += "总胆固醇低于3,";
                    }else if(chol > 5.2){
                        isWarn = true;
                        warnDesc += "总胆固醇高于5.2,";
                    }
                    if(isWarn){
                        warnTarget += "总胆固醇" + ",";
                        warnIndex += String.valueOf(chol) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if(jsonObject.get("highdensity")!=null){
                    double highdensity  = Integer.valueOf(jsonObject.get("highdensity").toString());
                    boolean isWarn = false;
                    if(highdensity < 0.7){
                        isWarn = true;
                        warnDesc += "高密度脂蛋白低于0.7,";
                    }else if(highdensity > 2.0){
                        isWarn = true;
                        warnDesc += "高密度脂蛋白高于2.0,";
                    }
                    if(isWarn){
                        warnTarget += "高密度脂蛋白" + ",";
                        warnIndex += String.valueOf(highdensity) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if(jsonObject.get("trig") != null){
                    double trig = Double.valueOf(jsonObject.get("trig").toString());
                    boolean isWarn = false;
                    if(trig > 1.70){
                        isWarn = true;
                        warnDesc += "甘油三酯高于1.70,";
                    }
                    if(isWarn){
                        warnTarget += "甘油三酯" + ",";
                        warnIndex += String.valueOf(trig) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if(jsonObject.get("lowdensity") != null){
                    double lowdensity = Double.valueOf(jsonObject.get("lowdensity").toString());
                    boolean isWarn = false;
                    if(lowdensity > 3.12){
                        isWarn = true;
                        warnDesc += "低密度脂蛋白高于3.12,";
                    }
                    if(isWarn){
                        warnTarget += "低密度脂蛋白" + ",";
                        warnIndex += String.valueOf(lowdensity) + ",";
                        isAbnormalWarn = true;
                    }
                }
                if (isAbnormalWarn) {
                    if (jsonObject.get("eventLatLon") != null) {//经纬度不为空
                        warnTarget = warnTarget.substring(0, warnTarget.length() - 1);
                        warnIndex = warnIndex.substring(0, warnIndex.length() - 1);
                        warnDesc = warnDesc.substring(0, warnDesc.length() - 1);
                        String describe = String.format(warnTemplate, warnDesc,jsonObject.getString("address"),
                                warnTarget, warnIndex);
                        receiveInfo.setId(getCode());
                        receiveInfo.setEventdesc(describe);
                        receiveInfo.setEventaddr(jsonObject.getString("address"));
                        receiveInfo.setLat_lon(jsonObject.getString("eventLatLon"));
                        receiveInfo.setSource_type("14");
                        receiveInfo.setRepname(jsonObject.getString("idCard"));
                        receiveInfo.setRepdate(new Date());
                        receiveInfo.setCreateDate(new Date());
                        receiveInfo.setSourceMenu(SendSMSConfigType.healthyBloodFat);
                        receiveinfoService.insert(receiveInfo);
                    }
                }
            } catch (Exception e) {
                logger.error("healthyMQMSG error", e);
                e.printStackTrace();
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
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
}
