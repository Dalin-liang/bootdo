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

import com.bootdo.archive.domain.ArchivePlanmainDO;
import com.bootdo.archive.service.ArchivePlanmainService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 执行方案的预案归档主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archivePlanmain")
public class ArchivePlanmainController {
	@Autowired
	private ArchivePlanmainService archivePlanmainService;
	
	@GetMapping()
	@RequiresPermissions("archive:archivePlanmain:archivePlanmain")
	String ArchivePlanmain(){
	    return "archive/archivePlanmain/archivePlanmain";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archivePlanmain:archivePlanmain")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchivePlanmainDO> archivePlanmainList = archivePlanmainService.list(query);
		int total = archivePlanmainService.count(query);
		PageUtils pageUtils = new PageUtils(archivePlanmainList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archivePlanmain:add")
	String add(){
	    return "archive/archivePlanmain/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archivePlanmain:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchivePlanmainDO archivePlanmain = archivePlanmainService.get(id);
		model.addAttribute("archivePlanmain", archivePlanmain);
	    return "archive/archivePlanmain/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archivePlanmain:add")
	public R save( ArchivePlanmainDO archivePlanmain){
		if(archivePlanmainService.save(archivePlanmain)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archivePlanmain:edit")
	public R update( ArchivePlanmainDO archivePlanmain){
		archivePlanmainService.update(archivePlanmain);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archivePlanmain:remove")
	public R remove( String id){
		if(archivePlanmainService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archivePlanmain:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archivePlanmainService.batchRemove(ids);
		return R.ok();
	}
	
}
