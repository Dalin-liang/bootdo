package com.bootdo.archive.controller;

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

import com.bootdo.archive.domain.ArchiveTaskDO;
import com.bootdo.archive.service.ArchiveTaskService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 应急执行方案归档任务表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveTask")
public class ArchiveTaskController {
	@Autowired
	private ArchiveTaskService archiveTaskService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveTask:archiveTask")
	String ArchiveTask(){
	    return "archive/archiveTask/archiveTask";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveTask:archiveTask")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveTaskDO> archiveTaskList = archiveTaskService.list(query);
		int total = archiveTaskService.count(query);
		PageUtils pageUtils = new PageUtils(archiveTaskList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveTask:add")
	String add(){
	    return "archive/archiveTask/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveTask:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveTaskDO archiveTask = archiveTaskService.get(id);
		model.addAttribute("archiveTask", archiveTask);
	    return "archive/archiveTask/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveTask:add")
	public R save( ArchiveTaskDO archiveTask){
		if(archiveTaskService.save(archiveTask)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveTask:edit")
	public R update( ArchiveTaskDO archiveTask){
		archiveTaskService.update(archiveTask);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveTask:remove")
	public R remove( String id){
		if(archiveTaskService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveTask:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveTaskService.batchRemove(ids);
		return R.ok();
	}
	
}
