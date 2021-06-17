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

import com.bootdo.device.domain.DeviceDO;
import com.bootdo.device.service.DeviceService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 物联设备表
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
 
@Controller
@RequestMapping("/device/device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	
	@GetMapping()
	@RequiresPermissions("device:device:device")
	String Device(){
	    return "device/device/device";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("device:device:device")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DeviceDO> deviceList = deviceService.list(query);
		int total = deviceService.count(query);
		PageUtils pageUtils = new PageUtils(deviceList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("device:device:add")
	String add(){
	    return "device/device/add";
	}

	@GetMapping("/edit/{deId}")
	@RequiresPermissions("device:device:edit")
	String edit(@PathVariable("deId") Integer deId,Model model){
		DeviceDO device = deviceService.get(deId);
		model.addAttribute("device", device);
	    return "device/device/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("device:device:add")
	public R save( DeviceDO device){
		if(deviceService.save(device)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("device:device:edit")
	public R update( DeviceDO device){
		deviceService.update(device);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("device:device:remove")
	public R remove( Integer deId){
		if(deviceService.remove(deId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("device:device:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] deIds){
		deviceService.batchRemove(deIds);
		return R.ok();
	}
	
}
