package com.bootdo.actionprogramManage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.actionprogramManage.dao.*;
import com.bootdo.actionprogramManage.domain.*;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.archive.dao.*;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.service.CommonFileService;
import com.bootdo.common.utils.HttpClientUtil;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.planManage.dao.*;
import com.bootdo.planManage.domain.*;
import com.bootdo.support.dao.*;
import com.bootdo.support.dto.ReceiveInfoDTO;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class DispatchActionprogramMainServiceImpl implements DispatchActionprogramMainService {
	@Autowired
	private DispatchActionprogramMainDao dispatchActionprogramMainDao;
	@Autowired
	private DispatchPlanMainDao dispatchPlanMainDao;
	@Autowired
	private DispatchRespdeptDao dispatchRespdeptDao;
	@Autowired
	private DispatchTaskDao dispatchTaskDao;
	@Autowired
	private DispatchTaskService dispatchTaskService ;
	@Autowired
	private ReceiveInfoMapper receiveInfoDao;
	@Autowired
	private SupportDeptDao supportDeptDao;
	@Autowired
	private PlanAccidentTypeDao planAccidentTypeDao;
	@Autowired
	private DispatchEarlywarnDao dispatchEarlywarnDao;
	@Autowired
	private PlanMainDao planMainDao;
	@Autowired
	private PlanTaskDao planTaskDao;
	@Autowired
	private PlanRespDeptDao planRespDeptDao;
	@Autowired
	private DispatchLogDao dispatchLogDao;
	@Autowired
	private DeptPersonMapper deptPersonDao;
	@Autowired
	private TeamMapper teamDao;
	@Autowired
	private ExpertInfoMapper expertInfoDao;
	@Autowired
	private PlanEarlywarnTypeDao planEarlywarnTypeDao;
	@Autowired
	private DispatchTaskFeedbackDao dispatchTaskFeedbackDao;
	@Autowired
	private DispatchTaskFeedbackDetailDao dispatchTaskFeedbackDetailDao;
	@Autowired
	private BaseEventService baseEventService;
	@Autowired
	private ArchiveActionprogramMainDao actionprogramMainDao;
	@Autowired
	private ArchiveEarlywarnDao archiveEarlywarnDao;
	@Autowired
	private ArchiveLogDao archiveLogDao;
	@Autowired
	private ArchivePlanmainDao archivePlanmainDao;
	@Autowired
	private ArchiveRespdeptDao archiveRespdeptDao;
	@Autowired
	private ArchiveTaskFeedbackDao archiveTaskFeedbackDao;
	@Autowired
	private ArchiveTaskDao archiveTaskDao;
	@Autowired
	private ArchiveTaskFeedbackDetailDao archiveTaskFeedbackDetailDao;
	@Autowired
	private ArchiveWebcamDao archiveWebcamDao;
	@Autowired
	private DispatchWebcamDao dispatchWebcamDao;     
	@Autowired
	private PlanEarlywarnLevelDao planEarlywarnLevelDao;
	@Autowired
	private ReceiveinfoService receiveinfoService;
	@Autowired
	private CommonFileService commonFileService;
	@Autowired
	private AsyncActionDetailService asyncActionDetailService;

	private static final Logger logger = LoggerFactory.getLogger(DispatchActionprogramMainService.class);
	
	public DispatchActionprogramMainDO get(String id){
		return dispatchActionprogramMainDao.get(id);
	}
	
	public List<DispatchActionprogramMainDO> list(Map<String, Object> map){
		return dispatchActionprogramMainDao.list(map);
	}
	
	public int count(Map<String, Object> map){
		return dispatchActionprogramMainDao.count(map);
	}
	
	@Transactional(readOnly = false)
	public int save(DispatchActionprogramMainDO dispatchActionprogramMain){
		return dispatchActionprogramMainDao.save(dispatchActionprogramMain);
	}
	
	@Transactional(readOnly = false)
	public int update(DispatchActionprogramMainDO dispatchActionprogramMain){
		return dispatchActionprogramMainDao.update(dispatchActionprogramMain);
	}
	
	@Transactional(readOnly = false)
	public void updateProgram(String actionprogramMain,String planMain,String respdept,String task){
		Long userId = ShiroUtils.getUser().getUserId();
		Date dateTime = new Date() ;
		String actionprogramMainId = null;
		String planmainId = null;
		
		//方案
		DispatchActionprogramMainDO dispatchActionprogramMain = JSON.parseObject(actionprogramMain, DispatchActionprogramMainDO.class);
		actionprogramMainId = dispatchActionprogramMain.getId();
		dispatchActionprogramMain.setUpdateBy(userId);
		dispatchActionprogramMain.setUpdateDate(dateTime);
		dispatchActionprogramMainDao.update(dispatchActionprogramMain);
		
		//预案信息
		DispatchPlanMainDO dispatchPlanMainDO = new DispatchPlanMainDO();
		if(planMain !=null &&!"".equals(planMain)){
			dispatchPlanMainDO = JSON.parseObject(planMain, DispatchPlanMainDO.class);
			planmainId = dispatchPlanMainDO.getId();
			dispatchPlanMainDO.setUpdateBy(userId);
			dispatchPlanMainDO.setUpdateDate(dateTime);
			dispatchPlanMainDao.update(dispatchPlanMainDO);
			
			//响应部门
			if(respdept!=null &&!"".equals(respdept) ) {
				List<Map<String,Object>> respDeptList = JSON.parseObject(respdept, List.class);
				String respDeptId = null;
				for (Map<String, Object> map : respDeptList) {
					DispatchRespdeptDO dispatchRespdeptDO = JSON.parseObject(JSON.toJSONString(map), DispatchRespdeptDO.class);
					respDeptId = dispatchRespdeptDO.getId();
					if(dispatchRespdeptDao.get(respDeptId) != null){
						dispatchRespdeptDO.setUpdateBy(userId);
						dispatchRespdeptDO.setUpdateDate(dateTime);
						dispatchRespdeptDao.update(dispatchRespdeptDO);
					}else{
						dispatchRespdeptDO.setId(UUID.randomUUID().toString().replace("-",""));
						dispatchRespdeptDO.setActionprogramId(actionprogramMainId);
						dispatchRespdeptDO.setDispatchPlanmainId(planmainId);
						dispatchRespdeptDO.setCreateBy(userId);
						dispatchRespdeptDO.setCreateDate(dateTime);
						dispatchRespdeptDO.setUpdateBy(userId);
						dispatchRespdeptDO.setUpdateDate(dateTime);
						dispatchRespdeptDao.save(dispatchRespdeptDO);
					}
				}
			}
			
			//任务
			if(task!=null &&!"".equals(task) ) {
				List<Map<String,Object>> taskList = JSON.parseObject(task, List.class);
				for (Map<String, Object> map : taskList) {
					DispatchTaskDO dispatchTaskDO = JSON.parseObject(JSON.toJSONString(map), DispatchTaskDO.class);
					String taskId = dispatchTaskDO.getId();
					if(dispatchTaskDao.get(taskId) != null){
						dispatchTaskDO.setUpdateBy(userId);
						dispatchTaskDO.setUpdateDate(dateTime);
						dispatchTaskDao.update(dispatchTaskDO);
					}else{
						dispatchTaskDO.setId(UUID.randomUUID().toString().replace("-",""));
						dispatchTaskDO.setActionprogramId(actionprogramMainId);
						dispatchTaskDO.setDispatchPlanmainId(planmainId);
						dispatchTaskDO.setCreateBy(userId);
						dispatchTaskDO.setCreateDate(dateTime);
						dispatchTaskDO.setUpdateBy(userId);
						dispatchTaskDO.setUpdateDate(dateTime);
						dispatchTaskDao.save(dispatchTaskDO);
					}
				}
			}
		}
		
	}
	
	
	// 生成方案
	@Transactional(readOnly = false, propagation = Propagation.NESTED)
	public String productProgram(String receiveinfoId ,String planmainId) {
		try {
			if (StringUtils.isNotEmpty(receiveinfoId) || StringUtils.isNotEmpty(planmainId)) {
				String planmainName = null;
				String eventdesc = null;
				//-----------插入方案信息-----------
				DispatchActionprogramMainDO dispatchActionprogramMainDO = new DispatchActionprogramMainDO();
				String actionprogramId = UUID.randomUUID().toString().replace("-", ""); // dispatch_actionprogram_main表的id
//				Long userId = ShiroUtils.getUser().getUserId();
				Long userId = 1L;
				Date now = new Date();
				String code = getCode();
				dispatchActionprogramMainDO.setId(actionprogramId);
				dispatchActionprogramMainDO.setCode(code);
				dispatchActionprogramMainDO.setStatus(1);
				dispatchActionprogramMainDO.setActionDate(now);
				dispatchActionprogramMainDO.setIsArchive(0);
				dispatchActionprogramMainDO.setCreateBy(userId);
				dispatchActionprogramMainDO.setCreateDate(now);
				dispatchActionprogramMainDao.save(dispatchActionprogramMainDO);

				//-----------插入预警信息-----------
				DispatchEarlywarnDO dispatchEarlywarnDO = new DispatchEarlywarnDO();

				if (StringUtils.isNotEmpty(receiveinfoId)) {
					Map<String, Object> receiveInfoMap = receiveInfoDao.getUniqueById(receiveinfoId);
					if (receiveInfoMap != null) {
						ReceiveInfoDTO receiveInfoDTO = JSON.parseObject(JSON.toJSONString(receiveInfoMap), ReceiveInfoDTO.class);
						dispatchEarlywarnDO.setId(receiveInfoDTO.getId());
						//应急执行方案表ID
						dispatchEarlywarnDO.setActionprogramId(actionprogramId);
						//上报人姓名
						dispatchEarlywarnDO.setRepname(receiveInfoDTO.getRepname());
						//上报人联系电话
						dispatchEarlywarnDO.setRepphone(receiveInfoDTO.getRepphone());
						//性别
						dispatchEarlywarnDO.setSex(receiveInfoDTO.getSex());
						//事件地址
						dispatchEarlywarnDO.setEventaddr(receiveInfoDTO.getEventaddr());
						//事件描述
						dispatchEarlywarnDO.setEventdesc(receiveInfoDTO.getEventdesc());
						eventdesc = receiveInfoDTO.getEventdesc();
						//上报时间
						dispatchEarlywarnDO.setRepdate(receiveInfoDTO.getRepdate());
						//接报途径(电话、短信、APP、微信、各部门的报警信息、日常巡查（巡查上报）、物联网接入模块（超过预警阀值）[消防模块、水质监测、特殊人群手环、视频])
						//dispatchEarlywarnDO.setSourceName(ReceiveInfoDTO.getSourceName());
						//单位名称847961022
						Map<String, Object> supportDeptMap = supportDeptDao.getUniqueById(receiveInfoDTO.getDept_id());
						if (supportDeptMap != null && StringUtils.isNotEmpty((String) supportDeptMap.get("name"))) {
							dispatchEarlywarnDO.setDeptName((String) supportDeptMap.get("name"));
						}
						//事故类别名称
						if (StringUtils.isNotEmpty(receiveInfoDTO.getAccident_type_id())) {
							PlanAccidentTypeDO planAccidentTypeDO = planAccidentTypeDao.get(receiveInfoDTO.getAccident_type_id());
							if (planAccidentTypeDO != null && StringUtils.isNotEmpty(planAccidentTypeDO.getName())) {
								dispatchEarlywarnDO.setAccidentTypeName(planAccidentTypeDO.getName());
							}
						}
						//事件级别名称
						if (StringUtils.isNotEmpty(receiveInfoDTO.getEventlevel())) {
							PlanEarlywarnLevelDO planEarlywarnLevelDO = planEarlywarnLevelDao.get(receiveInfoDTO.getEventlevel());
							if (planEarlywarnLevelDO != null && StringUtils.isNotEmpty(planEarlywarnLevelDO.getName())) {
								dispatchEarlywarnDO.setEventlevelName(planEarlywarnLevelDO.getName());
							}
						}
						//备注
						dispatchEarlywarnDO.setRemarks(receiveInfoDTO.getRemarks());
						//接收人
						dispatchEarlywarnDO.setDeptpersonId(receiveInfoDTO.getDeptperson_id());
						//记录录入人
						dispatchEarlywarnDO.setCreateBy(userId);
						//记录录入时间
						dispatchEarlywarnDO.setCreateDate(now);

						//事件经纬度
						dispatchEarlywarnDO.setLatLon(receiveInfoDTO.getLat_lon());
						//是否推送到应急指挥调度平台(0未推送；5已推送)
						dispatchEarlywarnDO.setIspush(receiveInfoDTO.getIspush());
						//预警类型名称
						if (StringUtils.isNotEmpty(receiveInfoDTO.getEarlywarn_id())) {
							PlanEarlywarnTypeDO planEarlywarnTypeDO = planEarlywarnTypeDao.get(receiveInfoDTO.getEarlywarn_id());
							if (planEarlywarnTypeDO != null && StringUtils.isNotEmpty(planEarlywarnTypeDO.getName())) {
								dispatchEarlywarnDO.setEarlywarnTypeName(planEarlywarnTypeDO.getName());
							}
						}

						//接报信息是否受理（0 否 1是）
						if (receiveInfoDTO.getIs_acceptance() != null) {
							dispatchEarlywarnDO.setIsAcceptance(Integer.parseInt(receiveInfoDTO.getIs_acceptance()));
						}
						//值守人员受理时间
						//dispatchEarlywarnDO.setAcceptanceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(receiveInfoDTO.getAcceptance_time(),new ParsePosition(8)));
						dispatchEarlywarnDO.setAcceptanceTime(receiveInfoDTO.getAcceptance_time());
						//值守人员的受理方式：上报1、推送5、终结10
						if (receiveInfoDTO.getAcceptance_type() != null) {
							dispatchEarlywarnDO.setAcceptanceType(Integer.parseInt(receiveInfoDTO.getAcceptance_type()));
						}
						//上报审批状态：上报审批中1、推送(同意)5、终结(不同意)10
						if (receiveInfoDTO.getExamine_type() != null) {
							dispatchEarlywarnDO.setExamineType(Integer.parseInt(receiveInfoDTO.getExamine_type()));
						}
						//上报的审批意见
						dispatchEarlywarnDO.setExamineOpinion(receiveInfoDTO.getExamine_opinion());
						//审批时间
						//dispatchEarlywarnDO.setExamineTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(receiveInfoDTO.getExamine_time(),new ParsePosition(8)));
						dispatchEarlywarnDO.setExamineTime(receiveInfoDTO.getExamine_time());
						//接收人的来源类型 1部门 2值班
						if (receiveInfoDTO.getExaminer_type() != null) {
							dispatchEarlywarnDO.setExaminerType(Integer.parseInt(receiveInfoDTO.getExaminer_type()));
						}
						dispatchEarlywarnDao.save(dispatchEarlywarnDO);
					} else {
						logger.error("预警信息没有记录");
						throw new Exception("预警信息没有记录");
					}
				}

				//-----------插入方案管理的预案、响应部门、响应任务-----------

				PlanMainDO planMainDO = new PlanMainDO();
				List<PlanRespDeptDO> planRespDeptDOList = new ArrayList<PlanRespDeptDO>();
				List<PlanTaskDO> planTaskList = new ArrayList<PlanTaskDO>();
				;
				if (StringUtils.isNotEmpty(planmainId)) {
					planMainDO = planMainDao.get(planmainId);
					if (planMainDO != null && StringUtils.isNotEmpty(planMainDO.getId())) {
						//改变预案的使用次数
						int planMainUseTime = (planMainDO.getUseTime() != null ? planMainDO.getUseTime() : 0) + 1;
						planMainDao.updatePlanMainUseTime(planMainUseTime, planMainDO.getId());

						DispatchPlanMainDO dispatchPlanmainDO = new DispatchPlanMainDO();
						String dispatchPlanmainId = UUID.randomUUID().toString().replace("-", "");
						dispatchPlanmainDO.setId(dispatchPlanmainId);

						dispatchPlanmainDO.setPlanmainId(planMainDO.getId());
						//应急执行方案表ID
						dispatchPlanmainDO.setActionprogramId(actionprogramId);
						//事故类型
						dispatchPlanmainDO.setAccidentTypeName(planMainDO.getAccidentTypeName());
						//预警类别
						dispatchPlanmainDO.setEarlywarnTypeName(planMainDO.getEarlywarnTypeName());
						//预警级别
						dispatchPlanmainDO.setEarlywarnLevelName(planMainDO.getEarlywarnLevelName());
						//责任部门名称
						dispatchPlanmainDO.setDutyDeptName(planMainDO.getDutyDeptName());
						//预案名称
						dispatchPlanmainDO.setName(planMainDO.getName());
						planmainName = planMainDO.getName();
						//预案编码
						dispatchPlanmainDO.setCode(planMainDO.getCode());
						//启动条件
						dispatchPlanmainDO.setStartCondition(planMainDO.getStartCondition());
						//预警报告单位
						dispatchPlanmainDO.setReprotDeptName(planMainDO.getReprotDeptName());
						//启动部门 名称
						dispatchPlanmainDO.setStartDeptName(planMainDO.getStartDeptName());
						//是否启用
						dispatchPlanmainDO.setEnabled(planMainDO.getEnabled());
						//使用次数
						dispatchPlanmainDO.setUseTime((planMainDO.getUseTime() != null ? planMainDO.getUseTime() : 0) + 1);
						//备注
						dispatchPlanmainDO.setRemarks(planMainDO.getRemarks());
						//记录录入人
						dispatchPlanmainDO.setCreateBy(userId);
						//记录录入时间
						dispatchPlanmainDO.setCreateDate(now);
						dispatchPlanMainDao.save(dispatchPlanmainDO);

						// 用planMainDO的id获取到对应的任务
						planTaskList = planTaskDao.getByPlanId(planmainId);
						if (planTaskList != null) {
							DispatchTaskDO dispatchTaskDO;
							for (PlanTaskDO planTask : planTaskList) { // 遍历任务集合，把每条任务插入到dispatch_task表中
								dispatchTaskDO = new DispatchTaskDO();
								String dispatchTaskId = UUID.randomUUID().toString().replace("-", "");
								dispatchTaskDO.setId(dispatchTaskId);
								planTask.setId(dispatchTaskId); //将planTask的id改为dispatchTaskDO的id，用于发短信时，记录管理表id

								//方案表ID
								dispatchTaskDO.setActionprogramId(actionprogramId);
								//执行方案的预案主表ID
								dispatchTaskDO.setDispatchPlanmainId(dispatchPlanmainId);
								//任务名称
								dispatchTaskDO.setName(planTask.getName());
								//任务内容
								dispatchTaskDO.setContent(planTask.getContent());
								//接收任务的对象类型(个人、应急队伍、部门)
								dispatchTaskDO.setType(planTask.getType());
								//负责对象的ID
								dispatchTaskDO.setLiabilityId(planTask.getLiabilityId());
								//任务是否安排
								dispatchTaskDO.setIsarrange(0);
								//现场的任务状态(已接收到任务、开始执行中、执行遇到困难、执行完成)
								dispatchTaskDO.setActionStatus(1);
								//是否逻辑删除
								dispatchTaskDO.setIsDel(0);
								//联系电话
								dispatchTaskDO.setMobile(planTask.getMobile());
								//记录录入人
								dispatchTaskDO.setCreateBy(userId);
								//记录录入时间
								dispatchTaskDO.setCreateDate(now);
								dispatchTaskDO.setSortNo(planTask.getSortNo());
								dispatchTaskDao.save(dispatchTaskDO);
							}
						} else {
							logger.error("任务表没有记录！");
							throw new Exception("任务表没有记录！");
						}

						// 用planMainDO的id获取到对应的响应部门
						planRespDeptDOList = planRespDeptDao.getByPlanId(planmainId);
						if (planRespDeptDOList != null) {
							DispatchRespdeptDO dispatchRespdeptDO;
							for (PlanRespDeptDO planRespDeptDO : planRespDeptDOList) {  // 遍历响应部门集合，把每条响应部门插入到dispatch_dept表中
								dispatchRespdeptDO = new DispatchRespdeptDO();
								String respdeptId = UUID.randomUUID().toString().replace("-", "");
								dispatchRespdeptDO.setId(respdeptId);
								planRespDeptDO.setId(respdeptId); //将planRespDeptDO的id改为dispatchRespdeptDO的id，用于发短信时，记录管理表id
								//方案表ID
								dispatchRespdeptDO.setActionprogramId(actionprogramId);
								//执行方案的预案主表ID
								dispatchRespdeptDO.setDispatchPlanmainId(dispatchPlanmainId);
								//部门名称
								dispatchRespdeptDO.setDeptName(planRespDeptDO.getDeptName());
								//响应工作内容
								dispatchRespdeptDO.setContent(planRespDeptDO.getContent());
								//负责人姓名
								dispatchRespdeptDO.setLiabilityMan(planRespDeptDO.getLiabilityMan());
								//联系电话
								dispatchRespdeptDO.setMobile(planRespDeptDO.getMobile());
								//任务是否安排
								dispatchRespdeptDO.setIsArrange(0);
								//现场的任务状态(已接收到任务、开始执行中、执行遇到困难、执行完成)
								dispatchRespdeptDO.setActionStatus(1);
								//是否逻辑删除
								dispatchRespdeptDO.setIsDel(0);
								//记录录入人
								dispatchRespdeptDO.setCreateBy(userId);
								//记录录入时间
								dispatchRespdeptDO.setCreateDate(now);
								dispatchRespdeptDO.setSortNo(planRespDeptDO.getSortNo());
								dispatchRespdeptDao.save(dispatchRespdeptDO);
							}
						} else {
							logger.error("响应部门没有记录！");
							throw new Exception("响应部门没有记录！");
						}
					} else {
						logger.error("预案表没有记录！");
						throw new Exception("预案表没有记录！");
					}
					pushWarn(dispatchEarlywarnDO, planMainDO);
				}

				// 在dispatch_log表中插入记录
				String content = code + "方案已生成";
				String summary = "生成方案成功";
				insertDispatchLog(actionprogramId, now, String.valueOf(userId), 1, content, summary);

				// 执行方案
				actionProgram(planRespDeptDOList, planTaskList, actionprogramId, now, receiveinfoId, eventdesc, planmainName, planmainId); // 方案在生成后就自动执行。

				return actionprogramId;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
		
	// 方案的执行
	public void actionProgram(List<PlanRespDeptDO> planRespDeptDOList, List<PlanTaskDO> planTaskList, String actionprogramId,Date now,String earlywarnId,String earlywarnEventdesc,String planMainName,String planmainId) { // id为应急执行方案表ID
		// 方案自动进行执行,发送短信到相关人员手机上，以及任务到他们手机上。
		if (StringUtils.isNotEmpty(actionprogramId)) {
			String liabilityId; // 任务的负责人Id
			String mobile =""; // 要发送的手机号码
			String liabilityMan =""; // 存储名称（负责人）
			String content =""; // 内容
			String respDeptName =""; // 响应部门名称
			String tasktName =""; // 响应任务名称
			Map<String,Object> supportDeptMap ;
			Long userId = ShiroUtils.getUser().getUserId();

			String associationTableId =""; //关联表的id


			if(planRespDeptDOList !=null && planRespDeptDOList.size()>0){
				for (PlanRespDeptDO planRespDeptDO : planRespDeptDOList) {
					supportDeptMap =  supportDeptDao.getUniqueById(planRespDeptDO.getDeptId());
					if(supportDeptMap !=null){
						respDeptName = (String)supportDeptMap.get("name"); //单位联系人
					}

					liabilityMan = planRespDeptDO.getLiabilityMan();
					mobile = planRespDeptDO.getMobile();
					content = planRespDeptDO.getContent();
					associationTableId = planRespDeptDO.getId();

					//拆分电话-发送短信
					if(StringUtils.isNotEmpty(mobile)){
						String [] mobiles=mobile.split("[,\\，]");
						if(mobiles !=null && mobiles.length>0){
							for(String mob:mobiles){
								respDeptActionDetailLost(mob,content,actionprogramId,associationTableId,liabilityMan,now, String.valueOf(userId),respDeptName,1,earlywarnId,earlywarnEventdesc,planMainName,planmainId);
							}
						}
					}
				}
			}
			if(planTaskList !=null && planTaskList.size()>0){
				for (PlanTaskDO planTask : planTaskList) {
					tasktName = planTask.getName();
					liabilityId = planTask.getLiabilityId();
					mobile = planTask.getMobile();
					Integer type = planTask.getType();
					content = planTask.getContent();
					associationTableId = planTask.getId();

					//拆分电话-发送短信
					if(StringUtils.isNotEmpty(mobile)){
						String [] mobiles=mobile.split("[,\\，]");
						if(mobiles !=null && mobiles.length>0){
							for(String mob:mobiles){
								taskActionDetailLost(liabilityId,type,actionprogramId,now,String.valueOf(userId),mob ,content,associationTableId,tasktName,1,earlywarnId,earlywarnEventdesc,planMainName,planmainId);
							}
						}
					}
				}
			}
		}
		
		// 有任务和响应部门的手机要发送手机短信。
		// 确保发送成功，要全部成功。哪一个不成功，则那一个就进行重新发送。 看这里应该要怎么样弄!!!
		// 日志表记录系统自动发了哪些短信。

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
	
	//生成方案编码
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
	
	@Transactional(readOnly = false)
	public int remove(String id){
		return dispatchActionprogramMainDao.remove(id);
	}
	
	@Transactional(readOnly = false)
	public int batchRemove(String[] ids){
		return dispatchActionprogramMainDao.batchRemove(ids);
	}

	public List<Map<String, Object>> getTaskAndRespDept(String actionProgramId) {
		List<Map<String, Object>> dispatchTaskList = dispatchTaskDao.getByActionprogramId(actionProgramId);
		List<Map<String, Object>> dispatchRespdeptList = dispatchRespdeptDao.getByActionprogramId(actionProgramId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(dispatchRespdeptList !=null){
			list.addAll(dispatchRespdeptList);
		}
		if(dispatchTaskList !=null){
			list.addAll(dispatchTaskList);
		}
		if(list !=null && list.size()>0){
			String feedbackContent = "";
			String feedbackFile="";
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String entityId = (String) map.get("id");
				Map<String,Object>paramsMap=new HashMap<String, Object>();
				paramsMap.put("relationId", entityId);
				List<DispatchTaskFeedbackDO> dispatchTaskFeedbackDO = dispatchTaskFeedbackDao.list(paramsMap);
				if(dispatchTaskFeedbackDO !=null&&dispatchTaskFeedbackDO.size()>0){
					Map<String, Object> feedbackDetailMap = new HashMap<String, Object>();
					feedbackDetailMap.put("feedbackId", dispatchTaskFeedbackDO.get(0).getId());
					List<CommonFileDO>commonFileList=commonFileService.list(paramsMap);
					List<DispatchTaskFeedbackDetailDO> feedbackDetailList = dispatchTaskFeedbackDetailDao.list(feedbackDetailMap);
					//附件预览地址
					if(commonFileList !=null && commonFileList.size()>0){
						feedbackFile = "";
						for (CommonFileDO commonFileDO : commonFileList) {
							feedbackFile += ";"+commonFileDO.getFileUrl();
						}
						feedbackFile.substring(1,feedbackFile.length());
						map.put("feedbackFilePath", feedbackFile);

					}else {
						map.put("feedbackFilePath", "");

					}
					if(feedbackDetailList !=null && feedbackDetailList.size()>0){
						feedbackContent = "";
						for (DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetailDO : feedbackDetailList) {
							feedbackContent += dispatchTaskFeedbackDetailDO.getContent()+" \n ";
						}
						map.put("feedbackContent", feedbackContent);
					}else{
						map.put("feedbackContent", "");
					}
				}else{
					map.put("feedbackContent", "");
				}
			}
		}
		return list;
	}

	@Transactional(readOnly = false)
	public int addTaskORRespDept(String entityJsonStr) {
		Long userId = ShiroUtils.getUser().getUserId();
		Date now = new Date() ;
		JSONObject entityJson = JSONObject.parseObject(entityJsonStr); 
		if("task".equals(entityJson.getString("flag"))){
			DispatchTaskDO dispatchTaskDO = JSON.parseObject(entityJsonStr, DispatchTaskDO.class);
			String dispatchTaskId = UUID.randomUUID().toString().replace("-","");
			dispatchTaskDO.setId(dispatchTaskId);
			dispatchTaskDO.setCreateBy(userId);
			dispatchTaskDO.setCreateDate(now);
			dispatchTaskDO.setTaskType(2);
			int row = dispatchTaskDao.save(dispatchTaskDO);
			if(row >0){
				// 任务发送短信和插入日记表
				String liabilityId = dispatchTaskDO.getLiabilityId();
				String actionprogramId = dispatchTaskDO.getActionprogramId();
				String content = dispatchTaskDO.getContent();
				Integer type = dispatchTaskDO.getType();
				String mobile = dispatchTaskDO.getMobile();
				String name = dispatchTaskDO.getName();


				taskActionDetail(liabilityId,type,actionprogramId,now,String.valueOf(userId), mobile,content,dispatchTaskId,name,2);
				return row;
			}
		}else if("respdept".equals(entityJson.getString("flag"))){
			DispatchRespdeptDO dispatchRespdeptDO = JSON.parseObject(entityJsonStr, DispatchRespdeptDO.class);
			String dispatchRespdeptId = UUID.randomUUID().toString().replace("-","");
			dispatchRespdeptDO.setId(dispatchRespdeptId);
			dispatchRespdeptDO.setCreateBy(userId);
			dispatchRespdeptDO.setCreateDate(now);
			dispatchRespdeptDO.setTaskType(2);
			int row = dispatchRespdeptDao.save(dispatchRespdeptDO);
			if(row >0){
				//响应部门发送短信和插入日记表
				String liabilityMan = dispatchRespdeptDO.getLiabilityMan();
				String mobile = dispatchRespdeptDO.getMobile();
				String content = dispatchRespdeptDO.getContent();
				String actionprogramId = dispatchRespdeptDO.getActionprogramId();

				String dispatchRespdeptName = dispatchRespdeptDO.getDeptName();

				respDeptActionDetail(mobile,content,actionprogramId,dispatchRespdeptId,liabilityMan,now, String.valueOf(userId),dispatchRespdeptName,2);
				return row;
			}
		}
		return 0;
	}

	@Transactional(readOnly = false)
	public int logicDeleteTaskORRespDept(String entityId, String entityFlag) {
		Date now = new Date();
		String userId = String.valueOf(ShiroUtils.getUser().getUserId());
		if("task".equals(entityFlag)){
			DispatchTaskDO dispatchTaskDO = dispatchTaskDao.get(entityId);
			if(dispatchTaskDO !=null){
				int row = dispatchTaskDao.logicDelete(entityId);
				if(row >0){
					// 在dispatch_log表中插入记录
					String actionprogramId = dispatchTaskDO.getActionprogramId();
					String name = dispatchTaskDO.getName();

					String content = "方案的响应任务："+name+"，已撤销";
					String summary = "方案的响应任务："+name+"，撤销成功";
					insertDispatchLog(actionprogramId, now,userId, 0,content,summary);
					return row;
				}
			}
		}else if("respdept".equals(entityFlag)){
			DispatchRespdeptDO dispatchRespdeptDO = dispatchRespdeptDao.get(entityId);
			if(dispatchRespdeptDO !=null){
				int row = dispatchRespdeptDao.logicDelete(entityId);
				if(row >0){
					// 在dispatch_log表中插入记录
					String actionprogramId = dispatchRespdeptDO.getActionprogramId();
					String name = dispatchRespdeptDO.getDeptName();

					String content = "方案的响应部门"+name+"的任务，已撤销";
					String summary =  "方案的响应部门"+name+"的任务，撤销成功";
					insertDispatchLog(actionprogramId, now,userId, 0,content,summary);
					return row;
				}
			}
		}
		return 0;
	}

	@Transactional(readOnly = false)
	public int updateTaskORRespDept(String entityJsonStr) {
		Long userId = ShiroUtils.getUser().getUserId();
		Date now = new Date() ;
		JSONObject entityJson = JSONObject.parseObject(entityJsonStr); 
		if("task".equals(entityJson.getString("flag"))){
			DispatchTaskDO dispatchTaskDO = JSON.parseObject(entityJsonStr, DispatchTaskDO.class);
			dispatchTaskDO.setUpdateBy(userId);
			dispatchTaskDO.setUpdateDate(now);
			int row = dispatchTaskDao.update(dispatchTaskDO);
			if(row > 0){
				// 任务发送短信和插入日记表
				String liabilityId = dispatchTaskDO.getLiabilityId();
				String actionprogramId = dispatchTaskDO.getActionprogramId();
				String content = dispatchTaskDO.getContent();
				String dispatchTaskId = dispatchTaskDO.getId();
				Integer type = dispatchTaskDO.getType();
				String mobile = dispatchTaskDO.getMobile();

				String name = dispatchTaskDO.getName();

				taskActionDetail(liabilityId,type,actionprogramId,now,String.valueOf(userId), mobile,content,dispatchTaskId,name,2);
				return row;
			}
		}else if("respdept".equals(entityJson.getString("flag"))){
			DispatchRespdeptDO dispatchRespdeptDO = JSON.parseObject(entityJsonStr, DispatchRespdeptDO.class);
			dispatchRespdeptDO.setUpdateBy(userId);
			dispatchRespdeptDO.setUpdateDate(now);
			int row = dispatchRespdeptDao.update(dispatchRespdeptDO);
			String dispatchRespdeptId = dispatchRespdeptDO.getId();
			if(row > 0){
				// 响应部门发送短信和插入日记表
				String liabilityMan = dispatchRespdeptDO.getLiabilityMan();
				String mobile = dispatchRespdeptDO.getMobile();
				String content = dispatchRespdeptDO.getContent();
				String actionprogramId = dispatchRespdeptDO.getActionprogramId();

				String dispatchRespdeptName = dispatchRespdeptDO.getDeptName();

				respDeptActionDetail(mobile,content,actionprogramId,dispatchRespdeptId,liabilityMan,now, String.valueOf(userId),dispatchRespdeptName,2);
				return row;
			}
		}
		return 0;
	}

	@Transactional(readOnly = false)
	public int updateReceiveinfoAndEarlywarn(String receiveinfoId, String latLon, String eventaddr) {
//		Long userId = ShiroUtils.getUser().getUserId();
		Date now = new Date() ;
		
		Map<String, Object> receiveinfoMap = receiveInfoDao.getUniqueById(receiveinfoId);
		if(receiveinfoMap !=null){
			ReceiveInfo receiveinfo = JSON.parseObject(JSON.toJSONString(receiveinfoMap), ReceiveInfo.class);
			receiveinfo.setEventaddr(eventaddr); //事件地址
			receiveinfo.setLat_lon(latLon); //经纬度
//			receiveinfo.setUpdateBy(String.valueOf(userId));
			receiveinfo.setUpdateDate(now);
			
			DispatchEarlywarnDO dispatchEarlywarnDO = dispatchEarlywarnDao.get(receiveinfoId);
			if(dispatchEarlywarnDO !=null){
				dispatchEarlywarnDO = new DispatchEarlywarnDO();
				dispatchEarlywarnDO.setId(receiveinfoId);
				dispatchEarlywarnDO.setEventaddr(eventaddr); //事件地址
				dispatchEarlywarnDO.setLatLon(latLon); //经纬度
//				dispatchEarlywarnDO.setUpdateBy(userId);
				dispatchEarlywarnDO.setUpdateDate(now);
				dispatchEarlywarnDao.update(dispatchEarlywarnDO);
			}
			return receiveInfoDao.update(receiveinfo);
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public int closeCase(String actionprogramId) {
		int i=0;
		dispatchActionprogramMainDao.updateStatusByActionprogramId(actionprogramId);
		//修改信息接报状态为终结
		receiveinfoService.updateByActionProId(actionprogramId);
		
		//保存到归档表
		actionprogramMainDao.saveFromDispatch(actionprogramId);//方案
		archiveEarlywarnDao.saveFromDispatch(actionprogramId);
		archivePlanmainDao.saveFromDispatch(actionprogramId);
		archiveLogDao.saveFromDispatch(actionprogramId);
		archiveRespdeptDao.saveFromDispatch(actionprogramId);
		archiveTaskFeedbackDao.saveFromDispatchByRespdept(actionprogramId);
		archiveTaskFeedbackDao.saveFromDispatchByTask(actionprogramId);
		archiveTaskDao.saveFromDispatch(actionprogramId);
		archiveTaskFeedbackDetailDao.saveFromDispatchByRespdept(actionprogramId);
		archiveTaskFeedbackDetailDao.saveFromDispatchByTask(actionprogramId);
		archiveWebcamDao.saveFromDispatch(actionprogramId);
		//删除原表
		dispatchTaskDao.removeByActionprogramId(actionprogramId);
		dispatchEarlywarnDao.removeByActionprogramId(actionprogramId);
		dispatchTaskFeedbackDetailDao.removeByTask(actionprogramId);
		dispatchTaskFeedbackDetailDao.removeByRespdept(actionprogramId);
		dispatchTaskFeedbackDao.removeByRespdept(actionprogramId);
		dispatchTaskFeedbackDao.removeByTask(actionprogramId);
		dispatchLogDao.removeByActionprogramId(actionprogramId);
		dispatchRespdeptDao.removeByActionprogramId(actionprogramId);
		dispatchWebcamDao.removeByActionprogramId(actionprogramId);
		dispatchPlanMainDao.removeByActionprogramId(actionprogramId);
		dispatchActionprogramMainDao.removeByActionprogramId(actionprogramId);
		i=1;
		
		return i;
	}

	@Override
	public List<Map<String, Object>> getTaskAndRespDeptByParams(String actionProgramId, String name, String content, String liabilityMan) {
		List<Map<String, Object>> dispatchTaskList = dispatchTaskService.getTaskByParams(actionProgramId, name, content, liabilityMan);
		List<Map<String, Object>> dispatchRespdeptList = dispatchRespdeptDao.getRespdeptByParams(actionProgramId, name, content, liabilityMan);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(dispatchRespdeptList !=null){
			list.addAll(dispatchRespdeptList);
		}
		if(dispatchTaskList !=null){
			list.addAll(dispatchTaskList);
		}
		if(list !=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				StringBuffer feedbackContent = new StringBuffer("");
				StringBuffer feedbackFile = new StringBuffer("");
				Map<String, Object> map = list.get(i);
				String entityId = (String) map.get("id");
				Map<String, Object> feedbackMap = new HashMap<String, Object>();
				feedbackMap.put("relationId", entityId);
	
				DispatchTaskFeedbackDO dispatchTaskFeedback = dispatchTaskFeedbackDao.getUnique(entityId);
				if(dispatchTaskFeedback !=null){
					Map<String, Object> feedbackDetailMap = new HashMap<String, Object>();
				
						feedbackDetailMap.put("feedbackId", dispatchTaskFeedback.getId());
						List<DispatchTaskFeedbackDetailDO> feedbackDetailList = dispatchTaskFeedbackDetailDao.list(feedbackDetailMap);
						if(feedbackDetailList !=null && feedbackDetailList.size()>0){
							
								feedbackContent.append(feedbackDetailList.get(0).getContent());
							
						
					}	
		
				}
				map.put("feedbackContent", feedbackContent);
			}
		}
		System.out.println("list=="+list);
		return list;
	}
	
	
	public List<Map<String, Object>> getEventProcess() {
		List<Map<String, Object>> dispatchEarlywarnList = dispatchActionprogramMainDao.getEarlywarnInfo();
		if(dispatchEarlywarnList !=null && dispatchEarlywarnList.size()>0){
			for (int j = 0; j < dispatchEarlywarnList.size(); j++) {
				Map<String, Object> dispatchEarlywarnMap = dispatchEarlywarnList.get(j);
				String actionprogramId = (String) dispatchEarlywarnMap.get("actionprogramId");
				
				List<Map<String, Object>> dispatchTaskList = dispatchTaskDao.getByActionprogramId(actionprogramId);
				List<Map<String, Object>> dispatchRespdeptList = dispatchRespdeptDao.getByActionprogramId(actionprogramId);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if(dispatchTaskList !=null){
					list.addAll(dispatchTaskList);
				}
				if(dispatchRespdeptList !=null){
					list.addAll(dispatchRespdeptList);
				}
				if(list !=null && list.size()>0){
					StringBuffer feedbackContent = new StringBuffer("");
					for (int i = 0; i < list.size(); i++) {
						String entityId = (String) list.get(i).get("id");
						Map<String,Object>paramsMap=new HashMap<String, Object>();
						paramsMap.put("relationId", entityId);
						List<DispatchTaskFeedbackDO> dispatchTaskFeedbackList = dispatchTaskFeedbackDao.list(paramsMap);
						if(dispatchTaskFeedbackList !=null && dispatchTaskFeedbackList.size()>0){
							Map<String, Object> feedbackDetailMap = new HashMap<String, Object>();
							for (DispatchTaskFeedbackDO dispatchTaskFeedbackDO : dispatchTaskFeedbackList) {
								feedbackDetailMap.put("feedbackId", dispatchTaskFeedbackDO.getId());
								List<DispatchTaskFeedbackDetailDO> feedbackDetailList = dispatchTaskFeedbackDetailDao.list(feedbackDetailMap);
								if(feedbackDetailList !=null && feedbackDetailList.size()>0){
									for (DispatchTaskFeedbackDetailDO dispatchTaskFeedbackDetailDO : feedbackDetailList) {
										feedbackContent.append(dispatchTaskFeedbackDetailDO.getContent());
										feedbackContent.append("\n");
									}
								}
							}
						}
					}
					dispatchEarlywarnMap.put("feedbackContent", feedbackContent);
				}
			}
		}
		return dispatchEarlywarnList;
	}
	
	
	//推送预案信息到大屏
	public  void pushWarn(DispatchEarlywarnDO dispatchEarlywarnDO,PlanMainDO planMainDO) {
		logger.info("进入推送预案到宣教方法" );
		String url="http://172.27.162.36:8280/pushServer/push";
//		String userList="022-5e89ee7b72cae85a"; // 设备编号
		String userList="022-7a0ce80417c9a3f3"; // 池田村的设备编号
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		JSONObject JsonObj=new JSONObject();

//		List<Map<String,Object>> messageList=new ArrayList<Map<String,Object>>();
		Map<String,Object> messageMap=new HashMap<String, Object>();
		JSONObject messageMap2=new JSONObject();
		messageMap.put("title1", planMainDO.getAccidentTypeName());
		messageMap.put("title2", "正果镇人民政府");
		messageMap.put("content1", sdf.format(dispatchEarlywarnDO.getRepdate())+","+dispatchEarlywarnDO.getEventaddr()+dispatchEarlywarnDO.getEventdesc()+"现已启动"+planMainDO.getName() );
		messageMap.put("content2",sdf.format(new Date()));
		messageMap2.put(dispatchEarlywarnDO.getEarlywarnTypeName(), messageMap);
//		messageList.add(messageMap2);
		JsonObj.put("userList", userList);
		
//		Warning warn=new Warning();
//		warn.setTitle1(planMainDO.getAccidentTypeName());
//		warn.setTitle2("正果镇人民政府  "+sdf.format(new Date()));
//		warn.setContent(sdf.format(dispatchEarlywarnDO.getRepdate())+","+dispatchEarlywarnDO.getEventaddr()+dispatchEarlywarnDO.getEventdesc()+"现已启动"+planMainDO.getName());
//		warn.setContent2("");
//		warn.setIcon("");
//		warn.setTyphoonContent("");
//		warn.setTyphoonTitle("");
//		warn.setVideo("");

		JsonObj.put("uri","WarningMap");
		JsonObj.put("title", "");
		JsonObj.put("message", messageMap2);
		JsonObj.put("save", "save");
		
		try {
			String res= HttpClientUtil.httpPostRequest(url, JsonObj);
			System.out.println("推送到宣教方法成功" + res);
			logger.warn("推送到宣教方法成功{}",res );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("推送预案到宣教失败{}",e.getMessage());
			System.out.println("推送预案到宣教失败" + e.getMessage());
			e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
		DispatchEarlywarnDO dispatchEarlywarnDO=new DispatchEarlywarnDO();
		dispatchEarlywarnDO.setRepdate(new Date());
		dispatchEarlywarnDO.setEarlywarnTypeName("干旱");
		dispatchEarlywarnDO.setEventaddr("测试");
		dispatchEarlywarnDO.setEventdesc("测试事件");
		PlanMainDO planMainDO=new PlanMainDO();
		planMainDO.setName("测试1");
		planMainDO.setAccidentTypeName("三防预警");

		DispatchActionprogramMainServiceImpl d = new DispatchActionprogramMainServiceImpl();
		d.pushWarn(dispatchEarlywarnDO,planMainDO);
	}

	@Override
	public List<Map<String, Object>> getTimeAxisData(String actionprogramId) {
		// TODO Auto-generated method stub
		
		return dispatchActionprogramMainDao.getTimeAxisData(actionprogramId);
	}

	@Override
	public List<Map<String, Object>> getArchivelog(String actionprogramId) {
		// TODO Auto-generated method stub
		return dispatchActionprogramMainDao.getArchivelog(actionprogramId);
	}

	@Override
	public List<Map<String, Object>> getAllName() {
		return dispatchActionprogramMainDao.getAllName();
	}

	@Override
	public List<Map<String, Object>> getLastNameForSms() {
		return dispatchActionprogramMainDao.getLastNameForSms();
	}

	private void respDeptActionDetailLost(String mobile,String content,String actionprogramId,String dispatchRespdeptId,String liabilityMan, Date now, String userId,String dispatchRespdeptName , int type,String earlywarnId,String earlywarnEventdesc,String planMainName,String planmainId) {
		if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){


			// 发送短信(mobile); // 看要不要弄成群发
//            String smsContent = "方案启动提醒：【"+earlywarnEventdesc+"（"+earlywarnId+"）】已启动预案 ["+dispatchPlanMainName+"]，请根据 ["
			StringBuffer smsContent = new StringBuffer();
//				"方案启动提醒：【事件描述的文本（事件编号）】已启动预案 [预案名称]，请根据 [部门名称或任务名称] 相关工作要求执行以下任务：任务工作内容文本...";
			smsContent.append("方案启动提醒：【");
			smsContent.append(earlywarnEventdesc+"（");
			smsContent.append(earlywarnId+"）】已启动预案 [");
			smsContent.append(planMainName+"]，请根据 [");
			smsContent.append(dispatchRespdeptName+"] 相关工作要求执行以下任务：");
			smsContent.append(content+" 。");
			asyncActionDetailService.respDeptActionSMS(mobile,content,actionprogramId,dispatchRespdeptId,liabilityMan,now, String.valueOf(userId),dispatchRespdeptName,type,smsContent.toString(),planmainId,earlywarnEventdesc);
		}
	}

	private void respDeptActionDetail(String mobile,String content,String actionprogramId,String dispatchRespdeptId,String liabilityMan, Date now, String userId,String dispatchRespdeptName , int type) {
		if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){
			DispatchEarlywarnDO dispatchEarlywarnDO  = dispatchActionprogramMainDao.getEarlywarnById(actionprogramId);
			String earlywarnId ="";
			String earlywarnEventdesc ="";
			if(dispatchEarlywarnDO!=null){
				earlywarnId = dispatchEarlywarnDO.getId();
				earlywarnEventdesc = dispatchEarlywarnDO.getEventdesc();

			}
			DispatchPlanMainDO dispatchPlanMainDO = dispatchPlanMainDao.getByActionprogramId(actionprogramId);
			String dispatchPlanMainName = "";
			if(dispatchPlanMainDO != null){
				dispatchPlanMainName = dispatchPlanMainDO.getName();
			}

			//拆分电话-发送短信
			String [] mobiles=mobile.split("[,\\，]");
			if(mobiles !=null && mobiles.length>0){
				for(String mob:mobiles){
					respDeptActionSend(mob,content,actionprogramId,dispatchRespdeptId,liabilityMan,now, userId,dispatchRespdeptName ,  type,earlywarnEventdesc,earlywarnId,dispatchPlanMainName);
				}
			}
		}
	}

	private void taskActionDetailLost(String liabilityId, Integer type,String actionprogramId, Date now, String userId, String mobile, String content,String dispatchTaskId,String taskName, int actionType,String earlywarnId,String earlywarnEventdesc,String planMainName,String planmainId){
		if(StringUtils.isNotEmpty(liabilityId) && type !=null){
			String name =""; // 存储名称
			Map<String,Object> deptPersonMap;
			Map<String,Object> teamMap;
			Map<String,Object> supportDeptMap ;
			Map<String,Object> expertInfoMap ;
			switch (type) { // 接收任务的对象类型(个人、应急队伍、部门)
				case 1: // 个人
					deptPersonMap = deptPersonDao.getUniqueById(liabilityId);
					if(deptPersonMap !=null){
						name = (String) deptPersonMap.get("name");
					}
					break;
				case 2: // 应急队伍
					teamMap =  teamDao.getUniqueById(liabilityId);
					if(teamMap !=null){
						supportDeptMap =  supportDeptDao.getUniqueById((String)teamMap.get("dept_id"));
						if(supportDeptMap !=null){
							name = (String)supportDeptMap.get("contact"); //单位联系人
						}
					}
					break;
				case 3: // 部门
					supportDeptMap =  supportDeptDao.getUniqueById(liabilityId);
					if(supportDeptMap !=null){
						name = (String)supportDeptMap.get("contact"); //单位联系人
					}
					break;
				case 4: // 专家
					expertInfoMap =  expertInfoDao.getUniqueById(liabilityId);
					if(expertInfoMap !=null){
						name = (String)expertInfoMap.get("name");
					}
					break;
			}

			if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){
				// 发送短信(mobile); // 看要不要弄成群发
				StringBuffer smsContent = new StringBuffer();
				if(actionType == 1){
//				"方案启动提醒：【事件描述的文本（事件编号）】已启动预案 [预案名称]，请根据 [任务名称] 相关工作要求执行以下任务：任务工作内容文本...";
					smsContent.append("方案启动提醒：【");
					smsContent.append(earlywarnEventdesc+"（");
					smsContent.append(earlywarnId+"）】已启动预案 [");
					smsContent.append(planMainName+"]，请根据 [");
					smsContent.append(taskName+"] 相关工作要求执行以下任务：");
					smsContent.append(content+" 。");
				}else if(actionType == 2){
//				指令下达提醒：【事件描述的文本（事件编号）】，请按指令内容执行以下任务：[指令简介的文本内容] 指令内容的文本内容
					smsContent.append("指令下达提醒：【");
					smsContent.append(earlywarnEventdesc+"（");
					smsContent.append(earlywarnId+"）】，请按指令内容执行以下任务：[");
					smsContent.append(taskName+"] ");
					smsContent.append(content+" 。");
				}
				asyncActionDetailService.taskActionDetailSMS(name,type,actionprogramId,now,String.valueOf(userId), mobile,content,dispatchTaskId,taskName,actionType,smsContent.toString(),planmainId,earlywarnEventdesc);
			}
		}


	}

	private void taskActionDetail(String liabilityId, Integer type,String actionprogramId, Date now, String userId, String mobile, String content,String dispatchTaskId,String taskName, int actionType){
		if(StringUtils.isNotEmpty(liabilityId) && type !=null){
			String name =""; // 存储名称
			Map<String,Object> deptPersonMap;
			Map<String,Object> teamMap;
			Map<String,Object> supportDeptMap ;
			Map<String,Object> expertInfoMap ;
			switch (type) { // 接收任务的对象类型(个人、应急队伍、部门)
				case 1: // 个人
					deptPersonMap = deptPersonDao.getUniqueById(liabilityId);
					if(deptPersonMap !=null){
						name = (String) deptPersonMap.get("name");
					}
					break;
				case 2: // 应急队伍
					teamMap =  teamDao.getUniqueById(liabilityId);
					if(teamMap !=null){
						supportDeptMap =  supportDeptDao.getUniqueById((String)teamMap.get("dept_id"));
						if(supportDeptMap !=null){
							name = (String)supportDeptMap.get("contact"); //单位联系人
						}
					}
					break;
				case 3: // 部门
					supportDeptMap =  supportDeptDao.getUniqueById(liabilityId);
					if(supportDeptMap !=null){
						name = (String)supportDeptMap.get("contact"); //单位联系人
					}
					break;
				case 4: // 专家
					expertInfoMap =  expertInfoDao.getUniqueById(liabilityId);
					if(expertInfoMap !=null){
						name = (String)expertInfoMap.get("name");
					}
					break;
			}

			if(StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)){
				// 发送短信(mobile); // 看要不要弄成群发
				DispatchEarlywarnDO dispatchEarlywarnDO  = dispatchActionprogramMainDao.getEarlywarnById(actionprogramId);
				String earlywarnId ="";
				String earlywarnEventdesc ="";
				if(dispatchEarlywarnDO!=null){
					earlywarnId = dispatchEarlywarnDO.getId();
					earlywarnEventdesc = dispatchEarlywarnDO.getEventdesc();
				}
				DispatchPlanMainDO dispatchPlanMainDO = dispatchPlanMainDao.getByActionprogramId(actionprogramId);
				String dispatchPlanMainName = "";
				if(dispatchPlanMainDO != null){
					dispatchPlanMainName = dispatchPlanMainDO.getName();
				}


				//拆分电话-发送短信
				String [] mobiles=mobile.split("[,\\，]");
				if(mobiles !=null && mobiles.length>0){
					for(String mob:mobiles){
						taskActionDetailSend(liabilityId, type,actionprogramId,  now, userId, mob, content,dispatchTaskId,taskName, actionType,earlywarnEventdesc,earlywarnId,dispatchPlanMainName,name);

					}
				}
			}
		}


	}

	private void taskActionDetailSend(String liabilityId, Integer type,String actionprogramId, Date now, String userId, String mobile, String content,String dispatchTaskId,String taskName, int actionType,String earlywarnEventdesc,String earlywarnId,String dispatchPlanMainName,String name){
		// 发送短信(mobile); // 看要不要弄成群发
		StringBuffer smsContent = new StringBuffer();
		if(actionType == 1){
//				"方案启动提醒：【事件描述的文本（事件编号）】已启动预案 [预案名称]，请根据 [任务名称] 相关工作要求执行以下任务：任务工作内容文本...";
			smsContent.append("方案启动提醒：【");
			smsContent.append(earlywarnEventdesc+"（");
			smsContent.append(earlywarnId+"）】已启动预案 [");
			smsContent.append(dispatchPlanMainName+"]，请根据 [");
			smsContent.append(taskName+"] 相关工作要求执行以下任务：");
			smsContent.append(content+" 。");
		}else if(actionType == 2){
//				指令下达提醒：【事件描述的文本（事件编号）】，请按指令内容执行以下任务：[指令简介的文本内容] 指令内容的文本内容
			smsContent.append("指令下达提醒：【");
			smsContent.append(earlywarnEventdesc+"（");
			smsContent.append(earlywarnId+"）】，请按指令内容执行以下任务：[");
			smsContent.append(taskName+"] ");
			smsContent.append(content+" 。");
		}
		asyncActionDetailService.taskActionDetailSMS(name,type,actionprogramId,now,String.valueOf(userId), mobile,content,dispatchTaskId,taskName,actionType,smsContent.toString(),null,earlywarnEventdesc);

	}

	private void respDeptActionSend(String mobile,String content,String actionprogramId,String dispatchRespdeptId,String liabilityMan, Date now, String userId,String dispatchRespdeptName , int type,String earlywarnEventdesc,String earlywarnId,String dispatchPlanMainName){
		// 发送短信(mobile); // 看要不要弄成群发
//            String smsContent = "方案启动提醒：【"+earlywarnEventdesc+"（"+earlywarnId+"）】已启动预案 ["+dispatchPlanMainName+"]，请根据 ["
		StringBuffer smsContent = new StringBuffer();
//				"方案启动提醒：【事件描述的文本（事件编号）】已启动预案 [预案名称]，请根据 [部门名称或任务名称] 相关工作要求执行以下任务：任务工作内容文本...";
		smsContent.append("方案启动提醒：【");
		smsContent.append(earlywarnEventdesc+"（");
		smsContent.append(earlywarnId+"）】已启动预案 [");
		smsContent.append(dispatchPlanMainName+"]，请根据 [");
		smsContent.append(dispatchRespdeptName+"] 相关工作要求执行以下任务：");
		smsContent.append(content+" 。");
		asyncActionDetailService.respDeptActionSMS(mobile,content,actionprogramId,dispatchRespdeptId,liabilityMan,now, String.valueOf(userId),dispatchRespdeptName,type,smsContent.toString(),null,earlywarnEventdesc);

	}


}
