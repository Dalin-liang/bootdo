package com.bootdo.support.controller;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.support.entity.PresonWayPointDO;
import com.bootdo.support.service.PresonWayPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 执法人轨迹表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-15 09:44:23
 */
 
@Controller
@RequestMapping("/system/presonWayPoint")
public class PresonWayPointController {
	@Autowired
	private PresonWayPointService presonWayPointService;
	
	@GetMapping()
	String PresonWayPoint(){
	    return "system/presonWayPoint/presonWayPoint";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PresonWayPointDO> presonWayPointList = presonWayPointService.list(query);
		int total = presonWayPointService.count(query);
		PageUtils pageUtils = new PageUtils(presonWayPointList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "system/presonWayPoint/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		PresonWayPointDO presonWayPoint = presonWayPointService.get(id);
		model.addAttribute("presonWayPoint", presonWayPoint);
	    return "system/presonWayPoint/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( PresonWayPointDO presonWayPoint){
		if(presonWayPointService.save(presonWayPoint)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( PresonWayPointDO presonWayPoint){
		presonWayPointService.update(presonWayPoint);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(presonWayPointService.remove(id)>0){
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
		presonWayPointService.batchRemove(ids);
		return R.ok();
	}
	
}
