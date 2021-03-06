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

    @ApiOperation(value="????????????", notes="")
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


    @ApiOperation(value="????????????????????????", notes="")
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


    @ApiOperation(value="????????????", notes="")
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
            //ws??????
            baseEventService.startActionProg(eventId,actionId);
        } catch (Exception e) {
            logger.error("pub error",e);
        }
        return ResponseEntity.ok(R.ok());
    }

    @ApiOperation(value="??????????????????", notes="")
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
                //ws??????
                baseEventService.eventReLocation(eventId,lat,lon,address);
            }
        } catch (Exception e) {
            logger.error("pub error",e);
        }
        return ResponseEntity.ok(R.ok());
    }

    @ApiOperation(value="????????????????????????")
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


    @ApiOperation(value="????????????????????????????????????")
    @PostMapping(value = "/getTaskStep")
    public ResponseEntity getTaskStepByParam(@ApiParam(name = "??????Id")@RequestParam(required = true) String actionProgramId,
                                             @ApiParam(name = "????????????")@RequestParam(required = false) String name,
                                             @ApiParam(name = "????????????")@RequestParam(required = false) String content,
                                             @ApiParam(name = "???????????????")@RequestParam(required = false) String liabilityMan){
        List<Map<String, Object>> result;
        try {
            result = dispatchActionprogramMainService.getTaskAndRespDeptByParams(actionProgramId,name,content,liabilityMan);
        } catch (Exception e) {
            logger.error("getTaskStepByParam error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }


    @ApiOperation(value="??????????????????")
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


    @ApiOperation(value="??????????????????")
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


    @ApiOperation(value="??????????????????")
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


    @ApiOperation(value="??????")
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
    
    @ApiOperation(value="????????????")
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

    @ApiOperation(value="??????????????????")
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

    @ApiOperation("??????????????????")
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

//    @ApiOperation("???????????????")
//    @RequestMapping("/knowledge/query")
//    public ResponseEntity queryKnowledge(@ApiParam(name = "id")@RequestParam(required = false)String id,
//                                         @ApiParam(name = "????????????ID") @RequestParam(required = false)String warnId,
//                                         @ApiParam(name = "??????") @RequestParam(required = false)Integer type ,
//                                         @ApiParam(name = "??????") @RequestParam(required = false)String title ,
//                                         @ApiParam(name = "?????????") @RequestParam(required = false)String keywork ,
//                                         @ApiParam(name = "??????") @RequestParam(required = false)String source  ){
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
    
    @ApiOperation("???????????????")
    @RequestMapping("/knowledgeInfo/query")
    public ResponseEntity queryKnowledgeInfo(@ApiParam(name = "??????") @RequestParam(required = false)String name,
                                         @ApiParam(name = "??????") @RequestParam(required = false)String otherName ,
                                         @ApiParam(name = "???????????????") @RequestParam(required = false)String dangerType  ){
        List<Map<String, Object>> result = null;
        try {
        	result = knowledgeInfoService.getknowledgeByParam("", name, otherName, dangerType);
        } catch (Exception e) {
            logger.error("startAction error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }
    
    @ApiOperation("?????????????????????")
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

    @ApiOperation(value="?????????????????????")
    @RequestMapping("/getTaskContact")
    public ResponseEntity queryTaskContact(@ApiParam(name = "????????????????????? ")@RequestParam(required = false)String flag ,
                                         @ApiParam(name = "?????????Id") @RequestParam(required = false)String liabilityId ,
                                         @ApiParam(name = "???????????????????????????") @RequestParam(required = false)Integer type ,
                                         @ApiParam(name = "????????????") @RequestParam(required = false)String deptName, 
                                         @ApiParam(name = "??????Id") @RequestParam(required = false)String actionprogramId
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
    
    @ApiOperation(value="????????????")
    @PostMapping("/smsSend")
    public ResponseEntity SmsSend(@ApiParam(name = "????????? ")@RequestParam(required = false)String mobiles,//???????????????????????????????????????????????????
                                         @ApiParam(name = "????????????") @RequestParam(required = false)String content ,
                                         @ApiParam(name = "????????????Json?????????") @RequestParam(required = false)String jsonStr  //?????????????????????????????????????????????(mobile,associationTableName,associationTableId,actionprogramId)
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
            logger.error("??????????????????????????????result???null");
            return ResponseEntity.ok(R.error("??????????????????"));
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    @ApiOperation(value="???????????????")
    @RequestMapping("/getArchiveCase")
    public ResponseEntity getArchiveCase(@ApiParam(name = "????????????ID ")@RequestParam(required = false)String warnTypeId ,
                                         @ApiParam(name = "????????????") @RequestParam(required = false)String accidentName ,
                                         @ApiParam(name = "????????????") @RequestParam(required = false)String  warnTypeName ,
                                         @ApiParam(name = "????????????") @RequestParam(required = false)String warnLevelName
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

    @ApiOperation(value="??????????????????")
    @RequestMapping("/getArchiveCaseDetails")
    public ResponseEntity getArchiveCaseDetails(@ApiParam(name = "??????ID ")@RequestParam(required = true)String archiveActionprogramId){
        Map<String,Object> result =new HashMap<String, Object>();
        try {
            ArchiveActionprogramMainDO archiveActionprogramMainDO = archiveActionprogramMainService.get(archiveActionprogramId);
            result.put("archiveActionprogram", archiveActionprogramMainDO);

            ArchivePlanmainDO archivePlanMain = archivePlanmainService.getByActionprogramId(archiveActionprogramId);
            if(archivePlanMain != null){
                result.put("archivePlanMain", archivePlanMain);
                String planMainId = archivePlanMain.getId();
                //????????????
                List<ArchiveRespdeptDO> respDeptList = archiveRespdeptService.getByPlanMainId(planMainId);
                result.put("archiveRespDept", respDeptList);

                //??????
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
                            switch (type) { // ???????????????????????????(??????????????????????????????)
                                case 1: // ??????
                                    deptPersonMap = deptPersonMapper.getUniqueById(liabilityId);
                                    if(deptPersonMap !=null){
                                        planTaskMap.put("liabilityObject", (String) deptPersonMap.get("name"));
                                    }
                                    break;
                                case 2: // ????????????
                                    teamMap =  teamService.getUniqueById(liabilityId);
                                    if(teamMap !=null){
                                        planTaskMap.put("liabilityObject", (String)teamMap.get("name"));
                                    }
                                    break;
                                case 3: // ??????
                                    supportDeptMap =  supportDeptService.getUniqueById(liabilityId);
                                    if(supportDeptMap !=null){
                                        planTaskMap.put("liabilityObject", (String)supportDeptMap.get("name"));
                                    }
                                    break;
                                case 4: // ??????
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

    @ApiOperation(value="?????????????????????????????????")
    @RequestMapping("/getSimilarCase")
    public ResponseEntity getSimilarCase(@ApiParam(name = "????????????ID ")@RequestParam(required = false)String eventId){
        List<Map<String,Object>> result;
        try {
            result = archiveActionprogramMainService.getProgramMainDetailByWarnTypeId(eventId);
        } catch (Exception e) {
            logger.error("getSimilarCase error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }
    
    @ApiOperation(value="??????????????????")
    @RequestMapping("/getCaseByLost")
    public ResponseEntity getCaseByLost(@ApiParam(name = "???????????? ")@RequestParam(required = false)String accidentName ,
            @ApiParam(name = "????????????") @RequestParam(required = false)String warnTypeName ,
            @ApiParam(name = "????????????") @RequestParam(required = false)String  warnLevelName ,
            @ApiParam(name = "????????????") @RequestParam(required = false)String code,
            @ApiParam(name = "????????????") @RequestParam(required = false)String  planName ,
            @ApiParam(name = "?????????????????????") @RequestParam(required = false)String  beginTime ,
            @ApiParam(name = "?????????????????????") @RequestParam(required = false)String  endTime,
            @ApiParam(name = "???????????????") @RequestParam(required = false)String receiveBtime,
            @ApiParam(name = "???????????????") @RequestParam(required = false)String receiveEtime){
        List<Map<String,Object>> result;
        try {
            result = archiveActionprogramMainService.getByLostParam(accidentName, warnTypeName, warnLevelName, code, planName, beginTime, endTime,receiveBtime,receiveEtime);
        } catch (Exception e) {
            logger.error("getCaseByLost error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",result));
    }

    //----------???????????????????????????
    @ApiOperation(value="????????????????????????")
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


    //-------------??????????????????

    @ApiOperation(value="????????????")
    @PostMapping("/feedBack")
    public ResponseEntity addFeedBack(@RequestBody FeedBackVO feedBackVO){
        if(CommonUtils.isEmpty(feedBackVO.getDetails()))
            return ResponseEntity.ok(R.error("??????????????????"));

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


    @ApiOperation(value="??????????????????")
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


    @ApiOperation(value="??????????????????")
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
    
    @ApiOperation(value="?????????????????????????????????")
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
    
    @ApiOperation(value="??????????????????")
    @GetMapping("/getAllEquipment")
    public ResponseEntity getAllEquipment(){
        Map<String,Object> res = new HashMap<>();
        List<EmergencyVehiclesRes> car = equipmentMapper.getAllEmergencyEquipment(2);//???????????????
        List<EmergencyVehiclesRes> other = equipmentMapper.getAllEmergencyEquipment(1);//??????????????????
        res.put("carData", car);
        res.put("otherData", other);
        try {
        } catch (Exception e) {
            logger.error("getByIdAndName error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok().put("data",res));
    }
    
    @ApiOperation(value="??????????????????????????????????????????")
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
    
    @ApiOperation(value="??????????????????")
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
    @ApiOperation(value="????????????Id??????????????????")
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
    
    @ApiOperation(value="????????????Id???????????????????????????")
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
    
    @ApiOperation(value="???????????????????????????????????????")
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

    @ApiOperation(value="???????????????????????????????????????_???????????????")
    @GetMapping("/getEventByTypeAndOrder")
    public ResponseEntity getEventByTypeAndOrder(@RequestParam(required = false) String sourceType){
        Map<String,Object>res=new HashMap<String, Object>();
        List<Map<String,Object>>sourceList=receiveinfoService.getSource();
        //List<CenterEvent> list=(List<CenterEvent>)baseEventService.getCurrUntreatedEvent();//???????????????
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
