package com.bootdo.app.service.impl;

import com.bootdo.actionprogramManage.dao.DispatchTaskFeedbackDao;
import com.bootdo.actionprogramManage.dao.DispatchTaskFeedbackDetailDao;
import com.bootdo.actionprogramManage.domain.*;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.actionprogramManage.service.DispatchLogService;
import com.bootdo.actionprogramManage.service.DispatchRespdeptService;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.app.dao.APPMapper;
import com.bootdo.app.domain.AppSignReportDO;
import com.bootdo.app.service.APPEventService;
import com.bootdo.common.utils.DateUtils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.dispatch.center.res.EmergencyEquipment;
import com.bootdo.dispatch.center.res.EmergencyTeamRes;
import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.dispatch.center.res.WatchmanRes;
import com.bootdo.dispatch.center.res.group.EmergencyEquipmentGroup;
import com.bootdo.dispatch.center.res.group.EmergencyTeamResGroup;
import com.bootdo.dispatch.center.res.group.WatchmanResGroup;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.dao.ExpertInfoMapper;
import com.bootdo.support.dao.ReceiveInfoMapper;
import com.bootdo.support.dto.SupportDeptPersonDTO;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.support.service.ReceiveinfoService;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class APPEventServiceImpl implements APPEventService {

    @Autowired
    private ReceiveInfoMapper receiveInfoMapper;

    @Autowired
    private DeptPersonMapper deptPersonMapper;

    @Autowired
    private EmergencyTeamResGroup emergencyTeamResGroup;

    @Autowired
    private EmergencyEquipmentGroup emergencyEquipmentGroup;

    @Autowired
    private ExpertInfoMapper expertInfoMapper;

    @Autowired
    private WatchmanResGroup watchmanResGroup;

    @Autowired
    private DispatchActionprogramMainService dispatchActionprogramMainService;

    @Autowired
    private DispatchTaskFeedbackDao dispatchTaskFeedbackDao;

    @Autowired
    private DispatchTaskFeedbackDetailDao detailDao;

    @Autowired
    private DispatchLogService logService;
    @Autowired
    private DispatchTaskService taskService;
    @Autowired
    private DispatchRespdeptService respdeptService;
    @Autowired
    private BaseEventService eventService;
    @Autowired
    private DispatchRespdeptService dispatchRespdeptService;
    @Autowired
    private ReceiveinfoService receiveinfoService;

    @Autowired
    public APPMapper appMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Map<String, Object>> getEmergencyEventInfo(String personId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = receiveInfoMapper.getAPPEmergencyEvent(personId);
            list.addAll(receiveInfoMapper.getAPPEmergencyEvent2(personId));
            if (CollectionUtils.isNotEmpty(list)) {
                Iterator<Map<String, Object>> iterable_map = list.iterator();
                Set<String> taskIdSet = new HashSet<>();
                while (iterable_map.hasNext()) {
                    Map<String, Object> map = iterable_map.next();
                    if (map.get("eventdesc") == null || map.get("repdate") == null || map.get("event_type_id") == null
                            || map.get("warn_level_id") == null || map.get("warn_type_id") == null || map.get("rep_dept") == null) {
                        iterable_map.remove();
                        continue;
                    }
                    String taskId = map.get("taskId") + "";
                    int beforeSize = taskIdSet.size();
                    if (StringUtils.isNotEmpty(taskId)) {
                        taskIdSet.add(taskId);
                    }
                    int afterSize = taskIdSet.size();
                    if (afterSize != (beforeSize + 1)) {
                        iterable_map.remove();
                    }
                }
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    //降序排序
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String obj1 = o1.get("ispush_time") != null ? o1.get("ispush_time").toString() : "";
                        String obj2 = o2.get("ispush_time") != null ? o2.get("ispush_time").toString() : "";
                        String tasktime1 = o1.get("create_date") != null ? o1.get("create_date").toString() : "";
                        String tasktime2 = o2.get("create_date") != null ? o1.get("create_date").toString() : "";
                        if (StringUtils.isNotEmpty(obj1) && StringUtils.isNotEmpty(obj2) &&
                                StringUtils.isNotEmpty(tasktime1) && StringUtils.isNotEmpty(tasktime2)) {
                            Date time1 = null;
                            Date time2 = null;
                            Date taskDate1 = null;
                            Date taskDate2 = null;
                            try {
                                time1 = sdf.parse(obj1);
                                time2 = sdf.parse(obj2);
                                taskDate1 = sdf.parse(tasktime1);
                                taskDate2 = sdf.parse(tasktime2);
                                int compareNum = time2.compareTo(time1);
                                return (compareNum == 0 ? taskDate2.compareTo(taskDate1) : compareNum);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        return 1;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getEmergencyEventMessage(String personId) {
        List<Map<String, Object>> taskList = null;
        List<Map<String, Object>> respList = null;
        try {
            //根据响应任务表获取推送的事件
            taskList = receiveInfoMapper.getEmergencyEventMessage(personId);
            //根据响应部门表获取推送的事件并与上面的列表合并
            respList = receiveInfoMapper.getEmergencyEventMessage2(personId);
            //合并
            respList.addAll(taskList);
            //对合并的结果重新排序并去掉重复事件
            if (CollectionUtils.isNotEmpty(respList)) {
                // for(Map<String, Object> map : respList){
                //去掉事故类型id为空的事件
                // if(map.get("eventTypeId") != null){
                // String eventId = map.get("eventId").toString();
                Set<String> keysSet = new HashSet<String>();
                Iterator<Map<String, Object>> it = respList.iterator();
                while (it.hasNext()) {
                    Map<String, Object> disMap = it.next();
                    if (disMap.get("eventTypeId") == null) {
                        it.remove();
                        continue;
                    }
                    String eventId = (String) disMap.get("eventId");
                    int beforeSize = keysSet.size();
                    keysSet.add(eventId);
                    int afterSize = keysSet.size();
                    if (afterSize != (beforeSize + 1)) {
                        it.remove();
                    }
                }
                //}
                //}
            }
            //排序
            if (CollectionUtils.isNotEmpty(respList)) {
                Collections.sort(respList, new Comparator<Map<String, Object>>() {
                    //降序排序
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String obj1 = o1.get("ispushTime") != null ? o1.get("ispushTime").toString() : "";
                        String obj2 = o2.get("ispushTime") != null ? o2.get("ispushTime").toString() : "";
                        if (StringUtils.isNotEmpty(obj1) && StringUtils.isNotEmpty(obj2)) {
                            Date time1 = null;
                            Date time2 = null;
                            try {
                                time1 = sdf.parse(obj1);
                                time2 = sdf.parse(obj2);
                                return time2.compareTo(time1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        return 1;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respList;
    }

    @Override
    public List<Map<String, Object>> getTaskListByEventProgramId(String actionprogramId) {
        return receiveInfoMapper.getTaskListByEventProgramId(actionprogramId);
    }

    @Override
    public List<Map<String, Object>> getEventEmergencyPerson(String liabilityId, String type) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(type)) {
            if ("5".equals(type)) {//直接从dispatch_respdept表查应急人员
                list = receiveInfoMapper.getgetEventEmergencyPersonByRespDept(liabilityId);
            } else {//根据liability_id 查询应急人员
                list = receiveInfoMapper.getEventEmergencyPerson(liabilityId);
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getReciveHandleEventInfo(String personId) {
        return receiveInfoMapper.getReciveHandleEventInfo(personId);
    }

    @Override
    public List<Map<String, Object>> getReceiveEventInfo(String personId) {
        return receiveInfoMapper.getReceiveEventInfo(personId);
    }

    @Override
    public List<Map<String, Object>> getHandleWorkContent(String personId) {
        return receiveInfoMapper.getHandleWorkContent(personId);
    }

    @Override
    public List<Map<String, Object>> getStartEventInfo(String personId) {
        return receiveInfoMapper.getStartEventInfo(personId);
    }

    @Override
    public List<SupportDeptPerson> getDeptPersonByUserName(String userName) {
        return deptPersonMapper.getDeptPersonByUserName(userName);
    }

    @Override
    public List<EmergencyTeamRes> getAllRes() {
        return emergencyTeamResGroup.getAllRes();
    }

    @Override
    public List<EmergencyEquipment> getAllEquipRes() {
        return emergencyEquipmentGroup.getAllRes();
    }

    @Override
    public List<ExpertRes> getAllExpertRes() {
        return expertInfoMapper.getAllExpert();
    }

    @Override
    public List<WatchmanRes> getAllWitchManRes() {
        return watchmanResGroup.getAllRes();
    }

    @Override
    public List<Map<String, Object>> getTaskAndRespDept(String actionProgramId) {
        return dispatchActionprogramMainService.getTaskAndRespDept(actionProgramId);
    }

    @Override
    public List<Map<String, Object>> getAllPerson() {
        SupportDeptPersonDTO supportDeptPersonDTO = new SupportDeptPersonDTO();
        return deptPersonMapper.get(supportDeptPersonDTO);
    }

    @Override
    //@Transactional(readOnly = false,rollbackFor = Exception.class)
    public int saveFeedbackAndDetail(DispatchTaskFeedbackDO dispatchTaskFeedback, List<DispatchTaskFeedbackDetailDO> detailDOList, String actionprogramId) {
        // TODO Auto-generated method stub
       /* int r=dispatchTaskFeedbackDao.save(dispatchTaskFeedback);
        if(dispatchTaskFeedback!=null) {
            //查询任务信息
            for(DispatchTaskFeedbackDetailDO detail:detailDOList) {
                detail.setFeedbackId(dispatchTaskFeedback.getId());
                if(detailDao.save(detail)==1){
                    saveLog(actionprogramId,dispatchTaskFeedback,detail,"add");
                }

            }
        }
        return 	r;*/
        //判断任务是否已经上报过
        DispatchTaskFeedbackDO taskFeedbackDO = dispatchTaskFeedback;
        int r = 0;
      //  if (taskFeedbackDO == null) {//不存在则保存
            r = dispatchTaskFeedbackDao.save(dispatchTaskFeedback);
            taskFeedbackDO = dispatchTaskFeedback;
        //} else {//更新
        //    r = dispatchTaskFeedbackDao.updateByrelationId(dispatchTaskFeedback);
   ////     }

        if (taskFeedbackDO != null) {
            //查询任务信息
            for (DispatchTaskFeedbackDetailDO detail : detailDOList) {
                detail.setFeedbackId(taskFeedbackDO.getId());
                if (detailDao.save(detail) == 1) {
                    saveLog(actionprogramId, taskFeedbackDO, detail, "add");
                }

            }
        }
        return r;
    }

    @Override
    public boolean appWarnEventReport(Map<String, Object> map) {
        ReceiveInfo receiveInfo = new ReceiveInfo();
        //生成主键ID
        Random random = new Random();
        try {
            int ends = random.nextInt(100);//产生两位随机数
            String dateUUid= DateUtils.format(new Date(), "yyyyMMddHHmmss") + String.format("%02d",ends);//当前时间加两位随机数
            receiveInfo.setId(dateUUid);

            if(map.get("lat") != null&&map.get("lon") != null){
                receiveInfo.setLat_lon(map.get("lat") + "," + map.get("lon"));
            }
            if(map.get("repname") != null){
                receiveInfo.setRepname(map.get("repname").toString());
            }
            if(map.get("eventaddr") != null){
                receiveInfo.setEventaddr(map.get("eventaddr").toString());
            }
            if(map.get("eventdesc") != null){
                receiveInfo.setEventdesc(map.get("eventdesc").toString());
            }
            if(map.get("event_type") != null){
                receiveInfo.setSource_type(map.get("event_type").toString());
            }
            receiveInfo.setRepdate(new Date());
            receiveInfo.setCreateDate(new Date());

            //更新巡查上报表，关联上报事件
            AppSignReportDO appSignReportDO = new AppSignReportDO();
            appSignReportDO.setId(map.get("id").toString());
            appSignReportDO.setWatchReceiveId(dateUUid);
            appMapper.update(appSignReportDO);

            //插入接报信息表,并推送到综合态势（插入时已经推送）
            receiveinfoService.insert(receiveInfo);


           /* if (res > 0) {
                //获取CenterEvent
                CenterEvent centerEvent = eventService.getEventById(receiveInfo.getId());
                //推送到综合态势
                eventService.newUntreatedEvent(centerEvent);
            }*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void saveLog(String actionprogramId, DispatchTaskFeedbackDO feedBack, DispatchTaskFeedbackDetailDO detail, String operation) {
        String name = "";
        String summary = "";
        if (StringUtils.isNotEmpty(feedBack.getFromTable()) && feedBack.getFromTable().equals("dispatch_task")) {
            DispatchTaskDO task = taskService.get(feedBack.getRelationId());
            if (task == null) {
                DispatchRespdeptDO dispatchRespdeptDO = dispatchRespdeptService.get(feedBack.getRelationId());
                name = dispatchRespdeptDO.getDeptName();
            } else {
                name = task.getName();
            }
        } else if (StringUtils.isNotEmpty(feedBack.getFromTable()) && feedBack.getFromTable().equals("dispatch_respdept")) {
            DispatchRespdeptDO dept = respdeptService.get(feedBack.getRelationId());
            name = dept.getLiabilityMan();
        }
        switch (operation) {
            case "add":
                summary = name + "新增任务反馈";
                break;
            case "update":
                summary = name + "修改任务反馈";
                break;
            case "delete":
                summary = name + "删除任务反馈";
                break;
        }
        String userName = ShiroUtils.getUser().getUsername();
        Date now = new Date();
        DispatchLogDO dispatchLog = new DispatchLogDO();
        dispatchLog.setId(UUID.randomUUID().toString().replace("-", ""));
        dispatchLog.setActionprogramId(actionprogramId);
        dispatchLog.setCreateBy(userName);
        dispatchLog.setCreateDate(now);
        dispatchLog.setTime(now);
        dispatchLog.setShowBigscreen(1);
        dispatchLog.setIsDel(1);
        dispatchLog.setContent(detail.getContent());
        dispatchLog.setSummary(summary);
        dispatchLog.setAddress(feedBack.getAddress());
        int i = logService.save(dispatchLog);
        if (i > 0) {
            eventService.appTaskFeedBack(actionprogramId);
        }
    }


}
