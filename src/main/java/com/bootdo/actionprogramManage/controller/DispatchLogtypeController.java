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

import com.bootdo.actionprogramManage.domain.DispatchLogtypeDO;
import com.bootdo.actionprogramManage.service.DispatchLogtypeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 应急调度日志类别表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:16
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchLogtype")
public class DispatchLogtypeController {
	@Autowired
	private DispatchLogtypeService dispatchLogtypeService;
	
	@GetMapping()
	@RequiresPermissions("actionprogramManage:dispatchLogtype:dispatchLogtype")
	String DispatchLogtype(){
	    return "actionprogramManage/dispatchLogtype/dispatchLogtype";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("actionprogramManage:dispatchLogtype:dispatchLogtype")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchLogtypeDO> dispatchLogtypeList = dispatchLogtypeService.list(query);
		int total = dispatchLogtypeService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchLogtypeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("actionprogramManage:dispatchLogtype:add")
	String add(){
	    return "actionprogramManage/dispatchLogtype/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("actionprogramManage:dispatchLogtype:edit")
	String edit(@PathVariable("id") String id,Model model){
		DispatchLogtypeDO dispatchLogtype = dispatchLogtypeService.get(id);
		model.addAttribute("dispatchLogtype", dispatchLogtype);
	    return "actionprogramManage/dispatchLogtype/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("actionprogramManage:dispatchLogtype:add")
	public R save( DispatchLogtypeDO dispatchLogtype){
		if(dispatchLogtypeService.save(dispatchLogtype)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("actionprogramManage:dispatchLogtype:edit")
	public R update( DispatchLogtypeDO dispatchLogtype){
		dispatchLogtypeService.update(dispatchLogtype);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchLogtype:remove")
	public R remove( String id){
		if(dispatchLogtypeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchLogtype:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchLogtypeService.batchRemove(ids);
		return R.ok();
	}
	
}
