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

import com.bootdo.support.entity.OneleveltypeDO;
import com.bootdo.support.service.OneleveltypeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 物资一级分类表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-30 10:01:07
 */
 
@Controller
@RequestMapping("/support/oneleveltype")
public class OneleveltypeController {
	@Autowired
	private OneleveltypeService oneleveltypeService;
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OneleveltypeDO> oneleveltypeList = oneleveltypeService.list(query);
		int total = oneleveltypeService.count(query);
		PageUtils pageUtils = new PageUtils(oneleveltypeList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getAll")
	public List<OneleveltypeDO> getAll(){
		return oneleveltypeService.getAll();
	}
	
	@GetMapping("/add")
	String add(){
	    return "support/oneleveltype/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		OneleveltypeDO oneleveltype = oneleveltypeService.get(id);
		model.addAttribute("oneleveltype", oneleveltype);
	    return "support/oneleveltype/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( OneleveltypeDO oneleveltype){
		if(oneleveltypeService.save(oneleveltype)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( OneleveltypeDO oneleveltype){
		oneleveltypeService.update(oneleveltype);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(oneleveltypeService.remove(id)>0){
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
		oneleveltypeService.batchRemove(ids);
		return R.ok();
	}
	
}
