package com.bootdo.archive.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.archive.domain.ArchivePlanmainDO;
import com.bootdo.archive.domain.ArchiveRespdeptDO;
import com.bootdo.archive.service.ArchivePlanmainService;
import com.bootdo.archive.service.ArchiveRespdeptService;
import com.bootdo.archive.service.ArchiveTaskService;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.service.ExpertInfoService;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.support.service.TeamService;
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

import com.bootdo.archive.domain.ArchiveActionprogramMainDO;
import com.bootdo.archive.service.ArchiveActionprogramMainService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 应急执行方案归档主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveActionprogramMain")
public class ArchiveActionprogramMainController {
	@Autowired
	private ArchiveActionprogramMainService archiveActionprogramMainService;
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
	private ExpertInfoService expertInfoService;
	@Autowired
	private TeamService teamService;
	

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map<String,Object>> archiveActionprogramMainList = archiveActionprogramMainService.list(query);
		int total = archiveActionprogramMainService.count(query);
		PageUtils pageUtils = new PageUtils(archiveActionprogramMainList, total);
		return pageUtils;
	}

	@PostMapping("/getProgramDetails")
	@ResponseBody
	public Map<String,Object> getProgramDetails(String actionprogramId,Model model){
		ArchivePlanmainDO archivePlanMain = archivePlanmainService.getByActionprogramId(actionprogramId);
		Map<String,Object> map =new HashMap<String, Object>();
		if(archivePlanMain != null){
			map.put("archivePlanMain", archivePlanMain);
			String planMainId = archivePlanMain.getId();
			//责任单位
			List<ArchiveRespdeptDO> respDeptList = archiveRespdeptService.getByPlanMainId(planMainId);
			map.put("archiveRespDept", respDeptList);

			//任务
			List<Map<String,Object>> taskList = archiveTaskService.getByActionprogramIdAndPlanMainId(actionprogramId,planMainId);
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
			map.put("archiveTask", taskList);
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
		archiveActionprogramMainService.updateProgram(actionprogramMain,planMain,respdept,task);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/productPlan")
	public R productPlan(String actionprogramMain,String planMain,String respdept,String task){
		int row = 0;
		try {
			row = archiveActionprogramMainService.productPlan(actionprogramMain,planMain,respdept,task);
			if(row >0 ){
				return R.ok("生产预案成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}

		return R.error("生产预案失败");
	}
	
//	@GetMapping("/add")
//	@RequiresPermissions("archive:archiveActionprogramMain:add")
//	String add(){
//	    return "archive/archiveActionprogramMain/add";
//	}
//
//	@GetMapping("/edit/{id}")
//	@RequiresPermissions("archive:archiveActionprogramMain:edit")
//	String edit(@PathVariable("id") String id,Model model){
//		ArchiveActionprogramMainDO archiveActionprogramMain = archiveActionprogramMainService.get(id);
//		model.addAttribute("archiveActionprogramMain", archiveActionprogramMain);
//	    return "archive/archiveActionprogramMain/edit";
//	}
//
//	/**
//	 * 保存
//	 */
//	@ResponseBody
//	@PostMapping("/save")
//	@RequiresPermissions("archive:archiveActionprogramMain:add")
//	public R save( ArchiveActionprogramMainDO archiveActionprogramMain){
//		if(archiveActionprogramMainService.save(archiveActionprogramMain)>0){
//			return R.ok();
//		}
//		return R.error();
//	}
//	/**
//	 * 修改
//	 */
//	@ResponseBody
//	@RequestMapping("/update")
//	@RequiresPermissions("archive:archiveActionprogramMain:edit")
//	public R update( ArchiveActionprogramMainDO archiveActionprogramMain){
//		archiveActionprogramMainService.update(archiveActionprogramMain);
//		return R.ok();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/remove")
//	@ResponseBody
//	@RequiresPermissions("archive:archiveActionprogramMain:remove")
//	public R remove( String id){
//		if(archiveActionprogramMainService.remove(id)>0){
//		return R.ok();
//		}
//		return R.error();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/batchRemove")
//	@ResponseBody
//	@RequiresPermissions("archive:archiveActionprogramMain:batchRemove")
//	public R remove(@RequestParam("ids[]") String[] ids){
//		archiveActionprogramMainService.batchRemove(ids);
//		return R.ok();
//	}
	
}
