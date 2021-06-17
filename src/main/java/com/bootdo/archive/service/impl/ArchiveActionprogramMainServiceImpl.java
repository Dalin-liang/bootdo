package com.bootdo.archive.service.impl;

import com.alibaba.fastjson.JSON;
import com.bootdo.archive.dao.ArchiveActionprogramMainDao;
import com.bootdo.archive.dao.ArchivePlanmainDao;
import com.bootdo.archive.dao.ArchiveRespdeptDao;
import com.bootdo.archive.dao.ArchiveTaskDao;
import com.bootdo.archive.domain.ArchiveActionprogramMainDO;
import com.bootdo.archive.domain.ArchivePlanmainDO;
import com.bootdo.archive.domain.ArchiveRespdeptDO;
import com.bootdo.archive.domain.ArchiveTaskDO;
import com.bootdo.archive.service.ArchiveActionprogramMainService;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.planManage.service.*;
import com.bootdo.support.service.DeptPersonService;
import com.bootdo.support.service.ExpertInfoService;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.support.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ArchiveActionprogramMainServiceImpl implements ArchiveActionprogramMainService {
	@Autowired
	private ArchiveActionprogramMainDao archiveActionprogramMainDao;
	@Autowired
	private ArchivePlanmainDao archivePlanmainDao;
	@Autowired
	private ArchiveRespdeptDao archiveRespdeptDao;
	@Autowired
	private ArchiveTaskDao archiveTaskDao;

	@Autowired
	private PlanMainService planMainService;
	@Autowired
	private PlanRespDeptService planRespDeptService;
	@Autowired
	private PlanTaskService planTaskService;
	@Autowired
	private PlanAccidentTypeService planAccidentTypeService;
	@Autowired
	private PlanEarlywarnLevelService planEarlywarnLevelService;
	@Autowired
	private PlanEarlywarnTypeService planEarlywarnTypeService;
	@Autowired
	private SupportDeptService supportDeptService;
	@Autowired
	private DeptPersonService deptPersonService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private ExpertInfoService expertInfoService;

	@Override
	public ArchiveActionprogramMainDO get(String id){
		return archiveActionprogramMainDao.get(id);
	}
	
	@Override
	public List<Map<String,Object>> list(Map<String, Object> map){
		return archiveActionprogramMainDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return archiveActionprogramMainDao.count(map);
	}
	
	@Override
	public int save(ArchiveActionprogramMainDO archiveActionprogramMain){
		return archiveActionprogramMainDao.save(archiveActionprogramMain);
	}
	
	@Override
	public int update(ArchiveActionprogramMainDO archiveActionprogramMain){
		return archiveActionprogramMainDao.update(archiveActionprogramMain);
	}
	
	@Override
	public int remove(String id){
		return archiveActionprogramMainDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return archiveActionprogramMainDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getProgramMainByParam(String warnTypeId, String accidentName, String warnTypeName, String warnLevelName) {
		if(StringUtils.isNotEmpty(accidentName) || StringUtils.isNotEmpty(warnTypeName) || StringUtils.isNotEmpty(warnLevelName)){
			return archiveActionprogramMainDao.getByParamName(accidentName, warnTypeName ,warnLevelName);
		}else if(StringUtils.isNotEmpty(warnTypeId)){
			return archiveActionprogramMainDao.getByWarnTypeId(warnTypeId);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getProgramMainDetailByWarnTypeId(String eventId) {
		return archiveActionprogramMainDao.getProgramMainDetailByWarnTypeId(eventId);
	}

	@Override
	public List<Map<String, Object>> getByLostParam(String accidentName, String warnTypeName, String warnLevelName,
			String code, String name, String beginTime, String endTime, String receiveBtime, String receiveEtime) {
		return archiveActionprogramMainDao.getByLostParam(accidentName, warnTypeName, warnLevelName, code,
				name, beginTime, endTime,receiveBtime,receiveEtime);
	}

	@Override
	public Map<String,Object> getCountByCase() {
		// TODO Auto-generated method stub
		return archiveActionprogramMainDao.getCountByCase();
	}


	@Transactional(rollbackFor=Exception.class)
	public void updateProgram(String actionprogramMain,String planMain,String respdept,String task){
		Long userId = ShiroUtils.getUser().getUserId();
		Date dateTime = new Date() ;
		String actionprogramMainId = null;
		String planmainId = null;

		//方案
		ArchiveActionprogramMainDO archiveActionprogramMain = JSON.parseObject(actionprogramMain, ArchiveActionprogramMainDO.class);
		actionprogramMainId = archiveActionprogramMain.getId();
		archiveActionprogramMain.setUpdateBy(userId);
		archiveActionprogramMain.setUpdateDate(dateTime);
		archiveActionprogramMainDao.update(archiveActionprogramMain);

		//预案信息
		ArchivePlanmainDO archivePlanmainDO = new ArchivePlanmainDO();
		if(planMain !=null &&!"".equals(planMain)){
			archivePlanmainDO = JSON.parseObject(planMain, ArchivePlanmainDO.class);
			planmainId = archivePlanmainDO.getId();
			archivePlanmainDO.setUpdateBy(userId);
			archivePlanmainDO.setUpdateDate(dateTime);
			archivePlanmainDao.update(archivePlanmainDO);

			//响应部门
			if(respdept!=null &&!"".equals(respdept) ) {
				List<Map<String,Object>> respDeptList = JSON.parseObject(respdept, List.class);
				String respDeptId = null;
				for (Map<String, Object> map : respDeptList) {
					ArchiveRespdeptDO archiveRespdeptDO = JSON.parseObject(JSON.toJSONString(map), ArchiveRespdeptDO.class);
					respDeptId = archiveRespdeptDO.getId();
					if(archiveRespdeptDao.get(respDeptId) != null){
						archiveRespdeptDO.setUpdateBy(userId);
						archiveRespdeptDO.setUpdateDate(dateTime);
						archiveRespdeptDao.update(archiveRespdeptDO);
					}else{
						archiveRespdeptDO.setId(UUID.randomUUID().toString().replace("-",""));
						archiveRespdeptDO.setActionprogramId(actionprogramMainId);
						archiveRespdeptDO.setDispatchPlanmainId(planmainId);
						archiveRespdeptDO.setCreateBy(userId);
						archiveRespdeptDO.setCreateDate(dateTime);
						archiveRespdeptDO.setUpdateBy(userId);
						archiveRespdeptDO.setUpdateDate(dateTime);
						archiveRespdeptDao.save(archiveRespdeptDO);
					}
				}
			}

			//任务
			if(task!=null &&!"".equals(task) ) {
				List<Map<String,Object>> taskList = JSON.parseObject(task, List.class);
				for (Map<String, Object> map : taskList) {
					ArchiveTaskDO archiveTaskDO = JSON.parseObject(JSON.toJSONString(map), ArchiveTaskDO.class);
					String taskId = archiveTaskDO.getId();
					if(archiveTaskDao.get(taskId) != null){
						archiveTaskDO.setUpdateBy(userId);
						archiveTaskDO.setUpdateDate(dateTime);
						archiveTaskDao.update(archiveTaskDO);
					}else{
						archiveTaskDO.setId(UUID.randomUUID().toString().replace("-",""));
						archiveTaskDO.setActionprogramId(actionprogramMainId);
						archiveTaskDO.setDispatchPlanmainId(planmainId);
						archiveTaskDO.setCreateBy(userId);
						archiveTaskDO.setCreateDate(dateTime);
						archiveTaskDO.setUpdateBy(userId);
						archiveTaskDO.setUpdateDate(dateTime);
						archiveTaskDao.save(archiveTaskDO);
					}
				}
			}
		}

	}

	@Override
	public List<Map<String, Object>> getEventType(String flag) {
		return archiveActionprogramMainDao.getEventType(flag);
	}


	@Override
	public List<Map<String, Object>> getEventLevel(String flag) {
		return archiveActionprogramMainDao.getEventLevel(flag);
	}

	@Override
	public List<Map<String, Object>> getEventImport(String flag) {
		return archiveActionprogramMainDao.getEventImport(flag);
	}

	@Override
	public List<Map<String, Object>> getEventDayData() {
		return archiveActionprogramMainDao.getEventDayData();
	}

	@Override
	public List<Map<String, Object>> getEventCurMonth() {
		return archiveActionprogramMainDao.getEventCurMonth();
	}

	@Override
	public Integer getEventYoYCompared(String eventType) {
		return archiveActionprogramMainDao.getEventYoYCompared(eventType);
	}

	@Override
	public Integer getEventMoMCompared(String eventType) {
		return archiveActionprogramMainDao.getEventMoMCompared(eventType);
	}

	/**
	 * 根据结案的修改，生产新的预案信息
	 * @param actionprogramMain
	 * @param planMain
	 * @param respdept
	 * @param task
	 */
	@Transactional(rollbackFor=Exception.class)
	public int productPlan(String actionprogramMain, String planMain, String respdept, String task) throws Exception{
		Long userId = ShiroUtils.getUser().getUserId();
		Date dateTime = new Date() ;
		String planmainId = null;
		int row = 0;


		//产生预案
		ArchivePlanmainDO archivePlanmainDO = new ArchivePlanmainDO();
		if(planMain !=null &&!"".equals(planMain)){
			archivePlanmainDO = JSON.parseObject(planMain, ArchivePlanmainDO.class);

			//插入预案表
			PlanMainDO planMainDO = new PlanMainDO();
			planmainId = UUID.randomUUID().toString().replace("-","");
			planMainDO.setId(planmainId);
			String accidentTypeName = archivePlanmainDO.getAccidentTypeName();
			//事故类型
			if(StringUtils.isNotEmpty(accidentTypeName)){
				String accidentTypeId = planAccidentTypeService.getIdByName(accidentTypeName);
				if(StringUtils.isNotEmpty(accidentTypeId)){
					planMainDO.setAccidentTypeId(accidentTypeId);
				}else{
					throw new Exception("该事故类型已经被删除，请选择其他类型");
				}
			}else{
				throw new Exception("事故类型不能为空");
			}

			//预警类型
			String earlywarnTypeName = archivePlanmainDO.getEarlywarnTypeName();
			if(StringUtils.isNotEmpty(accidentTypeName) && StringUtils.isNotEmpty(earlywarnTypeName)){
				String earlywarnTypeId = planEarlywarnTypeService.getIdByNames(accidentTypeName,earlywarnTypeName);
				if(StringUtils.isNotEmpty(earlywarnTypeId)){
					planMainDO.setEarlywarnTypeId(earlywarnTypeId);
				}else{
					throw new Exception("该预警类型已经被删除，请选择其他类型");
				}
			}else{
				throw new Exception("预警类型不能为空");
			}

			//预警级别
			String earlywarnLevelName = archivePlanmainDO.getEarlywarnLevelName();
			if(StringUtils.isNotEmpty(accidentTypeName) && StringUtils.isNotEmpty(earlywarnTypeName) && StringUtils.isNotEmpty(earlywarnLevelName)){
				String earlywarnLevelId = planEarlywarnLevelService.getIdByNames(accidentTypeName,earlywarnTypeName,earlywarnLevelName);
				if(StringUtils.isNotEmpty(earlywarnLevelId)){
					planMainDO.setEarlywarnLevelId(earlywarnLevelId);
				}else{
					throw new Exception("该预警级别已经被删除，请选择其他类型");
				}
			}else{
				throw new Exception("预警级别不能为空");
			}

			//责任部门
			if(StringUtils.isNotEmpty(archivePlanmainDO.getDutyDeptName())){
				String dutyDeptId = supportDeptService.getIdByName(archivePlanmainDO.getDutyDeptName());
				if(StringUtils.isNotEmpty(dutyDeptId)){
					planMainDO.setDutyDeptId(dutyDeptId);
				}else{
					throw new Exception("该责任部门已经被删除，请选择其他部门");
				}
			}else{
				throw new Exception("责任部门不能为空");
			}
			planMainDO.setName(archivePlanmainDO.getName());
			planMainDO.setCode(getCode());
			planMainDO.setStartCondition(archivePlanmainDO.getStartCondition());
			//报告单位
			if(StringUtils.isNotEmpty(archivePlanmainDO.getReprotDeptName())){
				String reprotDeptId = supportDeptService.getIdByName(archivePlanmainDO.getReprotDeptName());
				if(StringUtils.isNotEmpty(reprotDeptId)){
					planMainDO.setReprotDeptId(reprotDeptId);
				}else{
					throw new Exception("该报告单位已经被删除，请选择其他单位");
				}
			}else{
				throw new Exception("报告单位不能为空");
			}
			//启动部门
			if(StringUtils.isNotEmpty(archivePlanmainDO.getStartDeptName())){
				String startDeptId = supportDeptService.getIdByName(archivePlanmainDO.getStartDeptName());
				if(StringUtils.isNotEmpty(startDeptId)){
					planMainDO.setStartDeptId(startDeptId);
				}else{
					throw new Exception("该启动部门已经被删除，请选择其他部门");
				}
			}else{
				throw new Exception("启动部门不能为空");
			}
			planMainDO.setEnabled(1);
			planMainDO.setUseTime(null);
			planMainDO.setRemarks(archivePlanmainDO.getRemarks());
			planMainDO.setCreateBy(String.valueOf(userId));
			planMainDO.setCreateDate(dateTime);
			row = planMainService.save(planMainDO);

			if(row >0 ){
				//插入响应部门
				if(respdept!=null &&!"".equals(respdept) ) {
					List<Map<String,Object>> respDeptList = JSON.parseObject(respdept, List.class);
					for (Map<String, Object> map : respDeptList) {
						ArchiveRespdeptDO archiveRespdeptDO = JSON.parseObject(JSON.toJSONString(map), ArchiveRespdeptDO.class);

						PlanRespDeptDO planRespDeptDO = new PlanRespDeptDO();
						planRespDeptDO.setId(UUID.randomUUID().toString().replace("-",""));
						planRespDeptDO.setPlanMainId(planmainId);
						if(StringUtils.isNotEmpty(archiveRespdeptDO.getDeptName())){
							planRespDeptDO.setDeptId(supportDeptService.getIdByName(archiveRespdeptDO.getDeptName()));
						}
						//响应部门
						if(StringUtils.isNotEmpty(archiveRespdeptDO.getDeptName())){
							String deptId = supportDeptService.getIdByName(archiveRespdeptDO.getDeptName());
							if(StringUtils.isNotEmpty(deptId)){
								planRespDeptDO.setDeptId(deptId);
							}else{
								throw new Exception("响应部门里的"+archiveRespdeptDO.getDeptName()+"部门已经被删除，请选择其他部门");
							}
						}else{
							throw new Exception("响应部门不能为空");
						}
						planRespDeptDO.setContent(archiveRespdeptDO.getContent());
						planRespDeptDO.setLiabilityMan(archiveRespdeptDO.getLiabilityMan());
						planRespDeptDO.setMobile(archiveRespdeptDO.getMobile());
						planRespDeptDO.setCreateBy(String.valueOf(userId));
						planRespDeptDO.setCreateDate(dateTime);
						planRespDeptDO.setSortNo(archiveRespdeptDO.getSortNo());
						planRespDeptDO.setTaskType(archiveRespdeptDO.getTaskType());
						planRespDeptService.save(planRespDeptDO);

					}
				}

				//插入响应任务
				if(task!=null &&!"".equals(task) ) {
					List<Map<String,Object>> taskList = JSON.parseObject(task, List.class);
					for (Map<String, Object> map : taskList) {
						ArchiveTaskDO archiveTaskDO = JSON.parseObject(JSON.toJSONString(map), ArchiveTaskDO.class);

						PlanTaskDO planTaskDO = new PlanTaskDO();
						planTaskDO.setId(UUID.randomUUID().toString().replace("-",""));
						planTaskDO.setPlanMainId(planmainId);
						planTaskDO.setSortNo(archiveTaskDO.getSortNo());
						planTaskDO.setName(archiveTaskDO.getName());
						planTaskDO.setContent(archiveTaskDO.getContent());

						planTaskDO.setMobile(archiveTaskDO.getMobile());
						planTaskDO.setCreateBy(String.valueOf(userId));
						planTaskDO.setCreateDate(dateTime);

						//任务类型
						int type = archiveTaskDO.getType();
						planTaskDO.setType(type);
						String liabilityId = archiveTaskDO.getLiabilityId();
						switch (type) { // 接收任务的对象类型(个人、应急队伍、部门)
							case 1: // 个人
								if(deptPersonService.getUniqueById(liabilityId) == null){
									throw new Exception("响应任务对象，已被删除，请选择其他类型");
								}
								break;
							case 2: // 应急队伍
								if(teamService.getUniqueById(liabilityId) == null){
									throw new Exception("响应任务对象，已被删除，请选择其他类型");
								}
								break;
							case 3: // 部门
								if(supportDeptService.getUniqueById(liabilityId) == null){
									throw new Exception("响应任务对象，已被删除，请选择其他类型");
								}
								break;
							case 4: // 专家
								if(expertInfoService.getUniqueById(liabilityId) == null){
									throw new Exception("响应任务对象，已被删除，请选择其他类型");
								}
								break;
						}
						planTaskDO.setLiabilityId(liabilityId);
						planTaskDO.setTaskType(archiveTaskDO.getTaskType());
						planTaskService.save(planTaskDO);

					}
				}
			}
		}
		return row;
	}

	@Override
	public List<Map<String, Object>> getEventDataCompared(String flag) {
		return archiveActionprogramMainDao.getEventDataCompared(flag);
	}

    @Override
    public List<Map<String, Object>> getEventMonthsData() {
        return archiveActionprogramMainDao.getEventMonthsData();
    }


    //生成编码
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
}
