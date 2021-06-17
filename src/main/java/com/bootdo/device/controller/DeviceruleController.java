package com.bootdo.device.controller;

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

import com.bootdo.device.domain.DeviceruleDO;
import com.bootdo.device.service.DeviceruleService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 设备与报警阈值表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
 
@Controller
@RequestMapping("/device/devicerule")
public class DeviceruleController {
	@Autowired
	private DeviceruleService deviceruleService;
	
	@GetMapping()
	@RequiresPermissions("device:devicerule:devicerule")
	String Devicerule(){
	    return "device/devicerule/devicerule";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("device:devicerule:devicerule")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DeviceruleDO> deviceruleList = deviceruleService.list(query);
		int total = deviceruleService.count(query);
		PageUtils pageUtils = new PageUtils(deviceruleList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("device:devicerule:add")
	String add(){
	    return "device/devicerule/add";
	}

	@GetMapping("/edit/{drId}")
	@RequiresPermissions("device:devicerule:edit")
	String edit(@PathVariable("drId") Integer drId,Model model){
		DeviceruleDO devicerule = deviceruleService.get(drId);
		model.addAttribute("devicerule", devicerule);
	    return "device/devicerule/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("device:devicerule:add")
	public R save( DeviceruleDO devicerule){
		if(deviceruleService.save(devicerule)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("device:devicerule:edit")
	public R update( DeviceruleDO devicerule){
		deviceruleService.update(devicerule);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("device:devicerule:remove")
	public R remove( Integer drId){
		if(deviceruleService.remove(drId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("device:devicerule:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] drIds){
		deviceruleService.batchRemove(drIds);
		return R.ok();
	}
	
}
