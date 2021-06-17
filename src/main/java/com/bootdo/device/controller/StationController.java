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

import com.bootdo.device.domain.StationDO;
import com.bootdo.device.service.StationService;
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
@RequestMapping("/device/station")
public class StationController {
	@Autowired
	private StationService stationService;
	
	@GetMapping()
	@RequiresPermissions("device:station:station")
	String Station(){
	    return "device/station/station";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("device:station:station")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<StationDO> stationList = stationService.list(query);
		int total = stationService.count(query);
		PageUtils pageUtils = new PageUtils(stationList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("device:station:add")
	String add(){
	    return "device/station/add";
	}

	@GetMapping("/edit/{stId}")
	@RequiresPermissions("device:station:edit")
	String edit(@PathVariable("stId") Integer stId,Model model){
		StationDO station = stationService.get(stId);
		model.addAttribute("station", station);
	    return "device/station/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("device:station:add")
	public R save( StationDO station){
		if(stationService.save(station)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("device:station:edit")
	public R update( StationDO station){
		stationService.update(station);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("device:station:remove")
	public R remove( Integer stId){
		if(stationService.remove(stId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("device:station:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] stIds){
		stationService.batchRemove(stIds);
		return R.ok();
	}
	
}
