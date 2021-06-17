package com.bootdo.actionprogramManage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.sms.config.SmsConfig;
import com.bootdo.sms.service.SmsSendConfigService;
import com.bootdo.sms.service.impl.SmsSendRecordServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bootdo.actionprogramManage.dao.DispatchActionprogramMainDao;
import com.bootdo.actionprogramManage.dao.DispatchLogDao;
import com.bootdo.actionprogramManage.dao.DispatchPlanMainDao;
import com.bootdo.actionprogramManage.domain.DispatchEarlywarnDO;
import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.actionprogramManage.domain.DispatchPlanMainDO;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.sms.service.SmsSendRecordService;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.dao.ExpertInfoMapper;
import com.bootdo.support.dao.SupportDeptDao;
import com.bootdo.support.dao.TeamMapper;

@Service
public class AsyncActionDetailService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncActionDetailService.class);

    @Autowired
    private DispatchLogDao dispatchLogDao;
    @Autowired
    private BaseEventService baseEventService;
    @Autowired
    private SmsSendRecordService sendRecordService;
    @Autowired
    private SmsSendConfigService sendConfigService;
    @Autowired
    private SmsConfig smsConfig;


    @Async
    public void respDeptActionSMS(String mobile,String content,String actionprogramId,String dispatchRespdeptId,String liabilityMan, Date now, String userId,String dispatchRespdeptName , int type,String smsContent,String planmainId,String earlywarnEventdesc) {
        if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){

            boolean isSendSMS = true;

            //屏蔽短信
            isSendSMS = blockInfo(earlywarnEventdesc,liabilityMan);


            // 在dispatch_log表中插入记录
            String logContent ="";
            String logSummary = "";
            String result =null;

            if(type == 1){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByPlanmainId(planmainId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_respdept", dispatchRespdeptId, actionprogramId,isSendSMS);
 //						启动方案短信：[部门或任务名称] 【负责对象：发短信的电话】（发送失败/成功）
                logContent = "启动方案短信：["+dispatchRespdeptName+"] 【"+liabilityMan+"："+mobile+"】";
                logSummary = "启动方案短信：["+liabilityMan+"]："+mobile+"，";
            }else if(type == 2){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByActionprogramMainId(actionprogramId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_respdept", dispatchRespdeptId, actionprogramId,isSendSMS);
//						指令短信：[指令简介的文本内容] 指令内容的文本内容【负责对象：发短信的电话】（发送失败/成功）
                logContent = "指令短信：["+dispatchRespdeptName+"] "+content+" 【"+liabilityMan+"："+mobile+"】";
                logSummary = "指令短信：["+liabilityMan+"]："+mobile+"，";
            }
            if(result !=null){
                if(SmsSendRecordServiceImpl.SMSRESULT_ONOPEN.equals(result)){
                    logContent +="（发送失败）【短信未开启】";
                    logSummary += "发送失败【短信未开启】";
                }else{
                    logContent +="（发送成功）";
                    logSummary += "发送成功";
                }
            }else{
                logContent += "（发送失败）";
                logSummary += "发送失败";
            }

            logger.error(logContent);
            System.out.println("===logContent====="+logContent);
            insertDispatchLog(actionprogramId, now,userId, 1,logContent,logSummary);
        }
    }

    @Async
    public void taskActionDetailSMS(String liabilityName, Integer type,String actionprogramId, Date now, String userId, String mobile, String content,String dispatchTaskId,String taskName, int actionType,String smsContent,String planmainId,String earlywarnEventdesc){
        if(StringUtils.isNotEmpty(liabilityName) && type !=null){
//            String result = actionProgramSendSMS(mobile, smsContent, "dispatch_task", dispatchTaskId, actionprogramId,isSendSMS);

            boolean isSendSMS = true;

            //屏蔽短信
            isSendSMS = blockInfo(earlywarnEventdesc,liabilityName);

            // 在dispatch_log表中插入记录
            String logContent ="";
            String logSummary = "";
            String result =null;
            if(actionType == 1){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByPlanmainId(planmainId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_task", dispatchTaskId, actionprogramId,isSendSMS);

//						启动方案短信：[部门或任务名称] 【负责对象：发短信的电话】（发送失败/成功）
                logContent = "启动方案短信：["+taskName+"] 【"+liabilityName+"："+mobile+"】";
                logSummary = "启动方案短信：["+taskName+"]："+mobile+"，";
            }else if(actionType == 2){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByActionprogramMainId(actionprogramId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_task", dispatchTaskId, actionprogramId,isSendSMS);
//						指令短信：[指令简介的文本内容] 指令内容的文本内容【负责对象：发短信的电话】（发送失败/成功）
                logContent = "指令短信：["+taskName+"] "+content+" 【"+liabilityName+"："+mobile+"】";
                logSummary = "指令短信：["+taskName+"]："+mobile+"，";
            }
            if(result !=null){
                if(SmsSendRecordServiceImpl.SMSRESULT_ONOPEN.equals(result)){//8个9
                    logContent +="（发送失败）【短信未开启】";
                    logSummary += "发送失败【短信未开启】";
                }else{
                    logContent +="（发送成功）";
                    logSummary += "发送成功";
                }

            }else{
                logContent += "（发送失败）";
                logSummary += "发送失败";
            }

            logger.error(logContent);
            System.out.println("===logContent====="+logContent);
            insertDispatchLog(actionprogramId, now,userId, 1,logContent,logSummary);
        }
    }

    //dispatch_log表中插入记录
    private void insertDispatchLog(String actionprogramId, Date time, String userId,Integer isDel,String content, String summary) {
        if(StringUtils.isNotEmpty(actionprogramId)){
            DispatchLogDO dispatchLogDO = new DispatchLogDO();
            dispatchLogDO.setId(UUID.randomUUID().toString().replace("-",""));
            //应急执行方案表ID
            dispatchLogDO.setActionprogramId(actionprogramId);
            //应急调度日志类别表ID
            //dispatchLogDO.setLogtypeId(用枚举的形式获取ID);
            //日志时间
            dispatchLogDO.setTime(time);
            //是否在上大屏显示
            dispatchLogDO.setShowBigscreen(1);
            //逻辑删除（0删除1不删除）
            dispatchLogDO.setIsDel(isDel);
            //日志具体内容
            dispatchLogDO.setContent(content);
            //日志概要
            dispatchLogDO.setSummary(summary);
            //记录录入人
            dispatchLogDO.setCreateBy(userId);
            //记录录入时间
            dispatchLogDO.setCreateDate(time);
            dispatchLogDao.save(dispatchLogDO);

            //将日志推到前端
            baseEventService.publishLog(dispatchLogDO);
        }
    }

    private String actionProgramSendSMS(String mobile, String content, String associationTableName, String associationTableId, String actionprogramId,boolean isSendSMS) {
        // 发送短信(mobile); // 看要不要弄成群发
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // mobile手机号、associationTableName关联表名称、associationTableId关联表的id、actionprogramId方案表id
        map.put("mobile", mobile);
        map.put("associationTableName", associationTableName);
        map.put("associationTableId", associationTableId);
        map.put("actionprogramId", actionprogramId);
        list.add(map);
        return sendRecordService.sendSMS(mobile, content, list,isSendSMS);
    }

    /**
     *
     * @param mobile 手机号（多个，用逗号隔开）
     * @param content 短信内容
     * @param name 接收人
     * @param isSend 是否发送
     */
    @Async
    public void asyncSendSMS(String mobile,String content,String name,boolean isSend) {
        if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){
            String logContent = "【async短信】：接收人["+name+"："+mobile+"]，内容："+content;
            logger.warn(logContent);
            sendRecordService.sendSMS(mobile,content,isSend);
        }
    }

    public boolean blockInfo(String eventdesc,String liabilityMan){
        //屏蔽设备故障短信
        if(StringUtils.isNotEmpty(eventdesc)){
            if(eventdesc.indexOf("设备故障")!= -1){
                return false;
            }
        }

        //屏蔽配置文件中名单人员
        if(StringUtils.isNotEmpty(liabilityMan)){
            List<String> blockList = smsConfig.getBlockList();
            if(blockList !=null && blockList.size()>0){
                for (String block:blockList) {
                    if(block.equals(liabilityMan)){
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
