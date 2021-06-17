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

import com.bootdo.device.domain.RealDataDO;
import com.bootdo.device.service.RealDataService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 监测实时
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
 
@Controller
@RequestMapping("/device/realData")
public class RealDataController {
	@Autowired
	private RealDataService realDataService;
	
	@GetMapping()
	@RequiresPermissions("device:realData:realData")
	String RealData(){
	    return "device/realData/realData";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("device:realData:realData")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<RealDataDO> realDataList = realDataService.list(query);
		int total = realDataService.count(query);
		PageUtils pageUtils = new PageUtils(realDataList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("device:realData:add")
	String add(){
	    return "device/realData/add";
	}

	@GetMapping("/edit/{rdId}")
	@RequiresPermissions("device:realData:edit")
	String edit(@PathVariable("rdId") Integer rdId,Model model){
		RealDataDO realData = realDataService.get(rdId);
		model.addAttribute("realData", realData);
	    return "device/realData/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("device:realData:add")
	public R save( RealDataDO realData){
		if(realDataService.save(realData)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("device:realData:edit")
	public R update( RealDataDO realData){
		realDataService.update(realData);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("device:realData:remove")
	public R remove( Integer rdId){
		if(realDataService.remove(rdId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("device:realData:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] rdIds){
		realDataService.batchRemove(rdIds);
		return R.ok();
	}
	
}
