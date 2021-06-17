package com.bootdo.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskFeedbackDetailDO;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.actionprogramManage.service.DispatchTaskFeedbackService;
import com.bootdo.address.domain.AddressGroupDO;
import com.bootdo.address.service.AddressGroupService;
import com.bootdo.app.dao.APPMapper;
import com.bootdo.app.service.impl.APPEventServiceImpl;
import com.bootdo.app.util.Point;
import com.bootdo.app.util.PointUtil;
import com.bootdo.common.service.CommonFileService;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.planManage.service.PlanEarlywarnTypeService;
import com.bootdo.sms.service.SmsSendRecordService;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.support.entity.SupportExpertInfo;
import com.bootdo.support.entity.SupportTeam;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.support.service.TeamService;
import io.swagger.annotations.ApiOperation;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/app")
public class APPcontroller {
    @Autowired
    APPEventServiceImpl aPPEventServiceImpl;
    @Autowired
    public APPMapper appMapper;

    @Autowired
    private DispatchActionprogramMainService dispatchActionprogramMainService;

    @Autowired
    private DispatchTaskFeedbackService dispatchTaskFeedbackService;

    @Autowired
    private CommonFileService commonFileService;

    @Autowired
    private SupportDeptService supportDeptService;

    @Autowired
    private PlanEarlywarnTypeService planEarlywarnTypeService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private AddressGroupService addressGroupService;

    @Autowired
    private SmsSendRecordService sendRecordService;

    @Autowired
    private PointUtil pointUtil;


    /**
     * 获取新消息数量
     * @param params
     * @return
     */
    @RequestMapping("/getmsgnews")
    public ResponseEntity getmsgnews(@RequestParam Map<String, Object> params){
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
       try {
           String userid = (String) params.get("userid");
           HashMap<String, Object> p = new HashMap<String, Object>();
           p.put("jsuserid", userid);
           p.put("type", "1");
           int m2 = aPPEventServiceImpl.getEmergencyEventInfo(userid).size();
           int m1 = aPPEventServiceImpl.getEmergencyEventMessage(userid).size();
           int m3 = appMapper.queryappmsgcount(p);
           return ResponseEntity.ok(R.ok().put("m1", m1).put("m2", m2).put("m3", m3));
       }catch(Exception e){
           return ResponseEntity.ok(R.error(e.getMessage()));
       }
    }

    @RequestMapping("/tsmsg")
    public ResponseEntity gettsMsg(@RequestParam Map<String, Object> params){
       String userid=(String)params.get("userid");
        try {
            return ResponseEntity.ok(R.ok().put("data", aPPEventServiceImpl.getEmergencyEventInfo(userid)));
        }catch(Exception e){
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }


    /**
     * 获取app推送消息
     *
     * @param params
     * @return
     */
    @RequestMapping("/appmsg")
    public ResponseEntity getMsg(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
        String startTime = (String)params.get("startTime");
        String endTime =  (String)params.get("endTime");
        try {
        HashMap<String, Object> p = new HashMap<String, Object>();
        if(startTime!=null)
            p.put("startTime", sf.parse(startTime + " 00:00:00"));
        if(endTime!=null)
            p.put("endTime", sf.parse(endTime + " 23:59:59"));
        p.put("id", params.get("id"));
        p.put("fsuserid", params.get("fsuserid"));
        p.put("jsuserid", params.get("jsuserid"));
        p.put("type", params.get("type"));
        p.put("messages",params.get("messages")!=null?("%"+params.get("messages")+"%"):null);

       Integer count= appMapper.queryappmsgcount(p);

        Integer currPage=Integer.parseInt((String)params.get("pageindex"));
        Integer pageSize=Integer.parseInt((String)params.get("pageSize"));
        p.put("currIndex", currPage*pageSize);
        p.put("pageSize", pageSize);
        List<Map<String, Object>> l= appMapper.queryappmsg(p);
        Double pagecounts=Math.ceil(count/pageSize);
        int pagecount=(count-pagecounts.intValue()*pageSize>0?(pagecounts.intValue()+1):pagecounts.intValue());
            return ResponseEntity.ok(R.ok().put("data",l).put("count",count).put("pagecount",pagecount));
        }catch(Exception e){
            return ResponseEntity.ok(R.error(e.getMessage()));
        }

    }

    /**
     * 发送APP消息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/sendappmsg")
    public ResponseEntity sendmsg(HttpServletRequest request,
                                     HttpServletResponse response){
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        try {
        p.put("id",UUID.randomUUID().toString().replaceAll("-",""));
        p.put("fsuserid",request.getParameter("fsuserid"));
        p.put("fsusername",request.getParameter("fsusername"));
        p.put("jsuserid",request.getParameter("jsuserid"));
        p.put("jsusername",request.getParameter("jsusername"));
        p.put("type","1");
        p.put("fstime",new Date());
        p.put("messages",request.getParameter("messages"));
        appMapper.msgadd(p);


        //把新消息放进队列
        /*APPSysController.putmsglink(request.getParameter("messages"),
                "appmsg",
                request.getParameter("fsusername"),
                request.getParameter("jsusername"));*/
            return ResponseEntity.ok(R.ok("发送成功！"));
        }catch(Exception e){
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }


