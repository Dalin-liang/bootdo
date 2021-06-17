package com.bootdo.actionprogramManage.controller;

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

import com.bootdo.actionprogramManage.domain.DispatchPlanMainDO;
import com.bootdo.actionprogramManage.service.DispatchPlanMainService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 执行方案的预案归档主表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchPlanMain")
public class DispatchPlanMainController {
	@Autowired
	private DispatchPlanMainService dispatchPlanMainService;
	
	@GetMapping()
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:dispatchPlanMain")
	String DispatchPlanMain(){
	    return "actionprogramManage/dispatchPlanMain/dispatchPlanMain";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:dispatchPlanMain")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchPlanMainDO> dispatchPlanMainList = dispatchPlanMainService.list(query);
		int total = dispatchPlanMainService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchPlanMainList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:add")
	String add(){
	    return "actionprogramManage/dispatchPlanMain/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:edit")
	String edit(@PathVariable("id") String id,Model model){
		DispatchPlanMainDO dispatchPlanMain = dispatchPlanMainService.get(id);
		model.addAttribute("dispatchPlanMain", dispatchPlanMain);
	    return "actionprogramManage/dispatchPlanMain/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:add")
	public R save( DispatchPlanMainDO dispatchPlanMain){
		if(dispatchPlanMainService.save(dispatchPlanMain)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:edit")
	public R update( DispatchPlanMainDO dispatchPlanMain){
		dispatchPlanMainService.update(dispatchPlanMain);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:remove")
	public R remove( String id){
		if(dispatchPlanMainService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchPlanMain:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchPlanMainService.batchRemove(ids);
		return R.ok();
	}
	
}
