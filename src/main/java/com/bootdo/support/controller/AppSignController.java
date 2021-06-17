package com.bootdo.support.controller;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.support.entity.AppSignDO;
import com.bootdo.support.service.AppSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 签到表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-15 09:44:23
 */
 
@Controller
@RequestMapping("/system/appSign")
public class AppSignController {
	@Autowired
	private AppSignService appSignService;
	
	@GetMapping()
	String AppSign(){
	    return "system/appSign/appSign";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AppSignDO> appSignList = appSignService.list(query);
		int total = appSignService.count(query);
		PageUtils pageUtils = new PageUtils(appSignList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "system/appSign/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		AppSignDO appSign = appSignService.get(id);
		model.addAttribute("appSign", appSign);
	    return "system/appSign/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( AppSignDO appSign){
		if(appSignService.save(appSign)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( AppSignDO appSign){
		appSignService.update(appSign);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(appSignService.remove(id)>0){
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
		appSignService.batchRemove(ids);
		return R.ok();
	}
	
}
