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

import com.bootdo.support.dto.EquiptypeDO;
import com.bootdo.support.service.EquiptypeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 装备类型表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-08 09:56:36
 */
 
@Controller
@RequestMapping("/support/equiptype")
public class EquiptypeController {
	@Autowired
	private EquiptypeService equiptypeService;
	
	@GetMapping()
	@RequiresPermissions("support:equiptype:equiptype")
	String Equiptype(){
	    return "support/equiptype/equiptype";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EquiptypeDO> equiptypeList = equiptypeService.list(query);
		int total = equiptypeService.count(query);
		PageUtils pageUtils = new PageUtils(equiptypeList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getAll")
	public List<Map<String,Object>> getAll(){
		//查询列表数据
		List<Map<String,Object>> equiptypeList = equiptypeService.getAll();
		return equiptypeList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("support:equiptype:add")
	String add(){
	    return "support/equiptype/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("support:equiptype:edit")
	String edit(@PathVariable("id") String id,Model model){
		EquiptypeDO equiptype = equiptypeService.get(id);
		model.addAttribute("equiptype", equiptype);
	    return "support/equiptype/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("support:equiptype:add")
	public R save( EquiptypeDO equiptype){
		if(equiptypeService.save(equiptype)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("support:equiptype:edit")
	public R update( EquiptypeDO equiptype){
		equiptypeService.update(equiptype);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("support:equiptype:remove")
	public R remove( String id){
		if(equiptypeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("support:equiptype:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		equiptypeService.batchRemove(ids);
		return R.ok();
	}
	
}
