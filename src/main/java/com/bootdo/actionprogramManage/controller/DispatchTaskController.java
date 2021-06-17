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

import com.bootdo.actionprogramManage.domain.DispatchTaskDO;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 应急执行方案任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchTask")
public class DispatchTaskController {
	@Autowired
	private DispatchTaskService dispatchTaskService;
	
	@GetMapping()
	String DispatchTask(){
	    return "actionprogramManage/dispatchTask/dispatchTask";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchTaskDO> dispatchTaskList = dispatchTaskService.list(query);
		int total = dispatchTaskService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchTaskList, total);
		return pageUtils;
	}
	
	@PostMapping("/getSelectData")
	@ResponseBody
	public Map<String,Object> getSelectData(){
		Map<String,Object> map =new HashMap<String, Object>();
		List<Map<String,Object>> dpetList = dispatchTaskService.getDpetList();
		List<Map<String,Object>> personList = dispatchTaskService.getPersonList();
		List<Map<String,Object>> expertList = dispatchTaskService.getExpertList();
		List<Map<String,Object>> teamList = dispatchTaskService.getTeamList();
		map.put("dpetList", dpetList);
		map.put("personList", personList);
		map.put("expertList", expertList);
		map.put("teamList", teamList);
		map.put("msg", "true");
		return map;
	}
	
	@GetMapping("/add")
	String add(){
	    return "actionprogramManage/dispatchTask/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		DispatchTaskDO dispatchTask = dispatchTaskService.get(id);
		model.addAttribute("dispatchTask", dispatchTask);
	    return "actionprogramManage/dispatchTask/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( DispatchTaskDO dispatchTask){
		if(dispatchTaskService.save(dispatchTask)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( DispatchTaskDO dispatchTask){
		dispatchTaskService.update(dispatchTask);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(dispatchTaskService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchTaskService.batchRemove(ids);
		return R.ok();
	}
	
}
