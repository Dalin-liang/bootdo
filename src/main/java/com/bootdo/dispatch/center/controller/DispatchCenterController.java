package com.bootdo.dispatch.center.controller;

import com.alibaba.fastjson.JSONArray;
import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.actionprogramManage.service.DispatchLogService;
import com.bootdo.actionprogramManage.service.DispatchTaskFeedbackService;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.archive.domain.ArchiveActionprogramMainDO;
import com.bootdo.archive.domain.ArchivePlanmainDO;
import com.bootdo.archive.domain.ArchiveRespdeptDO;
import com.bootdo.archive.service.ArchiveActionprogramMainService;
import com.bootdo.archive.service.ArchivePlanmainService;
import com.bootdo.archive.service.ArchiveRespdeptService;
import com.bootdo.archive.service.ArchiveTaskService;
import com.bootdo.common.service.CommonFileService;
import com.bootdo.common.utils.CommonUtils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.res.EmergencyVehiclesRes;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.dispatch.center.vo.BaseEventVO;
import com.bootdo.dispatch.center.vo.FeedBackVO;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.planManage.service.PlanMainService;
import com.bootdo.sms.service.SmsSendRecordService;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.dao.EquipmentMapper;
import com.bootdo.support.entity.SupportArroundInfo;
import com.bootdo.support.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
@RestController
@RequestMapping("/dispatch/center")
public class DispatchCenterController {

    private static final Logger logger = LoggerFactory.getLogger(DispatchCenterController.class);
	@Value("${bootdo.mappingPath}")
	private String mappingUrl;
	
    @Autowired
    private BaseEventService baseEventService;

    @Autowired
    private PlanMainService planMainService;

    @Autowired
    private DispatchLogService dispatchLogService;

    @Autowired
    private DispatchActionprogramMainService dispatchActionprogramMainService;

    @Autowired
    private ExpertInfoService expertInfoService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private DispatchTaskService dispatchTaskService;
    
    @Autowired
    private SmsSendRecordService sendRecordService;
    
    @Autowired
    private ArchiveActionprogramMainService archiveActionprogramMainService;

    @Autowired
    private GeoInfoService geoInfoService;

    @Autowired
    private DispatchTaskFeedbackService dispatchTaskFeedbackService;
    @Autowired
    private KnowledgeInfoService knowledgeInfoService ;

    @Autowired
    private DeptPersonMapper deptPersonDao;
    
    @Autowired
    private EquipmentMapper equipmentMapper;
            ///deptPersonMap = deptPersonDao.getByIdAndName(liabilityId,liabilityMan);
    
    @Autowired
	private KnowledgeDangertypeService knowledgeDangertypeService;
    
    @Autowired
    private ReceiveinfoService receiveinfoService;
    
    @Autowired
    private CommonFileService commonFileService;

    @Autowired
    private ArchivePlanmainService archivePlanmainService;
    @Autowired
    private ArchiveRespdeptService archiveRespdeptService;
    @Autowired
    private ArchiveTaskService archiveTaskService;
    @Autowired
    private SupportDeptService supportDeptService;
    @Autowired
    private DeptPersonMapper deptPersonMapper;
    @Autowired
    private TeamService teamService;

    @ApiOperation(value="预案查询", notes="")
    @PostMapping("/queryPlan")
    public ResponseEntity queryPlan(@RequestParam(required = false) String levelId,
                                    @RequestParam(required = false) String accidentName,
                                    @RequestParam(required = false) String warnTypeName,
                                    @RequestParam(required = false) String warnLevelName){
        List<PlanMainDO> data;
        try {
            data = planMainService.getPlanMainByParam(levelId,accidentName,warnTypeName,warnLevelName);
            return ResponseEntity.ok(R.ok().put("data",data));
        } catch (Exception e) {
            logger.error("call planMainService.getPlanMainByParam error",e);
            data = Collections.emptyList();
            return ResponseEntity.ok(R.error().put("data",data));
        }
    }


    @ApiOperation(value="将事件标记为启动", notes="")
    @PostMapping("/startProcessing")
    public ResponseEntity startProcessing(@RequestParam String eventId){
        try {
            boolean value = baseEventService.startProcessEvent(eventId);
            return ResponseEntity.ok(R.ok().put("success",value));
        } catch (Exception e) {
            logger.error("call startProcessing error",e);
            return ResponseEntity.ok(R.error().put("success",false));
        }
    }


