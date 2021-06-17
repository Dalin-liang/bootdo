package com.bootdo.common.controller;

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

import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.service.CommonFileService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-14 14:32:54
 */
 
@Controller
@RequestMapping("/common/commonFile")
public class CommonFileController {
	@Autowired
	private CommonFileService commonFileService;
	
	@GetMapping()
	@RequiresPermissions("common:commonFile:commonFile")
	String CommonFile(){
	    return "common/commonFile/commonFile";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:commonFile:commonFile")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CommonFileDO> commonFileList = commonFileService.list(query);
		int total = commonFileService.count(query);
		PageUtils pageUtils = new PageUtils(commonFileList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("common:commonFile:add")
	String add(){
	    return "common/commonFile/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:commonFile:edit")
	String edit(@PathVariable("id") String id,Model model){
		CommonFileDO commonFile = commonFileService.get(id);
		model.addAttribute("commonFile", commonFile);
	    return "common/commonFile/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:commonFile:add")
	public R save( CommonFileDO commonFile){
		if(commonFileService.save(commonFile)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:commonFile:edit")
	public R update( CommonFileDO commonFile){
		commonFileService.update(commonFile);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:commonFile:remove")
	public R remove( String id){
		if(commonFileService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:commonFile:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		commonFileService.batchRemove(ids);
		return R.ok();
	}
	
}
