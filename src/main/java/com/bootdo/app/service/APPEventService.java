package com.bootdo.app.service;

import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;
import com.bootdo.dispatch.center.res.EmergencyEquipment;
import com.bootdo.dispatch.center.res.EmergencyTeamRes;
import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.dispatch.center.res.WatchmanRes;
import com.bootdo.support.entity.SupportDeptPerson;

import java.util.List;
import java.util.Map;

/**
 * APP接口事件信息服务
 * @author Qdk
 * @create 2019/9/26
 */
public interface APPEventService {

    /**
     * 获取推送的应急事件信息和处置安排信息(任务，通讯消息的消息列表)
     * @param personId 登陆用户id
     * @return
     */
    List<Map<String, Object>> getEmergencyEventInfo(String personId);

    /**
     * 应急指挥部 》事件消息列表查看的事件
     * @param personId 登陆用户的id
     * @return
     */
    List<Map<String, Object>> getEmergencyEventMessage(String personId);

    /**
     * 根据事件消息列表的事件的方案id查询该事件的任务
     * @param actionprogramId 事件的方案id
     * @return
     */
    List<Map<String, Object>> getTaskListByEventProgramId(String actionprogramId);


    /**
     * 事件任务的应急人员
     * @param liabilityId
     * @return
     */
    List<Map<String, Object>> getEventEmergencyPerson(String liabilityId, String type);
    /**
     * =====================作废===============
     *本人接收处理的事件的详细情况信息查询接口
     * @param personId
     * @return
     */
    List<Map<String, Object>> getReciveHandleEventInfo(String personId);

    List<Map<String, Object>> getReceiveEventInfo(String personId);

    List<Map<String, Object>> getHandleWorkContent(String personId);

    List<Map<String, Object>> getStartEventInfo(String personId);

    /**
     * 根据登陆用户名获取部门应急人员
     * @param userName
     * @return
     */
    List<SupportDeptPerson> getDeptPersonByUserName(String userName);

    /**
     * 获取所有应急队伍
     * @return
     */
    List<EmergencyTeamRes>  getAllRes();

    /**
     * 获取所有应急装备
     * @return
     */
    List<EmergencyEquipment> getAllEquipRes();


    /**
     * 所有应急专家
     * @return
     */
    List<ExpertRes> getAllExpertRes();

    /**
     * 当天值守人员
     * @return
     */
    List<WatchmanRes> getAllWitchManRes();


    /**
     * 获取执行方案步骤
     * @param actionProgramId 执行方案id
     * @return
     */
    List<Map<String, Object>> getTaskAndRespDept(String actionProgramId);

    /**
     * 获取所有部门应急人员
     * @return
     */
    List<Map<String, Object>> getAllPerson();

    /**
     * 插入反馈表和详情表
     * @param dispatchTaskFeedback
     * @param detailDOList
     * @param actionprogramId
     * @return
     */
    int saveFeedbackAndDetail(DispatchTaskFeedbackDO dispatchTaskFeedback,
                                     List<DispatchTaskFeedbackDetailDO> detailDOList, String actionprogramId);

    /**
     * app巡查上报事件插入到接报信息事件表
     * 并推送综合态势告警信息中
     * @param map
     * @return
     */
    boolean appWarnEventReport(Map<String, Object> map);


}
