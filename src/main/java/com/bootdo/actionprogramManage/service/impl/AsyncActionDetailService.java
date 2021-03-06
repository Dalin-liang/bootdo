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

            //????????????
            isSendSMS = blockInfo(earlywarnEventdesc,liabilityMan);


            // ???dispatch_log??????????????????
            String logContent ="";
            String logSummary = "";
            String result =null;

            if(type == 1){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByPlanmainId(planmainId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_respdept", dispatchRespdeptId, actionprogramId,isSendSMS);
 //						?????????????????????[?????????????????????] ??????????????????????????????????????????????????????/?????????
                logContent = "?????????????????????["+dispatchRespdeptName+"] ???"+liabilityMan+"???"+mobile+"???";
                logSummary = "?????????????????????["+liabilityMan+"]???"+mobile+"???";
            }else if(type == 2){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByActionprogramMainId(actionprogramId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_respdept", dispatchRespdeptId, actionprogramId,isSendSMS);
//						???????????????[???????????????????????????] ?????????????????????????????????????????????????????????????????????????????????/?????????
                logContent = "???????????????["+dispatchRespdeptName+"] "+content+" ???"+liabilityMan+"???"+mobile+"???";
                logSummary = "???????????????["+liabilityMan+"]???"+mobile+"???";
            }
            if(result !=null){
                if(SmsSendRecordServiceImpl.SMSRESULT_ONOPEN.equals(result)){
                    logContent +="???????????????????????????????????????";
                    logSummary += "?????????????????????????????????";
                }else{
                    logContent +="??????????????????";
                    logSummary += "????????????";
                }
            }else{
                logContent += "??????????????????";
                logSummary += "????????????";
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

            //????????????
            isSendSMS = blockInfo(earlywarnEventdesc,liabilityName);

            // ???dispatch_log??????????????????
            String logContent ="";
            String logSummary = "";
            String result =null;
            if(actionType == 1){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByPlanmainId(planmainId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_task", dispatchTaskId, actionprogramId,isSendSMS);

//						?????????????????????[?????????????????????] ??????????????????????????????????????????????????????/?????????
                logContent = "?????????????????????["+taskName+"] ???"+liabilityName+"???"+mobile+"???";
                logSummary = "?????????????????????["+taskName+"]???"+mobile+"???";
            }else if(actionType == 2){
                if(isSendSMS){
                    isSendSMS = sendConfigService.getIsSendByActionprogramMainId(actionprogramId);
                }
                result = actionProgramSendSMS(mobile, smsContent, "dispatch_task", dispatchTaskId, actionprogramId,isSendSMS);
//						???????????????[???????????????????????????] ?????????????????????????????????????????????????????????????????????????????????/?????????
                logContent = "???????????????["+taskName+"] "+content+" ???"+liabilityName+"???"+mobile+"???";
                logSummary = "???????????????["+taskName+"]???"+mobile+"???";
            }
            if(result !=null){
                if(SmsSendRecordServiceImpl.SMSRESULT_ONOPEN.equals(result)){//8???9
                    logContent +="???????????????????????????????????????";
                    logSummary += "?????????????????????????????????";
                }else{
                    logContent +="??????????????????";
                    logSummary += "????????????";
                }

            }else{
                logContent += "??????????????????";
                logSummary += "????????????";
            }

            logger.error(logContent);
            System.out.println("===logContent====="+logContent);
            insertDispatchLog(actionprogramId, now,userId, 1,logContent,logSummary);
        }
    }

    //dispatch_log??????????????????
    private void insertDispatchLog(String actionprogramId, Date time, String userId,Integer isDel,String content, String summary) {
        if(StringUtils.isNotEmpty(actionprogramId)){
            DispatchLogDO dispatchLogDO = new DispatchLogDO();
            dispatchLogDO.setId(UUID.randomUUID().toString().replace("-",""));
            //?????????????????????ID
            dispatchLogDO.setActionprogramId(actionprogramId);
            //???????????????????????????ID
            //dispatchLogDO.setLogtypeId(????????????????????????ID);
            //????????????
            dispatchLogDO.setTime(time);
            //????????????????????????
            dispatchLogDO.setShowBigscreen(1);
            //???????????????0??????1????????????
            dispatchLogDO.setIsDel(isDel);
            //??????????????????
            dispatchLogDO.setContent(content);
            //????????????
            dispatchLogDO.setSummary(summary);
            //???????????????
            dispatchLogDO.setCreateBy(userId);
            //??????????????????
            dispatchLogDO.setCreateDate(time);
            dispatchLogDao.save(dispatchLogDO);

            //?????????????????????
            baseEventService.publishLog(dispatchLogDO);
        }
    }

    private String actionProgramSendSMS(String mobile, String content, String associationTableName, String associationTableId, String actionprogramId,boolean isSendSMS) {
        // ????????????(mobile); // ????????????????????????
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // mobile????????????associationTableName??????????????????associationTableId????????????id???actionprogramId?????????id
        map.put("mobile", mobile);
        map.put("associationTableName", associationTableName);
        map.put("associationTableId", associationTableId);
        map.put("actionprogramId", actionprogramId);
        list.add(map);
        return sendRecordService.sendSMS(mobile, content, list,isSendSMS);
    }

    /**
     *
     * @param mobile ???????????????????????????????????????
     * @param content ????????????
     * @param name ?????????
     * @param isSend ????????????
     */
    @Async
    public void asyncSendSMS(String mobile,String content,String name,boolean isSend) {
        if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){
            String logContent = "???async?????????????????????["+name+"???"+mobile+"]????????????"+content;
            logger.warn(logContent);
            sendRecordService.sendSMS(mobile,content,isSend);
        }
    }

    public boolean blockInfo(String eventdesc,String liabilityMan){
        //????????????????????????
        if(StringUtils.isNotEmpty(eventdesc)){
            if(eventdesc.indexOf("????????????")!= -1){
                return false;
            }
        }

        //?????????????????????????????????
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