    /**
     * 标记消息为已读
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/appmsgrd")
    public ResponseEntity msgrd(HttpServletRequest request,
                                       HttpServletResponse response){
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        try {
        p.put("id", request.getParameter("id"));
        appMapper.msgrd(p);
        return ResponseEntity.ok(R.ok());
    }catch(Exception e){
        return ResponseEntity.ok(R.error(e.getMessage()));
    }
    }
    /**
     * 获取应急推送的消息
     *
     * @param params
     * @return
     */
    @RequestMapping("/yjmsg")
    public ResponseEntity getyjMsg(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        try {
            return ResponseEntity.ok(R.ok().put("data",aPPEventServiceImpl.getEmergencyEventMessage((String) params.get("userid"))));
        }catch(Exception e){
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    /**
     * 获取事件对应的应急方案
     *
     * @param params
     * @return
     */
    @RequestMapping("/getYjfa")
    public ResponseEntity getYjfa(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            List<Map<String, Object>> list = dispatchActionprogramMainService.getTaskAndRespDeptByParams((String) params.get("id"),"","","");
            //重新封装map对象
            for (Map<String, Object> map : list){
                String taskType = map.get("taskType").toString();
                if ("1".equals(taskType)){
                    Map<String, Object> subMap = new HashMap<>();
                    subMap.put("id", map.get("id"));
                    subMap.put("actionprogram_id", map.get("actionprogram_id"));
                    String liabilityId = map.get("liability_id") != null ? map.get("liability_id").toString() : map.get("id").toString();
                    String type = map.get("liability_id") != null ? "1" : "5";//5表示部门任务，
                    subMap.put("liability_id", liabilityId);
                    subMap.put("type", type);
                    subMap.put("taskName", map.get("name"));
                    subMap.put("taskContent", map.get("content"));
                    subMap.put("taskStatus", "");
                    subMap.put("sort_no", map.get("sortNo"));
                    resultList.add(subMap);
                }
            }
            //根据sortNo升序排序
            if (CollectionUtils.isNotEmpty(resultList)) {
                Collections.sort(resultList, new Comparator<Map<String, Object>>() {
                    //降序排序
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String obj1 = o1.get("sort_no") != null ? o1.get("sort_no").toString() : "";
                        String obj2 = o2.get("sort_no") != null ? o2.get("sort_no").toString() : "";
                        if (StringUtils.isNotEmpty(obj1) && StringUtils.isNotEmpty(obj2)) {
                            Integer sortNo1 = Integer.parseInt(obj1);
                            Integer sortNo2 = Integer.parseInt(obj2);
                            return sortNo1.compareTo(sortNo2);
                        }
                       return 1;
                    }
                });
            }
            return ResponseEntity.ok(R.ok().put("data", resultList));
            //aPPEventServiceImpl.getTaskListByEventProgramId((String) params.get("id"))));
        }catch(Exception e){
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }
    @RequestMapping("/getyjdw")
    public ResponseEntity getyjdw(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
            try {
                return ResponseEntity.ok(R.ok().put("data",aPPEventServiceImpl.getEventEmergencyPerson((String) params.get("id"),(String) params.get("type"))));
            }catch(Exception e){
                return ResponseEntity.ok(R.error(e.getMessage()));
            }
    }


    /**
     * 部门和人员数据
     */
    @RequestMapping("/getDeptAndPersons")
    public ResponseEntity getDeptAndPersons(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        Map<String, Object> r = new HashMap<String, Object>();
        try {
        r.put("name",params.get("user_name")!=null?("%"+params.get("user_name")+"%"):null);
        r.put("type",params.get("type"));

        Integer count= appMapper.queryphonebookcount(r);

        Integer currPage=Integer.parseInt((String)params.get("pageindex"));
        Integer pageSize=Integer.parseInt((String)params.get("pageSize"));
        r.put("currIndex", currPage*pageSize);
        r.put("pageSize", pageSize);
        List<Map<String, Object>> l=appMapper.queryphonebook(r);
        Double pagecounts=Math.ceil(count/pageSize);
        int pagecount=(count-pagecounts.intValue()*pageSize>0?(pagecounts.intValue()+1):pagecounts.intValue());
        return ResponseEntity.ok(R.ok().put("data",l).put("count",count).put("pagecount",pagecount));
        }catch(Exception e){
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    @ApiOperation(value="获取当天值守人员", notes="")
    @GetMapping("/watchman")
    public ResponseEntity getAllWatchman(){
        try {
            return ResponseEntity.ok(R.ok().put("data",aPPEventServiceImpl.getAllWitchManRes()));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }
    @ApiOperation(value="所有装备(不包含车辆)", notes="")
    @GetMapping("/equipment")
    public ResponseEntity getAllEquipment(){
        try {
            return ResponseEntity.ok(R.ok().put("data",aPPEventServiceImpl.getAllEquipRes()));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }
    @ApiOperation(value="获取所有应急队伍", notes="")
    @GetMapping("/emergencyTeam")
    public ResponseEntity getAllTeam(){
        try {
            return ResponseEntity.ok(R.ok().put("data",aPPEventServiceImpl.getAllRes()));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }
    /**
     * 救援上报
     */
    @RequestMapping("/rwfk")
    public ResponseEntity rwfk(HttpServletRequest request,
                                     HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        try {
        DispatchTaskFeedbackDO dispatchTaskFeedback = new DispatchTaskFeedbackDO();

        dispatchTaskFeedback.setFromTable("dispatch_task");
        dispatchTaskFeedback.setFeedbackDate(new Date());
        dispatchTaskFeedback.setSourceType(0);
        dispatchTaskFeedback.setPersonType(0);
        //UUID.randomUUID().toString().replaceAll("-", "")
        dispatchTaskFeedback.setId(request.getParameter("id"));
        dispatchTaskFeedback.setCreateBy(request.getParameter("userid"));

        dispatchTaskFeedback.setRelationId(request.getParameter("rtid"));
        dispatchTaskFeedback.setAddress(request.getParameter("address"));
        dispatchTaskFeedback.setRemark(request.getParameter("remark"));
        dispatchTaskFeedback.setDeptpersonId(request.getParameter("perselctionid"));
        dispatchTaskFeedback.setDeptpesonName(request.getParameter("perselctionname"));

        List<DispatchTaskFeedbackDetailDO> detailDOList = new ArrayList<DispatchTaskFeedbackDetailDO>();
        //根据id查询附件表，生成 List<DispatchTaskFeedbackDetailDO>

            DispatchTaskFeedbackDetailDO dd = new DispatchTaskFeedbackDetailDO();
            dd.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            dd.setFeedbackId(dispatchTaskFeedback.getId());
            dd.setContent(dispatchTaskFeedback.getRemark());
            dd.setType(1);
            detailDOList.add(dd);
        aPPEventServiceImpl.saveFeedbackAndDetail(dispatchTaskFeedback, detailDOList, request.getParameter("apid"));
            return ResponseEntity.ok(R.ok("上报成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }






    /**
     * 签到
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/savesign")
    protected ResponseEntity saveSign2(HttpServletRequest request,
                                            HttpServletResponse response){
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        try {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<String, Object>();
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String userid = request.getParameter("userid");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        HashMap<String, Object> p = new HashMap<String, Object>();
        p.put("id", UUID.randomUUID().toString());
        p.put("latitude", latitude);
        p.put("longitude", longitude);
        p.put("userid", userid);
        p.put("username", username);
        p.put("address", address);
        p.put("signtime", new Date());
        p.put("startTime", sf.parse(sf.format(new Date()) + " 00:00:00"));
        p.put("endTime", sf.parse(sf.format(new Date())) + " 23:59:59");
        p.put("currIndex", 0);
        p.put("pageSize", 10);
        List<Map<String, Object>> l = appMapper.getsignByDay(p);
        if (l.size() == 0) {
            appMapper.savesign(p);
            return ResponseEntity.ok(R.ok().put("msg","签到成功"));
        } else {
            return ResponseEntity.ok(R.ok().put("msg","今天已经签到"));
        }
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    /**
     * 签到记录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querysignlist")
    protected ResponseEntity querysignlist(HttpServletRequest request,
                                                      HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        try {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String userid = request.getParameter("userid");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        HashMap<String, Object> p = new HashMap<String, Object>();
        p.put("userid", userid);
        if(startTime!=null&&!"".equals(startTime))
        p.put("startTime", sf.parse(startTime + " 00:00:00"));
        if(endTime!=null&&!"".equals(endTime))
        p.put("endTime", sf.parse(endTime + " 23:59:59"));

        Integer count= appMapper.getsignByDaycount(p);

        Integer currPage=Integer.parseInt(request.getParameter("pageindex"));
        Integer pageSize=Integer.parseInt(request.getParameter("pageSize"));
        p.put("currIndex", currPage*pageSize);
        p.put("pageSize", pageSize);

        List<Map<String, Object>> l = appMapper.getsignByDay(p);

        Double pagecounts=Math.ceil(count/pageSize);
        int pagecount=(count-pagecounts.intValue()*pageSize>0?(pagecounts.intValue()+1):pagecounts.intValue());
        return ResponseEntity.ok(R.ok().put("data",l).put("count",count).put("pagecount",pagecount));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    /***
     * 生成执法人轨迹
     * @param request
     * @param response
     */
    @RequestMapping(value = "/personTrajectory")
    protected Map<String, Object> personTrajectory(HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        String userid = request.getParameter("userid");
        String longitude = request.getParameter("longitude");//经度
        String latitude = request.getParameter("latitude");//纬度

        if (longitude != null && !longitude.equals("") && userid != null) {
            HashMap<String, Object> p = new HashMap<String, Object>();
            p.put("id", UUID.randomUUID().toString());
            p.put("userid", userid);
            p.put("longitude", longitude);
            p.put("latitude", latitude);
            p.put("create_time", new Date());
            appMapper.personTrajectory(p);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "ok");
        return map;
    }

    /**
     * 巡查上報
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dailyreport")
    protected ResponseEntity dailyreport(HttpServletRequest request,
                                              HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        Point point=new Point();
        try{
        point =   pointUtil.bd_google_encrypt(Double.parseDouble(request.getParameter("latitude")),Double.parseDouble(request.getParameter("longitude")));
        p.put("id", request.getParameter("id"));
        p.put("lat", request.getParameter("latitude"));//point.getLat()
        p.put("lon", request.getParameter("longitude"));//point.getLon()
        p.put("create_by", request.getParameter("userid"));
        p.put("repname", request.getParameter("username"));
        p.put("eventaddr", request.getParameter("eventaddr"));
        p.put("eventdesc", request.getParameter("eventdesc"));
        p.put("repdate", new Date());
        p.put("create_date", new Date());
        //event_type改为watch_source表中的app上报类型的ID,event_type也是改为从watch_source途径表中获取的
        p.put("event_type", request.getParameter("event_type"));
        appMapper.dailyreport(p);
        //巡查上报推送到综合态势
        aPPEventServiceImpl.appWarnEventReport(p);
        return ResponseEntity.ok(R.ok("上报成功！"));
    } catch (Exception e) {
        return ResponseEntity.ok(R.error(e.getMessage()));
    }
    }

    /**
     * 上报记录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querydailyreportlist")
    protected ResponseEntity querydailyreportlist(HttpServletRequest request,
                                                             HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
try{
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String userid = request.getParameter("userid");
        String id = request.getParameter("id");
        HashMap<String, Object> p = new HashMap<String, Object>();
        if(startTime!=null&&!"".equals(startTime))
        p.put("startTime", sf.parse(startTime + " 00:00:00"));
        if(endTime!=null&&!"".equals(endTime))
        p.put("endTime", sf.parse(endTime + " 23:59:59"));
        p.put("userid", userid);
        p.put("id", id);

        Integer count= appMapper.getdailyreportByDaycount(p);

        Integer currPage=Integer.parseInt(request.getParameter("pageindex"));
        Integer pageSize=Integer.parseInt(request.getParameter("pageSize"));
        p.put("currIndex", currPage*pageSize);
        p.put("pageSize", pageSize);

        List<Map<String, Object>> l = appMapper.getdailyreportByDay(p);

        Double pagecounts=Math.ceil(count/pageSize);
        int pagecount=(count-pagecounts.intValue()*pageSize>0?(pagecounts.intValue()+1):pagecounts.intValue());
        return ResponseEntity.ok(R.ok().put("data",l).put("count",count).put("pagecount",pagecount));
    } catch (Exception e) {
        return ResponseEntity.ok(R.error(e.getMessage()));
    }
    }

    /**
     * 获取资料普查列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querylocaleData")
    protected ResponseEntity querylocaleData(HttpServletRequest request,
                                                             HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String userid = request.getParameter("userid");
        String username = request.getParameter("username");

        HashMap<String, Object> p = new HashMap<String, Object>();
        if(startTime!=null&&!"".equals(startTime))
        p.put("startTime", sf.parse(startTime + " 00:00:00"));
        if(endTime!=null&&!"".equals(endTime))
        p.put("endTime", sf.parse(endTime + " 23:59:59"));
//        p.put("userid", userid);
//        p.put("username", username);
        p.put("id", request.getParameter("id"));
        p.put("bh",request.getParameter("bh")!=null?("%"+request.getParameter("bh")+"%"):null);
        p.put("name", request.getParameter("name")!=null?("%"+request.getParameter("name")+"%"):null);
        if(request.getParameter("type")!=null)
        p.put("type", request.getParameter("type").equals("")?null:Integer.parseInt( request.getParameter("type")));

        Integer count= appMapper.querylocaleDatacount(p);

        Integer currPage=Integer.parseInt(request.getParameter("pageindex"));
        Integer pageSize=Integer.parseInt(request.getParameter("pageSize"));
        p.put("currIndex", currPage*pageSize);
        p.put("pageSize", pageSize);

        List<Map<String, Object>> l = appMapper.querylocaleData(p);
            Double pagecounts=Math.ceil(count/pageSize);
            int pagecount=(count-pagecounts.intValue()*pageSize>0?(pagecounts.intValue()+1):pagecounts.intValue());
            return ResponseEntity.ok(R.ok().put("data",l).put("count",count).put("pagecount",pagecount));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    /**
     * 资料上报
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/placeinfoadd")
    protected ResponseEntity placeinfoadd(HttpServletRequest request,
                                                             HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        try{
        p.put("id", request.getParameter("id"));
        p.put("createuserid", request.getParameter("userid"));
        p.put("createusername", request.getParameter("username"));
        p.put("bh", request.getParameter("bh"));
        p.put("name", request.getParameter("name"));
        p.put("address", request.getParameter("address"));
        p.put("fzr", request.getParameter("fzr"));
        p.put("phone", request.getParameter("phone"));
        p.put("bz", request.getParameter("bz"));
        p.put("lon", request.getParameter("lon"));
        p.put("lat", request.getParameter("lat"));
        p.put("type",request.getParameter("type").equals("")||request.getParameter("type")==null?null:Integer.parseInt( request.getParameter("type")));
        p.put("mainpollutants", request.getParameter("mainpollutants"));
        p.put("createtime", new Date());
        appMapper.placeinfoadd(p);
            return ResponseEntity.ok(R.ok());
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    /**
     * 资料修改
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/placeinfoedit")
    protected ResponseEntity placeinfoedit(HttpServletRequest request,
                                               HttpServletResponse response) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        try{
        p.put("id", request.getParameter("id"));
        p.put("createuserid", request.getParameter("userid"));
        p.put("createusername", request.getParameter("username"));
        p.put("bh", request.getParameter("bh"));
        p.put("name", request.getParameter("name"));
        p.put("address", request.getParameter("address"));
        p.put("fzr", request.getParameter("fzr"));
        p.put("phone", request.getParameter("phone"));
        p.put("bz", request.getParameter("bz"));
        p.put("lon", request.getParameter("lon"));
        p.put("lat", request.getParameter("lat"));
        p.put("type",request.getParameter("type").equals("")||request.getParameter("type")==null?null:Integer.parseInt( request.getParameter("type")));
        p.put("mainpollutants", request.getParameter("mainpollutants"));
        p.put("createtime", new Date());
        appMapper.placeinfoedit(p);
        return ResponseEntity.ok(R.ok("修改成功！"));
    } catch (Exception e) {
        return ResponseEntity.ok(R.error(e.getMessage()));
    }
    }

    @RequestMapping("/querydisct")
    public ResponseEntity querydisct(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        HashMap<String, Object> p = new HashMap<String, Object>();
        try{
        p.put("type",params.get("type"));
        p.put("del_flag","0");
        return ResponseEntity.ok(R.ok().put("data",appMapper.querydisct(p)));
    } catch (Exception e) {
        return ResponseEntity.ok(R.error(e.getMessage()));
    }
    }

    /**
     * 根据 任务taskId 获取该任务的反馈信息
     * @param params 任务taskId
     * @return
     */
    @RequestMapping("/getFeedbackByTaskId")
    public ResponseEntity getFeedbackByTaskId(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null){
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
        Map<String,Object>res=new HashMap<String, Object>();
        List<Map<String,Object>>fileList=new ArrayList<Map<String,Object>>();
        try{
            String taskId = params.get("taskId").toString();
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
            return ResponseEntity.ok(R.ok().put("data",res));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }


    /**
     * 获取通讯录 分组信息
     */
    @RequestMapping("/getDeptAndPersons_new")
    public ResponseEntity getDeptAndPersons_new(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null)
        {
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        String deptname=(String)params.get("deptname");
        String username=(String)params.get("username");
        Map<String,Object> returnMap = new HashMap<>();
        try {
            //应急部门和应急人员
            List<SupportDeptDO> depts = supportDeptService.getDeptAndPersons();
            List<Map<String,Object>> nodeList = new ArrayList<>();
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(depts)) {
                for (SupportDeptDO dept : depts) {
                    if(deptname!=null&&!"".equals(deptname)&&dept.getName().indexOf(deptname)==-1)
                        continue;
                    Map<String, Object> father = new HashMap<>();
                    father.put("name", dept.getName());
                    father.put("id", dept.getId());
                    boolean b=false;
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(dept.getDeptperson())) {
                        List<Map<String, Object>> childrenNode = new ArrayList<>();
                        for (SupportDeptPerson person : dept.getDeptperson()) {
                            if(username!=null&&!"".equals(username)&&person.getName().indexOf(username)==-1)
                                continue;
                            Map<String, Object> children = new HashMap<>();
                            children.put("name", person.getName());
                            children.put("id", person.getId());
                            children.put("mobile", person.getMobile());
                            children.put("position", person.getPosition());
                            childrenNode.add(children);
                        }
                        if(childrenNode.size()>0)
                        {
                            b=true;
                        father.put("children", childrenNode);
                        }
                    }
                    if(b)
                    nodeList.add(father);
                }
            }
            //应急专家
            List<PlanEarlywarnTypeDO> earlyWarnType = planEarlywarnTypeService.getEarlyWarnTypeAndExpertInfo();
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(earlyWarnType)){
                for (PlanEarlywarnTypeDO warnType : earlyWarnType){
                    if(deptname!=null&&!"".equals(deptname)&&warnType.getName().indexOf(deptname)==-1)
                        continue;
                    Map<String, Object> expertFather = new HashMap<>();
                    expertFather.put("name", warnType.getName());
                    expertFather.put("id", warnType.getId());
                    boolean b=false;
                    if(org.apache.commons.collections.CollectionUtils.isNotEmpty(warnType.getExperInfo())){
                        List<Map<String, Object>> expertListNode = new ArrayList<>();
                        for (SupportExpertInfo expert : warnType.getExperInfo()){
                            if(username!=null&&!"".equals(username)&&expert.getName().indexOf(username)==-1)
                                continue;
                            Map<String, Object> expertChildren = new HashMap<>();
                            expertChildren.put("name", expert.getName());
                            expertChildren.put("id", expert.getId());
                            expertChildren.put("mobile", expert.getMobile());
                            expertChildren.put("position", expert.getPosition());
                            expertListNode.add(expertChildren);
                        }
                        if(expertListNode.size()>0)
                        {
                            b=true;
                            expertFather.put("children", expertListNode);
                        }
                    }
                    if(b)
                    nodeList.add(expertFather);
                }
            }

            //应急队伍
            List<SupportTeam> teams = teamService.getTeamAndPerson();
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(teams)) {
                for (SupportTeam team : teams) {
                    if(deptname!=null&&!"".equals(deptname)&&team.getName().indexOf(deptname)==-1)
                        continue;
                    Map<String, Object> teamFather = new HashMap<>();
                    teamFather.put("name", team.getName());
                    teamFather.put("id", team.getId());
                    boolean b=false;
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(team.getDeptperson())) {
                        List<Map<String, Object>> childrenNode = new ArrayList<>();
                        for (SupportDeptPerson person : team.getDeptperson()) {
                            if(username!=null&&!"".equals(username)&&person.getName().indexOf(username)==-1)
                                continue;
                            Map<String, Object> teamChildren = new HashMap<>();
                            teamChildren.put("name", person.getName());
                            teamChildren.put("id", person.getId());
                            teamChildren.put("mobile", person.getMobile());
                            teamChildren.put("position", person.getPosition());
                            childrenNode.add(teamChildren);
                        }
                        if(childrenNode.size()>0) {
                            b = true;
                            teamFather.put("children", childrenNode);
                        }
                    }
                    if(b)
                    nodeList.add(teamFather);
                }
            }

            //群组
            List<AddressGroupDO> groups = addressGroupService.getGroupAndPerson();
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(groups)) {
                for (AddressGroupDO group : groups) {
                    if(deptname!=null&&!"".equals(deptname)&&group.getName().indexOf(deptname)==-1)
                        continue;
                    Map<String, Object> groupFather = new HashMap<>();
                    groupFather.put("name", group.getName());
                    groupFather.put("id", group.getId());
                    boolean b=false;
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(group.getDeptperson())) {
                        List<Map<String, Object>> childrenNode = new ArrayList<>();
                        for (SupportDeptPerson person : group.getDeptperson()) {
                            if(username!=null&&!"".equals(username)&&person.getName().indexOf(username)==-1)
                                continue;
                            Map<String, Object> groupChildren = new HashMap<>();
                            groupChildren.put("name", person.getName());
                            groupChildren.put("id", person.getId());
                            groupChildren.put("mobile", person.getMobile());
                            groupChildren.put("position", person.getPosition());
                            childrenNode.add(groupChildren);
                        }
                        if(childrenNode.size()>0) {
                            b = true;
                            groupFather.put("children", childrenNode);
                        }
                    }
                    if(b)
                    nodeList.add(groupFather);
                }
            }
            returnMap.put("nodes", nodeList);
            return ResponseEntity.ok(R.ok().put("data",returnMap));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }

    /**
     * 发送短信
     * @param params mobiles 手机号 ：群发时，多个手机号，用逗号隔开）
     * @param params content 短信内容
     * @return
     */
    @RequestMapping("/smsSend")
    public ResponseEntity smsSend(@RequestParam Map<String, Object> params) {
        if(ShiroUtils.getUser()==null){
            return ResponseEntity.ok(R.error(100,"登录超时，重新登录！"));
        }
        String mobiles = params.get("mobiles").toString();
        String content = params.get("content").toString();
        if(com.bootdo.common.utils.StringUtils.isBlank(mobiles) || com.bootdo.common.utils.StringUtils.isBlank(content) ){
            return ResponseEntity.ok(R.error("手机号或短信内容 不能为空"));
        }
        String result;
        try{
            result = sendRecordService.sendSMS(mobiles, content, null);
            if(result == null || "99999999".equals(result) ||"0".equals(result)){
                return ResponseEntity.ok(R.error("短信发送失败"));
            }
            return ResponseEntity.ok(R.ok("短信发送成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(R.error(e.getMessage()));
        }
    }
}
