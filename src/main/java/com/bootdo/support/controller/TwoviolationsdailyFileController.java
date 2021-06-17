package com.bootdo.support.controller;

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

import com.bootdo.support.dto.TwoviolationsdailyFileDO;
import com.bootdo.support.service.TwoviolationsdailyFileService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 两违日志附件表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 14:13:35
 */
 
@Controller
@RequestMapping("/support/twoviolationsdailyFile")
public class TwoviolationsdailyFileController {
	@Autowired
	private TwoviolationsdailyFileService twoviolationsdailyFileService;
	
	@GetMapping()
	@RequiresPermissions("support:twoviolationsdailyFile:twoviolationsdailyFile")
	String TwoviolationsdailyFile(){
	    return "support/twoviolationsdailyFile/twoviolationsdailyFile";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("support:twoviolationsdailyFile:twoviolationsdailyFile")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TwoviolationsdailyFileDO> twoviolationsdailyFileList = twoviolationsdailyFileService.list(query);
		int total = twoviolationsdailyFileService.count(query);
		PageUtils pageUtils = new PageUtils(twoviolationsdailyFileList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("support:twoviolationsdailyFile:add")
	String add(){
	    return "support/twoviolationsdailyFile/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("support:twoviolationsdailyFile:edit")
	String edit(@PathVariable("id") String id,Model model){
		TwoviolationsdailyFileDO twoviolationsdailyFile = twoviolationsdailyFileService.get(id);
		model.addAttribute("twoviolationsdailyFile", twoviolationsdailyFile);
	    return "support/twoviolationsdailyFile/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("support:twoviolationsdailyFile:add")
	public R save( TwoviolationsdailyFileDO twoviolationsdailyFile){
		if(twoviolationsdailyFileService.save(twoviolationsdailyFile)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("support:twoviolationsdailyFile:edit")
	public R update( TwoviolationsdailyFileDO twoviolationsdailyFile){
		twoviolationsdailyFileService.update(twoviolationsdailyFile);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("support:twoviolationsdailyFile:remove")
	public R remove( String id){
		if(twoviolationsdailyFileService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("support:twoviolationsdailyFile:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		twoviolationsdailyFileService.batchRemove(ids);
		return R.ok();
	}
	
}
