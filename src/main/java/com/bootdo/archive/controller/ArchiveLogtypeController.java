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

import com.bootdo.archive.domain.ArchiveLogtypeDO;
import com.bootdo.archive.service.ArchiveLogtypeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 应急调度日志归档类别表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveLogtype")
public class ArchiveLogtypeController {
	@Autowired
	private ArchiveLogtypeService archiveLogtypeService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveLogtype:archiveLogtype")
	String ArchiveLogtype(){
	    return "archive/archiveLogtype/archiveLogtype";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveLogtype:archiveLogtype")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveLogtypeDO> archiveLogtypeList = archiveLogtypeService.list(query);
		int total = archiveLogtypeService.count(query);
		PageUtils pageUtils = new PageUtils(archiveLogtypeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveLogtype:add")
	String add(){
	    return "archive/archiveLogtype/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveLogtype:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveLogtypeDO archiveLogtype = archiveLogtypeService.get(id);
		model.addAttribute("archiveLogtype", archiveLogtype);
	    return "archive/archiveLogtype/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveLogtype:add")
	public R save( ArchiveLogtypeDO archiveLogtype){
		if(archiveLogtypeService.save(archiveLogtype)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveLogtype:edit")
	public R update( ArchiveLogtypeDO archiveLogtype){
		archiveLogtypeService.update(archiveLogtype);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveLogtype:remove")
	public R remove( String id){
		if(archiveLogtypeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveLogtype:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveLogtypeService.batchRemove(ids);
		return R.ok();
	}
	
}
