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

import com.bootdo.archive.domain.ArchiveLogDO;
import com.bootdo.archive.service.ArchiveLogService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 应急调度日志归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveLog")
public class ArchiveLogController {
	@Autowired
	private ArchiveLogService archiveLogService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveLog:archiveLog")
	String ArchiveLog(){
	    return "archive/archiveLog/archiveLog";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveLog:archiveLog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveLogDO> archiveLogList = archiveLogService.list(query);
		int total = archiveLogService.count(query);
		PageUtils pageUtils = new PageUtils(archiveLogList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveLog:add")
	String add(){
	    return "archive/archiveLog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveLog:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveLogDO archiveLog = archiveLogService.get(id);
		model.addAttribute("archiveLog", archiveLog);
	    return "archive/archiveLog/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveLog:add")
	public R save( ArchiveLogDO archiveLog){
		if(archiveLogService.save(archiveLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveLog:edit")
	public R update( ArchiveLogDO archiveLog){
		archiveLogService.update(archiveLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveLog:remove")
	public R remove( String id){
		if(archiveLogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveLog:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveLogService.batchRemove(ids);
		return R.ok();
	}
	
}
