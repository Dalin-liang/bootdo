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

import com.bootdo.device.domain.AlertdataDO;
import com.bootdo.device.service.AlertdataService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 预警/报警数据
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
 
@Controller
@RequestMapping("/device/alertdata")
public class AlertdataController {
	@Autowired
	private AlertdataService alertdataService;
	
	@GetMapping()
	@RequiresPermissions("device:alertdata:alertdata")
	String Alertdata(){
	    return "device/alertdata/alertdata";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("device:alertdata:alertdata")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AlertdataDO> alertdataList = alertdataService.list(query);
		int total = alertdataService.count(query);
		PageUtils pageUtils = new PageUtils(alertdataList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("device:alertdata:add")
	String add(){
	    return "device/alertdata/add";
	}

	@GetMapping("/edit/{adId}")
	@RequiresPermissions("device:alertdata:edit")
	String edit(@PathVariable("adId") Integer adId,Model model){
		AlertdataDO alertdata = alertdataService.get(adId);
		model.addAttribute("alertdata", alertdata);
	    return "device/alertdata/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("device:alertdata:add")
	public R save( AlertdataDO alertdata){
		if(alertdataService.save(alertdata)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("device:alertdata:edit")
	public R update( AlertdataDO alertdata){
		alertdataService.update(alertdata);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("device:alertdata:remove")
	public R remove( Integer adId){
		if(alertdataService.remove(adId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("device:alertdata:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] adIds){
		alertdataService.batchRemove(adIds);
		return R.ok();
	}
	
}
