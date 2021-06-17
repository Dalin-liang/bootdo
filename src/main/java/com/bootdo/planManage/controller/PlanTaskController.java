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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.planManage.domain.PlanTaskDO;
import com.bootdo.planManage.service.PlanTaskService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;

/**
 * 预案任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
 
@Controller
@RequestMapping("/planManage/planTask")
public class PlanTaskController {
	@Autowired
	private PlanTaskService planTaskService;
	
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PlanTaskDO> planTaskList = planTaskService.list(query);
		int total = planTaskService.count(query);
		PageUtils pageUtils = new PageUtils(planTaskList, total);
		return pageUtils;
	}
	

	@GetMapping("/edit/{id}")
	public Map<String, Object> edit(@PathVariable("id") String id,Model model){
		PlanTaskDO planTask = planTaskService.get(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planTask", planTask);
		return map;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( PlanTaskDO planTask){
		if(planTaskService.save(planTask)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( PlanTaskDO planTask){
		planTaskService.update(planTask);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("planManage:planTask:remove")
	public R remove( String id){
		try {
			if(planTaskService.remove(id)>0){
				return R.ok();
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
	@RequiresPermissions("planManage:planTask:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		planTaskService.batchRemove(ids);
		return R.ok();
	}
	
}
