package com.bootdo.actionprogramManage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.actionprogramManage.domain.DispatchActionprogramMainDO;
import com.bootdo.actionprogramManage.domain.DispatchPlanMainDO;
import com.bootdo.actionprogramManage.domain.DispatchRespdeptDO;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.actionprogramManage.service.DispatchPlanMainService;
import com.bootdo.actionprogramManage.service.DispatchRespdeptService;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.service.ExpertInfoService;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.support.service.TeamService;

/**
 * 应急执行方案主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchActionprogramMain")
public class DispatchActionprogramMainController {
	@Autowired
	private DispatchActionprogramMainService dispatchActionprogramMainService;
	@Autowired
	private DispatchPlanMainService dispatchPlanMainService;
	@Autowired
	private DispatchRespdeptService dispatchRespdeptService ;
	@Autowired
	private DispatchTaskService dispatchTaskService;
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
		List<DispatchActionprogramMainDO> dispatchActionprogramMainList = dispatchActionprogramMainService.list(query);
		int total = dispatchActionprogramMainService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchActionprogramMainList, total);
		return pageUtils;
	}
	
	@PostMapping("/getProgramDetails")
	@ResponseBody
	public Map<String,Object> getProgramDetails(String actionprogramId,Model model){
		DispatchPlanMainDO dispatchPlanMain = dispatchPlanMainService.getByActionprogramId(actionprogramId);
		Map<String,Object> map =new HashMap<String, Object>();
		if(dispatchPlanMain != null){
			map.put("dispatchPlanMain", dispatchPlanMain);
			String planMainId = dispatchPlanMain.getId();
			//责任单位
			List<DispatchRespdeptDO> respDeptList = dispatchRespdeptService.getByPlanMainId(planMainId);
			map.put("dispatchRespDept", respDeptList);
			
			//任务
			List<Map<String,Object>> taskList = dispatchTaskService.getByActionprogramIdAndPlanMainId(actionprogramId,planMainId);
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
			map.put("dispatchTask", taskList);
		}
		map.put("msg", "true");
		return map;
	}
	
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(String actionprogramMain,String planMain,String respdept,String task){
		dispatchActionprogramMainService.updateProgram(actionprogramMain,planMain,respdept,task);
		return R.ok();
	}
	
}
