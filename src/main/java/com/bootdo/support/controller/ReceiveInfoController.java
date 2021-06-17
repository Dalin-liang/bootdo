package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.utils.*;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.base.Location;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.support.dto.ReceiveInfoDTO;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
import com.bootdo.support.service.TeamService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 信息接报管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 09:07:54
 */
 
@RestController
@RequestMapping("/receiveInfo")
public class ReceiveInfoController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiveInfoController.class);

	@Autowired
	private ReceiveinfoService receiveinfoService;

	@Autowired
    private BaseEventService baseEventService;

    @Autowired
    private TeamService teamService;


	/**
     * 查询
     * @param receiveInfoDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(ReceiveInfoDTO receiveInfoDTO,
							@RequestParam(value = "pageSize",required = false) String limit,
							@RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = receiveinfoService.get(receiveInfoDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<Map<String, Object>> receiveinfoList = receiveinfoService.list(query);
        int total = receiveinfoService.count(query);
        PageUtils pageUtils = new PageUtils(receiveinfoList, total);
        return pageUtils;
    }

    //任务列表
    @ResponseBody
    @GetMapping("/taskList")
    public PageUtils taskList(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<Map<String, Object>> receiveinfoList = receiveinfoService.taskList(query);
        int total = receiveinfoService.countTaskList(query);
        PageUtils pageUtils = new PageUtils(receiveinfoList, total);
        return pageUtils;
    }
    /**
     * 插入
     * @param receiveInfo
     * @return
     */
    @RequestMapping("/insert")
    public int insert(ReceiveInfo receiveInfo){
        Random random = new Random();
        int res = 0;
        try {
            int ends = random.nextInt(100);//产生两位随机数
            String dateUUid= DateUtils.format(new Date(), "yyyyMMddHHmmss") + String.format("%02d",ends);
            UserDO userDo= ShiroUtils.getUser();
            receiveInfo.setId(dateUUid);
            receiveInfo.setCreateBy(userDo.getUsername());
            receiveInfo.setCreateDate(new Date());
            receiveInfo.setIspush_time(new Date());
            res = receiveinfoService.insert(receiveInfo);
            if(receiveInfo.getTempsaveFlag() == 0 &&res == 1){
                if(receiveInfo.getTempsaveFlag() == 0 && receiveInfo.getExamine_type().equals("5")){//推送
                    CenterEvent centerEvent = baseEventService.getEventById(receiveInfo.getId());
                    if(StringUtils.isNotEmpty(receiveInfo.getLat_lon())){
                        Location.SimpleLocation location = new Location.SimpleLocation(receiveInfo.getLat_lon().split(",")[1],
                                receiveInfo.getLat_lon().split(",")[0]);
                        centerEvent.setLocation(location);
                    }
                    boolean bool = baseEventService.newEvent(centerEvent);
                    res = (bool == true ? 1 : 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *更新/修改
     * @param receiveInfo
     * @return
     */
    @RequestMapping("/update")
    public int update(ReceiveInfo receiveInfo){
        int res = 0;
        try {
            UserDO userDo=ShiroUtils.getUser();
            receiveInfo.setUpdateBy(userDo.getUsername());
            receiveInfo.setUpdateDate(new Date());
            res = receiveinfoService.update(receiveInfo);
            if(receiveInfo.getTempsaveFlag() == 0 && res == 1){
                if(receiveInfo.getTempsaveFlag() == 0 && receiveInfo.getExamine_type().equals("5")){
                   List<CenterEvent> list = (List<CenterEvent>)baseEventService.getCurrEvent();
                    CenterEvent centerEvent = baseEventService.getEventById(receiveInfo.getId());
                    if(StringUtils.isNotEmpty(receiveInfo.getLat_lon())){
                        Location.SimpleLocation location = new Location.SimpleLocation(receiveInfo.getLat_lon().split(",")[1],
                                receiveInfo.getLat_lon().split(",")[0]);
                        centerEvent.setLocation(location);
                    }
                    boolean bool = true;
                    if(!list.contains(centerEvent)){//如果一个事件已推送，则不再推送到大屏页面
                    	receiveInfo.setIspush_time(new Date());
                    	res = receiveinfoService.update(receiveInfo);
                        bool = baseEventService.newEvent(centerEvent);
                    }
                    res = (bool == true ? 1 : 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return receiveinfoService.delete(id);
    }


    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return receiveinfoService.getUniqueById(id);
    }

    @RequestMapping("/getSource")
    public List<Map<String,Object>> getSource(){
        return receiveinfoService.getSource();
    }

    /**
     * 事故类别列表
     * @param id 部门id
     * @return
     */
    @RequestMapping("/getAccidentType")
    public List<Map<String,Object>> getAccidentType(@RequestParam("id")String id){
        return receiveinfoService.getAccidentType(id);
    }

    /**
     * 获取当前值班人员列表
     * @return
     */
    @RequestMapping("/getCurrentDutyPerson")
    public List<Map<String, Object>> getCurrentDutyPerson(@RequestParam(value = "personId", required = false) String personId) {
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> list = null;
        try {
            if (StringUtils.isNotEmpty(personId)) {
                params.put("personId", personId);
            }else{
                params.put("personId", null);
            }
            //params.put("isException", "0");//正常状态
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            params.put("startTime", DateUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MINUTE, 59);
            params.put("endTime", DateUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
            list = receiveinfoService.getCurrentDutyPerson(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/queryExamine")
    public JSONObject queryExamine(ReceiveInfoDTO receiveInfoDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = receiveinfoService.queryExamine(receiveInfoDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 退回产品（终结）
     * @param id
     */
    @RequestMapping("/backReceiveInfo")
    public int backReceiveInfo(@RequestParam("id")String id){
        return receiveinfoService.updateReceiveExamineType(id);

    }

    /**
     * 根据事故类型id获取预警类型列表
     * @param id 事故类型id
     * @return
     */
    @RequestMapping("/getEarlyWarnTypeByAccidentId")
    public List<Map<String,Object>> getEarlyWarnTypeByAccidentId(@RequestParam("id")String id){
        return receiveinfoService.getEarlyWarnTypeByAccidentId(id);
    }

    /**
     * 预警级别列表
     * @param id 预警类型id
     * @return
     */
    @RequestMapping("/getEarlyWarnLevelByTypeId")
    public List<Map<String,Object>> getEarlyWarnLevelByTypeId(@RequestParam("id")String id){
        return receiveinfoService.getEarlyWarnLevelByTypeId(id);
    }

    /**
     * 综合态势页面推送事件所需数据
     * @return
     */
    @RequestMapping("/getCompushData")
    public Map<String, Object> getComPushData(){
        Map<String, Object> res = new HashMap<>();
        //接报途径数据
        List<Map<String, Object>> sourceTypeList = receiveinfoService.getSource();
        res.put("sourceType", sourceTypeList);
        //主管单位
        List<Map<String, Object>> deptList = teamService.getDept();
        res.put("chargeDept", deptList);
        return res;
    }

    /**
     * 综合态势事件推送（接报信息已存在的事件）
     * @param receiveInfo
     * @return
     */
    @RequestMapping("/comEventStart")
    public ResponseEntity comEventStart(@RequestBody ReceiveInfo receiveInfo){
        CenterEvent centerEvent = null;
        try {
            receiveInfo.setTempsaveFlag(0);
            receiveInfo.setExamine_type("5");
            receiveInfo.setAcceptance_type("5");
            receiveInfo.setIspush(5);
            receiveInfo.setIs_acceptance("1");
            receiveInfo.setCreateDate(new Date());
            receiveInfo.setIspush_time(new Date());
            int res = receiveinfoService.update(receiveInfo);
            logger.info("综合态势重大事件 推送更新信息 {}", receiveInfo);
            if(res == 1){
               centerEvent = baseEventService.getEventById(receiveInfo.getId());
                if(StringUtils.isNotEmpty(centerEvent.getLatLon())){
                    Location.SimpleLocation location = new Location.SimpleLocation(centerEvent.getLatLon().split(",")[1],
                            centerEvent.getLatLon().split(",")[0]);
                    centerEvent.setLocation(location);
                }
               baseEventService.newSituationEvent(centerEvent);//添加到事件list中
               baseEventService.startProcessEvent(receiveInfo.getId());//推送到应急预案列表中
               logger.info("综合态势重大事件 推送成功 {}", centerEvent);
            }
        } catch (Exception e) {
            logger.error("综合态势 重大事件推送失败 ERROR {}" , e);
            e.printStackTrace();
        }
        return ResponseEntity.ok(R.ok().put("data",centerEvent));
    }

    /**
     * 综合态势新增事件推送
     * @param receiveInfo
     * @return
     */
    @RequestMapping("/newEventPush")
    public ResponseEntity newEventPush(@RequestBody ReceiveInfo receiveInfo){
        Random random = new Random();
        int res = 0;
        CenterEvent centerEvent = null;
        try {
            int ends = random.nextInt(100);//产生两位随机数
            String dateUUid= DateUtils.format(new Date(), "yyyyMMddHHmmss") + String.format("%02d",ends);
            //UserDO userDo= ShiroUtils.getUser();
            receiveInfo.setId(dateUUid);
            receiveInfo.setCreateDate(new Date());
            receiveInfo.setIspush_time(new Date());
            receiveInfo.setTempsaveFlag(0);
            receiveInfo.setExamine_type("5");
            receiveInfo.setAcceptance_type("5");
            receiveInfo.setIspush(5);
            receiveInfo.setIs_acceptance("1");
            res = receiveinfoService.insert(receiveInfo);
            if(res == 1){
                    centerEvent = baseEventService.getEventById(receiveInfo.getId());
                    if(StringUtils.isNotEmpty(receiveInfo.getLat_lon())){
                        Location.SimpleLocation location = new Location.SimpleLocation(receiveInfo.getLat_lon().split(",")[1],
                                receiveInfo.getLat_lon().split(",")[0]);
                        centerEvent.setLocation(location);
                    }
                baseEventService.newSituationEvent(centerEvent);//添加到事件list中
                baseEventService.startProcessEvent(receiveInfo.getId());//推送到应急预案列表中
                logger.info("综合态势新增事件推送成功 {}", centerEvent);
            }
        } catch (Exception e) {
            logger.error("综合态势新增事件推送失败 ERROR {}" , e);
            e.printStackTrace();
        }
        return ResponseEntity.ok(R.ok().put("data",centerEvent));
    }

    @ApiOperation(value="终结发送")
    @RequestMapping("/comFinishedSendMsg")
    public ResponseEntity SmsSend(@ApiParam(name = "事件ID")@RequestParam(required = false)String eventId,
                                  @ApiParam(name = "操作人") @RequestParam(required = false)String operator,
                                  @ApiParam(name = "结论")@RequestParam(required = false)String finishType,
                                  @ApiParam(name = "说明") @RequestParam(required = false)String content
                                  ){
        //操作人 + 结论 + 说明
        String descible = "";
        try {

            //更新事件
            if(StringUtils.isNotEmpty(operator)){
                descible += operator;
            }
            if(StringUtils.isNotEmpty(finishType)){
                descible +=finishType;
            }
            if(StringUtils.isNotEmpty(content)){
                descible += content;
            }
            ReceiveInfo receiveInfo = new ReceiveInfo();
            if(StringUtils.isNotEmpty(eventId)){
                receiveInfo.setId(eventId);
                receiveInfo.setRemarks(descible);
                receiveInfo.setExamine_type("10");//终结
                receiveInfo.setAcceptance_type("10");
                receiveInfo.setUpdateDate(new Date());
                receiveinfoService.update(receiveInfo);
            }
        } catch (Exception e) {
            //logger.error("SmsSend error",e);
            return ResponseEntity.ok(R.error());
        }
        return ResponseEntity.ok(R.ok());
    }

    @RequestMapping("/getAppReportImgByEventId")
    public Map<String, Object> getAppReportImgByEventId(@ApiParam(name = "事件ID")@RequestParam("eventId")String eventId){
        Map<String, Object> map = new HashMap<>();
        List<CommonFileDO> imageList = receiveinfoService.getAppReportEventImg(eventId);
        map.put("data", imageList);
        return map;
    }
    @RequestMapping("/getDeptChargeMan")
    public Map<String, Object> getCurrentPerson(){
        Map<String, Object> res = new HashMap<>();
        //部门负责人
        List<Map<String, Object>> listcontact = receiveinfoService.getDeptContactPerson();

        res.put("deptContact", listcontact);
        //当前值班人员
        List<Map<String, Object>> dutyList = null;
        Map<String,Object> map = new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        map.put("startTime", DateUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MINUTE, 59);
        map.put("endTime", DateUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
        dutyList = receiveinfoService.getNowDutyPreson(map);
        res.put("dutyPerson", dutyList);
        return res;
    }
    @RequestMapping("/getUnTreatedEventList")
    public  JSONObject getUnTreatedEventList(@RequestParam(value = "limit",required = false) String limit,
			@RequestParam(value = "offset",required = false) String offset){
        JSONObject json=new JSONObject();
    	  List<CenterEvent> list=(List<CenterEvent>)baseEventService.getCurrUntreatedEvent();
    	  json.put("total",(int)list.size());
    	//  list=(List<CenterEvent>) ListPageUtils.startPage(list, Integer.valueOf(offset)+1, Integer.parseInt(limit));
    	  json.put("rows",list);
		return json;
    }
    //批量终结物联报警
    @RequestMapping("/batchEndCase")
    public  R batchEndCase(@RequestParam("ids[]") String[] ids){
    	if(receiveinfoService.batchEndCase(ids)>0) {
    		for(int i=0;i<ids.length;i++) {
                baseEventService.endWarnEvent(ids[i]);
        	}
    		return R.ok();
    	}
		return R.error();
    
    }


    @RequestMapping("/getChartSourceType")
    public JSONObject getChartSourceType(String year){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list =  receiveinfoService.getChartSourceType(year);
        result.put("data", list);
        return result;
    }

    @RequestMapping("/getChartLevel")
    public JSONObject getChartLevel(String year){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list =  receiveinfoService.getChartLevel(year);
        result.put("data", list);
        return result;
    }

    @RequestMapping("/getChartMonth")
    public JSONObject getChartMonth(String year){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list =  receiveinfoService.getChartMonth(year);
        int [] data = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
        if(list !=null && list.size()>0){
            for (Map<String,Object> map:list) {
                int value = Integer.parseInt(map.get("value").toString());
                int index = Integer.parseInt(map.get("month").toString());
                data[index-1] = value;
            }
        }
        result.put("data", data);
        return result;
    }
	
}