    @ApiOperation(value="启动预案", notes="")
    @PostMapping("/startAction")
    public ResponseEntity startAction(@RequestParam String eventId,
                                      @RequestParam String planId){
        String actionId;
        try {
            actionId = dispatchActionprogramMainService.productProgram(eventId,planId);
        } catch (Exception e) {
            logger.error("productProgram error",e);
            return ResponseEntity.ok(R.error());
        }
        try {
            //ws推送
            baseEventService.startActionProg(eventId,actionId);
        } catch (Exception e) {
            logger.error("pub error",e);
        }
        return ResponseEntity.ok(R.ok());
    }

    @ApiOperation(value="事件重新定位", notes="")
    @PostMapping("/eventReLocation")
    public ResponseEntity eventReLocation(@RequestParam String eventId,
                                          @RequestParam BigDecimal lat,
                                          @RequestParam BigDecimal lon,
                                          @RequestParam String address){
        int result;
        try {
            result = dispatchActionprogramMainService.updateReceiveinfoAndEarlywarn(eventId,lat.toString()+","+lon.toString(),address);
        } catch (Exception e) {
            logger.error("updateReceiveinfoAndEarlywarn error",e);
            return ResponseEntity.ok(R.error());
        }
        try {
            if(result==1){
                //ws推送
                baseEventService.eventReLocation(eventId,lat,lon,address);
            }
        } catch (Exception e) {
            logger.error("pub error",e);
        }
        return ResponseEntity.ok(R.ok());
    }

