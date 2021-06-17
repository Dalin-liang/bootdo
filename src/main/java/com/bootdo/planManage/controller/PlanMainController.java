package com.bootdo.planManage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.planManage.domain.PlanAccidentTypeDO;
import com.bootdo.planManage.domain.PlanEarlywarnLevelDO;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.planManage.service.PlanAccidentTypeService;
import com.bootdo.planManage.service.PlanEarlywarnLevelService;
import com.bootdo.planManage.service.PlanEarlywarnTypeService;
import com.bootdo.planManage.service.PlanMainService;
import com.bootdo.planManage.service.PlanRespDeptService;
import com.bootdo.planManage.service.PlanTaskService;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.service.ExpertInfoService;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.support.service.TeamService;
import com.bootdo.system.domain.UserDO;
import com.alibaba.fastjson.JSON;
import com.bootdo.common.utils.PageQuery;
import com.bootdo.common.utils.PageResultMap;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;

/**
 * 预案表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
 
@Controller
@RequestMapping("/planManage/planMain")
public class PlanMainController {
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
	private DeptPersonMapper deptPersonMapper;
	@Autowired
	private ExpertInfoService expertInfoService;
	@Autowired
	private TeamService teamService;
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<PlanMainDO> planMainList = planMainService.list(query);
		int total = planMainService.count(query);
		PageUtils pageUtils = new PageUtils(planMainList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/actionRecordlist")
	public PageUtils actionRecordlist(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<PlanMainDO> planMainList = planMainService.actionRecordlist(query);
		int total = planMainService.actionRecordlistCount(query);
		PageUtils pageUtils = new PageUtils(planMainList, total);
		return pageUtils;
	}
	
	@PostMapping("/getPlanDetails")
	@ResponseBody
	public Map<String,Object> getPlan(String id,Model model){
		PlanMainDO planMain = planMainService.get(id);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("planMain", planMain);
		
		//责任单位
		List<PlanRespDeptDO> respDeptList = planRespDeptService.getByPlanId(id);
		map.put("respDept", respDeptList);
		
		//任务
		List<PlanTaskDO> taskList = planTaskService.getByPlanId(id);
		if(taskList !=null && taskList.size()>0){
			String liabilityId ="";
			Integer type;
			for(PlanTaskDO planTaskDO : taskList) {
				type = planTaskDO.getType();
				liabilityId = planTaskDO.getLiabilityId();
				if(type !=null && StringUtils.isNotEmpty(liabilityId)){
					Map<String,Object> deptPersonMap;
					Map<String,Object> teamMap;
					Map<String,Object> supportDeptMap ;
					Map<String,Object> expertInfoMap ;
					switch (type) { // 接收任务的对象类型(个人、应急队伍、部门)
						case 1: // 个人
							deptPersonMap = deptPersonMapper.getUniqueById(liabilityId);
							if(deptPersonMap !=null){
								planTaskDO.setLiabilityMan((String) deptPersonMap.get("name"));
							}
							break;
						case 2: // 应急队伍
							teamMap =  teamService.getUniqueById(liabilityId);
							if(teamMap !=null){
								planTaskDO.setLiabilityMan((String)teamMap.get("name"));
							}
							break;
						case 3: // 部门
							supportDeptMap =  supportDeptService.getUniqueById(liabilityId);
							if(supportDeptMap !=null){
								planTaskDO.setLiabilityMan((String)supportDeptMap.get("name"));
							}
							break;
						case 4: // 专家
							expertInfoMap =  expertInfoService.getUniqueById(liabilityId);
							if(expertInfoMap !=null){
								planTaskDO.setLiabilityMan((String)expertInfoMap.get("name"));
							}
							break;
					}
				}
			}
		}
		map.put("task", taskList);
		map.put("msg", "true");
		return map;
	}
	
	@PostMapping("/getSelectData")
	@ResponseBody
	public Map<String,Object> getSelectData(Model model){
		Map<String,Object> map =new HashMap<String, Object>();
		List<PlanAccidentTypeDO> accidentTypeList = planAccidentTypeService.getAccidentType();
		map.put("accidentTypeList", accidentTypeList);
		List<Map<String,Object>> dpetList = planAccidentTypeService.getDpetList();
		map.put("dpetList", dpetList);
		List<PlanEarlywarnLevelDO> earlywarnLevelList = planEarlywarnLevelService.getEarlywarnLevel();
		map.put("earlywarnLevelList", earlywarnLevelList);
		List<PlanEarlywarnTypeDO> earlywarnTypeList = planEarlywarnTypeService.getEarlywarnType();
		map.put("earlywarnTypeList", earlywarnTypeList);
		map.put("msg", "true");
		return map;
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String enabled){
		if(planMainService.changeStatus(id ,enabled)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save(String planMain,String respDept,String task){
		planMainService.addPlan(planMain,respDept,task);
		return R.ok("true");
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(String planMain,String respDept,String task){
		planMainService.updatePlan(planMain,respDept,task);
		return R.ok("true");
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(planMainService.deletePlan(id)>0){
				return R.ok("true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(500,"删除数据失败，请先删除相关的表记录！");
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] ids){
		planMainService.batchDeletePlan(ids);
		return R.ok();
	}
	
}
