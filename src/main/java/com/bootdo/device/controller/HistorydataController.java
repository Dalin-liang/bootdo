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

import com.bootdo.device.domain.HistorydataDO;
import com.bootdo.device.service.HistorydataService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 历史数据
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-16 11:40:41
 */
 
@Controller
@RequestMapping("/device/historydata")
public class HistorydataController {
	@Autowired
	private HistorydataService historydataService;
	
	@GetMapping()
	@RequiresPermissions("device:historydata:historydata")
	String Historydata(){
	    return "device/historydata/historydata";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("device:historydata:historydata")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<HistorydataDO> historydataList = historydataService.list(query);
		int total = historydataService.count(query);
		PageUtils pageUtils = new PageUtils(historydataList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("device:historydata:add")
	String add(){
	    return "device/historydata/add";
	}

	@GetMapping("/edit/{hdId}")
	@RequiresPermissions("device:historydata:edit")
	String edit(@PathVariable("hdId") Integer hdId,Model model){
		HistorydataDO historydata = historydataService.get(hdId);
		model.addAttribute("historydata", historydata);
	    return "device/historydata/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("device:historydata:add")
	public R save( HistorydataDO historydata){
		if(historydataService.save(historydata)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("device:historydata:edit")
	public R update( HistorydataDO historydata){
		historydataService.update(historydata);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("device:historydata:remove")
	public R remove( Integer hdId){
		if(historydataService.remove(hdId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("device:historydata:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] hdIds){
		historydataService.batchRemove(hdIds);
		return R.ok();
	}
	
}