    @ApiOperation(value="获取执行方案步骤")
    @RequestMapping(value = "/getTaskStep/{actionProgramId}",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity getTaskStep(@PathVariable String actionProgramId){
        List<Map<String, Object>> result;
        try {
//            result = dispatchActionprogramMainService.getTaskAndRespDept(actionProgramId);
            result = dispatchActionprogramMainService.getTaskAndRespDeptByParams(actionProgramId,"","","");
        } catch (Exception e) {
            logger.error("getTaskAndRespDept error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }


    @ApiOperation(value="根据参数获取执行方案步骤")
    @PostMapping(value = "/getTaskStep")
    public ResponseEntity getTaskStepByParam(@ApiParam(name = "方案Id")@RequestParam(required = true) String actionProgramId,
                                             @ApiParam(name = "任务名称")@RequestParam(required = false) String name,
                                             @ApiParam(name = "任务内容")@RequestParam(required = false) String content,
                                             @ApiParam(name = "任务负责人")@RequestParam(required = false) String liabilityMan){
        List<Map<String, Object>> result;
        try {
            result = dispatchActionprogramMainService.getTaskAndRespDeptByParams(actionProgramId,name,content,liabilityMan);
        } catch (Exception e) {
            logger.error("getTaskStepByParam error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }


    @ApiOperation(value="添加方案步骤")
    @RequestMapping("/taskStep/create")
    public ResponseEntity createTaskStep(String entityJsonStr){
    	List<Map<String, Object>> result;
    	try {
    		dispatchActionprogramMainService.addTaskORRespDept(entityJsonStr);
    	} catch (Exception e) {
    		logger.error("startAction error",e);
    		return ResponseEntity.ok(R.error());
    	}
    	return ResponseEntity.ok(R.ok());
    }


    @ApiOperation(value="更新方案步骤")
    @RequestMapping("/taskStep/update")
    public ResponseEntity updateTaskStep(String entityJsonStr){
    	List<Map<String, Object>> result;
    	try {
    		dispatchActionprogramMainService.updateTaskORRespDept(entityJsonStr);
    	} catch (Exception e) {
    		logger.error("startAction error",e);
    		return ResponseEntity.ok(R.error());
    	}
    	return ResponseEntity.ok(R.ok());

    }


    @ApiOperation(value="删除方案步骤")
    @RequestMapping("/taskStep/delete")
    public ResponseEntity delTaskStep(String taskId,String flag){
    	List<Map<String, Object>> result;
    	try {
    		dispatchActionprogramMainService.logicDeleteTaskORRespDept(taskId,flag);
    	} catch (Exception e) {
    		logger.error("startAction error",e);
    		return ResponseEntity.ok(R.error());
    	}
    	return ResponseEntity.ok(R.ok());
    }


    @ApiOperation(value="结案")
    @PostMapping("/event/end")
    public ResponseEntity eventEnd(String eventId ,String actionProgramId){
        try {
            dispatchActionprogramMainService.closeCase(actionProgramId);
            baseEventService.endEvent(eventId);
        } catch (Exception e) {
            logger.error("startAction error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok());
    }
    
    @ApiOperation(value="结束告警")
    @PostMapping("/event/endWarn")
    public ResponseEntity endWarn(String eventId){
        try {
            baseEventService.endWarnEvent(eventId);
        } catch (Exception e) {
            logger.error("endWarn error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok());
    }

    @ApiOperation(value="获取日志数据")
    @RequestMapping("/getTaskLog/{actionProgramId}")
    public ResponseEntity getDispatchLog(@PathVariable String actionProgramId){
        List<DispatchLogDO> result;
        try {
            Map<String,Object> param = new HashMap<>(1);
            param.put("actionId",actionProgramId);
            result = dispatchLogService.getDispatchLogByActionprogramId(param);
        } catch (Exception e) {
            logger.error("startAction error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    @ApiOperation("查询专家数据")
    @RequestMapping("/expert/query")
    public ResponseEntity queryExpert(@RequestParam(required = false)String name,
                                      @RequestParam(required = false)String typeOf,
                                      @RequestParam(required = false)String warnId,
                                      @RequestParam(required = false)String warnName){
        List<Map<String,Object>> result;
        try {
            Map<String,Object> param = new HashMap<>(4);
            if(name!=null)  param.put("name",name);
            if(typeOf!=null)  param.put("typeof",typeOf);
            if(warnId!=null)  param.put("warn_id",warnId);
            if(warnName!=null)  param.put("warn_name",warnName);
            result = expertInfoService.getExpertInfoByParams(param);
        } catch (Exception e) {
            logger.error("startAction error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

//    @ApiOperation("知识库查询")
//    @RequestMapping("/knowledge/query")
//    public ResponseEntity queryKnowledge(@ApiParam(name = "id")@RequestParam(required = false)String id,
//                                         @ApiParam(name = "预警类别ID") @RequestParam(required = false)String warnId,
//                                         @ApiParam(name = "分类") @RequestParam(required = false)Integer type ,
//                                         @ApiParam(name = "标题") @RequestParam(required = false)String title ,
//                                         @ApiParam(name = "关键字") @RequestParam(required = false)String keywork ,
//                                         @ApiParam(name = "来源") @RequestParam(required = false)String source  ){
//        List<KnowledgeDO> result;
//        try {
//            Map<String,Object> param = new HashMap<>(6);
//            if(id!=null)  param.put("id",id);
//            if(type!=null)  param.put("type",type);
//            if(title!=null)  param.put("title",title);
//            if(keywork!=null)  param.put("keywork",keywork);
//            if(source!=null)  param.put("source",source);
//            if(warnId!=null)  param.put("warn_id",warnId);
//            result = knowledgeService.getListByParams(param);
//        } catch (Exception e) {
//            logger.error("startAction error",e);
//            return ResponseEntity.ok(R.error());
//        }
//        return ResponseEntity.ok(R.ok().put("data",result));
//    }
    
    @ApiOperation("知识库查询")
    @RequestMapping("/knowledgeInfo/query")
    public ResponseEntity queryKnowledgeInfo(@ApiParam(name = "名称") @RequestParam(required = false)String name,
                                         @ApiParam(name = "俗称") @RequestParam(required = false)String otherName ,
                                         @ApiParam(name = "危险性类别") @RequestParam(required = false)String dangerType  ){
        List<Map<String, Object>> result = null;
        try {
        	result = knowledgeInfoService.getknowledgeByParam("", name, otherName, dangerType);
        } catch (Exception e) {
            logger.error("startAction error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }
    
    @ApiOperation("危险性类别查询")
    @RequestMapping("/knowledgeDangerType/query")
    public ResponseEntity queryknowledgeDangerType(){
        List<Map<String, Object>> result = null;
        try {
        	result = knowledgeDangertypeService.getDangerType();
        } catch (Exception e) {
            logger.error("startAction error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    @ApiOperation(value="任务联系人查询")
    @RequestMapping("/getTaskContact")
    public ResponseEntity queryTaskContact(@ApiParam(name = "任务或响应部门 ")@RequestParam(required = false)String flag ,
                                         @ApiParam(name = "负责人Id") @RequestParam(required = false)String liabilityId ,
                                         @ApiParam(name = "接收任务的对象类型") @RequestParam(required = false)Integer type ,
                                         @ApiParam(name = "部门名称") @RequestParam(required = false)String deptName, 
                                         @ApiParam(name = "方案Id") @RequestParam(required = false)String actionprogramId
                                           ){
        Map<String,Object> result;
        try {
            Map<String,Object> param = new HashMap<>(4);
            if(flag!=null)  param.put("flag",flag);
            if(liabilityId!=null)  param.put("liabilityId",liabilityId);
            if(type!=null)  param.put("type",type);
            if(deptName!=null)  param.put("deptName",deptName);
            if(actionprogramId!=null)  param.put("actionprogramId",actionprogramId);
       
            result = dispatchTaskService.getPersonContact(param);
        } catch (Exception e) {
            logger.error("getTaskContact error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }
    
    @ApiOperation(value="短信发送")
    @PostMapping("/smsSend")
    public ResponseEntity SmsSend(@ApiParam(name = "手机号 ")@RequestParam(required = false)String mobiles,//（群发时，多个手机号，用逗号隔开）
                                         @ApiParam(name = "短信内容") @RequestParam(required = false)String content ,
                                         @ApiParam(name = "短信记录Json字符串") @RequestParam(required = false)String jsonStr  //传一些值，用于插入短信记录表中(mobile,associationTableName,associationTableId,actionprogramId)
                                           ){
        String result;
        try {
            List<Map<String,Object>> paramList = new ArrayList<>();
            if(jsonStr!=null) {
            	paramList=(List<Map<String,Object>>) JSONArray.parse(jsonStr);
            }
            result = sendRecordService.sendSMS(mobiles, content, paramList);
        } catch (Exception e) {
            logger.error("SmsSend error",e);
            return ResponseEntity.ok(R.error());
        }
        if(result == null){
            logger.error("短信发送失败：返回的result为null");
            return ResponseEntity.ok(R.error("短信发送失败"));
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    @ApiOperation(value="案例库查询")
    @RequestMapping("/getArchiveCase")
    public ResponseEntity getArchiveCase(@ApiParam(name = "预警类别ID ")@RequestParam(required = false)String warnTypeId ,
                                         @ApiParam(name = "事故类型") @RequestParam(required = false)String accidentName ,
                                         @ApiParam(name = "预警类别") @RequestParam(required = false)String  warnTypeName ,
                                         @ApiParam(name = "预警级别") @RequestParam(required = false)String warnLevelName
                                           ){
        List<Map<String,Object>> result;
        try {
            result = archiveActionprogramMainService.getProgramMainByParam(warnTypeId, accidentName, warnTypeName, warnLevelName);
        } catch (Exception e) {
            logger.error("getArchiveCase error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    @ApiOperation(value="案例详情信息")
    @RequestMapping("/getArchiveCaseDetails")
    public ResponseEntity getArchiveCaseDetails(@ApiParam(name = "案例ID ")@RequestParam(required = true)String archiveActionprogramId){
        Map<String,Object> result =new HashMap<String, Object>();
        try {
            ArchiveActionprogramMainDO archiveActionprogramMainDO = archiveActionprogramMainService.get(archiveActionprogramId);
            result.put("archiveActionprogram", archiveActionprogramMainDO);

            ArchivePlanmainDO archivePlanMain = archivePlanmainService.getByActionprogramId(archiveActionprogramId);
            if(archivePlanMain != null){
                result.put("archivePlanMain", archivePlanMain);
                String planMainId = archivePlanMain.getId();
                //责任单位
                List<ArchiveRespdeptDO> respDeptList = archiveRespdeptService.getByPlanMainId(planMainId);
                result.put("archiveRespDept", respDeptList);

                //任务
                List<Map<String,Object>> taskList = archiveTaskService.getByActionprogramIdAndPlanMainId(archiveActionprogramId,planMainId);
                if(taskList !=null && taskList.size()>0){
                    String liabilityId ="";
                    Integer type;
                    for(Map<String,Object> planTaskMap : taskList) {
                        type = (Integer) planTaskMap.get("type");
                        liabilityId = (String) planTaskMap.get("liability_id");
                        if(type !=null && StringUtils.isNotEmpty(liabilityId)){
                            Map<String,Object> deptPersonMap;
                            Map<String,Object> teamMap;
                            Map<String,Object> supportDeptMap ;
                            Map<String,Object> expertInfoMap ;
                            switch (type) { // 接收任务的对象类型(个人、应急队伍、部门)
                                case 1: // 个人
                                    deptPersonMap = deptPersonMapper.getUniqueById(liabilityId);
                                    if(deptPersonMap !=null){
                                        planTaskMap.put("liabilityObject", (String) deptPersonMap.get("name"));
                                    }
                                    break;
                                case 2: // 应急队伍
                                    teamMap =  teamService.getUniqueById(liabilityId);
                                    if(teamMap !=null){
                                        planTaskMap.put("liabilityObject", (String)teamMap.get("name"));
                                    }
                                    break;
                                case 3: // 部门
                                    supportDeptMap =  supportDeptService.getUniqueById(liabilityId);
                                    if(supportDeptMap !=null){
                                        planTaskMap.put("liabilityObject", (String)supportDeptMap.get("name"));
                                    }
                                    break;
                                case 4: // 专家
                                    expertInfoMap =  expertInfoService.getUniqueById(liabilityId);
                                    if(expertInfoMap !=null){
                                        planTaskMap.put("liabilityObject", (String)expertInfoMap.get("name"));
                                    }
                                    break;
                            }
                        }
                    }
                }
                result.put("archiveTask", taskList);
            }

        } catch (Exception e) {
            logger.error("getArchiveCaseDetails error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    @ApiOperation(value="周边信息的相似案例查询")
    @RequestMapping("/getSimilarCase")
    public ResponseEntity getSimilarCase(@ApiParam(name = "预警类别ID ")@RequestParam(required = false)String eventId){
        List<Map<String,Object>> result;
        try {
            result = archiveActionprogramMainService.getProgramMainDetailByWarnTypeId(eventId);
        } catch (Exception e) {
            logger.error("getSimilarCase error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }
    
    @ApiOperation(value="案件综合查询")
    @RequestMapping("/getCaseByLost")
    public ResponseEntity getCaseByLost(@ApiParam(name = "事件类型 ")@RequestParam(required = false)String accidentName ,
            @ApiParam(name = "预警类别") @RequestParam(required = false)String warnTypeName ,
            @ApiParam(name = "预警级别") @RequestParam(required = false)String  warnLevelName ,
            @ApiParam(name = "事件编号") @RequestParam(required = false)String code,
            @ApiParam(name = "预案名称") @RequestParam(required = false)String  planName ,
            @ApiParam(name = "事件发生时间起") @RequestParam(required = false)String  beginTime ,
            @ApiParam(name = "事件发生时间止") @RequestParam(required = false)String  endTime,
            @ApiParam(name = "预警时间起") @RequestParam(required = false)String receiveBtime,
            @ApiParam(name = "预警时间止") @RequestParam(required = false)String receiveEtime){
        List<Map<String,Object>> result;
        try {
            result = archiveActionprogramMainService.getByLostParam(accidentName, warnTypeName, warnLevelName, code, planName, beginTime, endTime,receiveBtime,receiveEtime);
        } catch (Exception e) {
            logger.error("getCaseByLost error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    //----------全镇自定义重点信息
    @ApiOperation(value="全镇重点保护对象")
    @GetMapping("/geoInfo")
    public ResponseEntity geoInfo(){
        List<SupportArroundInfo> result;
        try {
            result = geoInfoService.getArroundGEOInfoData();
        } catch (Exception e) {
            logger.error("geoInfo error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }


    //-------------任务反馈接口

    @ApiOperation(value="任务反馈")
    @PostMapping("/feedBack")
    public ResponseEntity addFeedBack(@RequestBody FeedBackVO feedBackVO){
        if(CommonUtils.isEmpty(feedBackVO.getDetails()))
            return ResponseEntity.ok(R.error("反馈明细为空"));

        try {
            DispatchTaskFeedbackDO feedBack = new DispatchTaskFeedbackDO();
            feedBack.setId(CommonUtils.getUUID());
            feedBack.setFromTable("dispatch_task");
            feedBack.setFeedbackDate(new Date());
            feedBack.setAddress(feedBackVO.getAddress());
            feedBack.setPersonType(feedBackVO.getPersonType());
            feedBack.setDeptpesonName(feedBackVO.getDeptpesonName());
            feedBack.setDeptpersonId(feedBackVO.getDeptpersonId());
            feedBack.setRelationId(feedBackVO.getTaskId());
            feedBack.setSourceType(1);
            for (DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetailDO : feedBackVO.getDetails()) {
                dispatchTaskFeedbackDetailDO.setId(CommonUtils.getUUID());
            }
            dispatchTaskFeedbackService.saveFeedbackAndDetail(feedBack,feedBackVO.getDetails(),feedBackVO.getActionProgId());
        } catch (Exception e) {
            logger.error("addFeedBack error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok());
    }


    @ApiOperation(value="任务反馈查询")
    @GetMapping("/feedBack/{taskId}")
    public ResponseEntity getFeedBack(@PathVariable String taskId){
        List<Map<String,Object>> data;
        try {
            Map<String,Object> param = new HashMap<>(1);
            param.put("relationId",taskId);
            data =  dispatchTaskFeedbackService.getfeedbackAndDetail(param);
        } catch (Exception e) {
            logger.error("addFeedBack error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",data));
    }


    @ApiOperation(value="查询部门用户")
    @PostMapping("/deptPerson")
    public ResponseEntity getDeptPerson(@RequestParam(required = false) String userId,@RequestParam(required = false) String username){
        Object data;
        try {
            data = deptPersonDao.getByIdAndName(userId,username);
        } catch (Exception e) {
            logger.error("getByIdAndName error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",data));
    }
    
    @ApiOperation(value="查询未处理与已结案数量")
    @GetMapping("/getCountByCase")
    public ResponseEntity getCountByCase(){
        Map<String,Object> res = new HashMap<>();
        try {
        	res = archiveActionprogramMainService.getCountByCase();
        } catch (Exception e) {
            logger.error("getByIdAndName error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",res));
    }
    
    @ApiOperation(value="查询所有装备")
    @GetMapping("/getAllEquipment")
    public ResponseEntity getAllEquipment(){
        Map<String,Object> res = new HashMap<>();
        List<EmergencyVehiclesRes> car = equipmentMapper.getAllEmergencyEquipment(2);//应急车辆的
        List<EmergencyVehiclesRes> other = equipmentMapper.getAllEmergencyEquipment(1);//非应急车辆的
        res.put("carData", car);
        res.put("otherData", other);
        try {
        } catch (Exception e) {
            logger.error("getByIdAndName error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",res));
    }
    
    @ApiOperation(value="查询事件的过程信息和反馈信息")
    @PostMapping("/getEventProcess")
    public ResponseEntity getEventProcess(){
        Object data;
        try {
        	data = dispatchActionprogramMainService.getEventProcess();
        } catch (Exception e) {
            logger.error("getEventProcess error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",data));
    }
    
    @ApiOperation(value="查询物联设备")
    @GetMapping("/getWarnByDevice")
    public ResponseEntity getWarnByDevice(){
        Object data;
        try {
        	data = receiveinfoService.getWarnByDevice();
        } catch (Exception e) {
            logger.error("getWarnByDevice error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",data));
    }
    @ApiOperation(value="根据任务Id查询反馈信息")
    @GetMapping("/getFeedBackByRelationId")
    public ResponseEntity getFeedBackByRelationId(@RequestParam(required = true) String taskId){
        List<Map<String,Object>> data;
        Map<String,Object>res=new HashMap<String, Object>();
    	Map<String,Object>paramsMap=new HashMap<String, Object>();
    	  List<Map<String,Object>>fileList=new ArrayList<Map<String,Object>>();
        try {
           	data = dispatchTaskFeedbackService.getFeedBackByRelationId(taskId);
           	res.put("feedBack", data);
      
           	if(data!=null&&data.size()>0) {
           		for(Map<String,Object> map:data) {
               	 	fileList=commonFileService.getMappingFile(map.get("id").toString());
           			if(fileList!=null&&fileList.size()>0) {
           				map.put("file", fileList);
           			}else {
           				map.put("file", new ArrayList<Map<String,Object>>());
           			}
           		
           		}
           		
           	}
           	
           	
        } catch (Exception e) {
            logger.error("getFeedBackByRelationId error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",res));
    }
    
    @ApiOperation(value="根据方案Id查询事件时间轴数据")
    @GetMapping("/getTimeAxisData")
    public ResponseEntity getTimeAxisData(@RequestParam(required = true) String actionprogramId){
    	Map<String,Object>res=new HashMap<String, Object>();
    	 Object data;
    	 Object data2;
         try {
        	data= dispatchActionprogramMainService.getTimeAxisData(actionprogramId);
        	data2=dispatchActionprogramMainService.getArchivelog(actionprogramId);
        	res.put("data", data);
        	res.put("detail", data2);
         } catch (Exception e) {
             logger.error("getTimeAxisData error",e);
             return ResponseEntity.ok(R.error());
         }
         return ResponseEntity.ok(R.ok().put("data",res));
    }
    
    @ApiOperation(value="查询事件每个分类前十条数据")
    @GetMapping("/getEventByType")
    public ResponseEntity getEventByType(@RequestParam(required = false) String sourceType){
    	Map<String,Object>res=new HashMap<String, Object>();
    	List<Map<String,Object>>sourceList=receiveinfoService.getSource();
        List<BaseEventVO> dataList = new ArrayList<BaseEventVO>();

    	for(int i=0;i<sourceList.size();i++) {
    		dataList=receiveinfoService.getEventByType(sourceList.get(i).get("id").toString())==null?new ArrayList<BaseEventVO>():receiveinfoService.getEventByType(sourceList.get(i).get("id").toString());
    		res.put(sourceList.get(i).get("id").toString(),dataList );
    	}
    	
    	   return ResponseEntity.ok(R.ok().put("data",res));
    	
    }

    @ApiOperation(value="查询事件每个分类前十条数据_按时间排序")
    @GetMapping("/getEventByTypeAndOrder")
    public ResponseEntity getEventByTypeAndOrder(@RequestParam(required = false) String sourceType){
        Map<String,Object>res=new HashMap<String, Object>();
        List<Map<String,Object>>sourceList=receiveinfoService.getSource();
        //List<CenterEvent> list=(List<CenterEvent>)baseEventService.getCurrUntreatedEvent();//推送的事件
        List<BaseEventVO> dataList = new ArrayList<BaseEventVO>();
        List<BaseEventVO> dataListEvent = new ArrayList<BaseEventVO>();
        List<String> dataIdList = new ArrayList<String>();

        List<String> isFirst = new ArrayList<String>();

        for(int i=0;i<sourceList.size();i++) {
           String sourceId = sourceList.get(i).get("id").toString();
            List<String>  eventIds = receiveinfoService.getEventIdsByType(sourceId);
            if(eventIds !=null &&eventIds.size()>0){
                dataIdList.addAll(eventIds);
                isFirst.add(eventIds.get(0));
            }
        }

        dataList = receiveinfoService.getEventByIds(dataIdList);

        if(dataList !=null &&dataList.size()>0){
//            for (BaseEventVO baseEventVO:dataList) {
//                for (CenterEvent centerEvent:list) {
//                    if(baseEventVO.getEventId().equals(centerEvent.getEventId())){
//                        dataListEvent.add(baseEventVO);
//                        break;
//                    }
//                }
//            }
            for (BaseEventVO baseEventVO:dataList) {
                boolean flag = false;
                for (String id:isFirst) {
                    if(baseEventVO.getEventId().equals(id)){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    baseEventVO.setIsFirstData("1");
                }else{
                    baseEventVO.setIsFirstData("0");
                }

            }
        }



        res.put("data",dataList);
        return ResponseEntity.ok(R.ok().put("data",res));

    }


}
