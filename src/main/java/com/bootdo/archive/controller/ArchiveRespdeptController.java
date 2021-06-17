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

import com.bootdo.archive.domain.ArchiveRespdeptDO;
import com.bootdo.archive.service.ArchiveRespdeptService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 执行方案的预案响应部门归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveRespdept")
public class ArchiveRespdeptController {
	@Autowired
	private ArchiveRespdeptService archiveRespdeptService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveRespdept:archiveRespdept")
	String ArchiveRespdept(){
	    return "archive/archiveRespdept/archiveRespdept";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveRespdept:archiveRespdept")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveRespdeptDO> archiveRespdeptList = archiveRespdeptService.list(query);
		int total = archiveRespdeptService.count(query);
		PageUtils pageUtils = new PageUtils(archiveRespdeptList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveRespdept:add")
	String add(){
	    return "archive/archiveRespdept/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveRespdept:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveRespdeptDO archiveRespdept = archiveRespdeptService.get(id);
		model.addAttribute("archiveRespdept", archiveRespdept);
	    return "archive/archiveRespdept/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveRespdept:add")
	public R save( ArchiveRespdeptDO archiveRespdept){
		if(archiveRespdeptService.save(archiveRespdept)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveRespdept:edit")
	public R update( ArchiveRespdeptDO archiveRespdept){
		archiveRespdeptService.update(archiveRespdept);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveRespdept:remove")
	public R remove( String id){
		if(archiveRespdeptService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveRespdept:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveRespdeptService.batchRemove(ids);
		return R.ok();
	}
	
}
