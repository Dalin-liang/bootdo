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

import com.bootdo.actionprogramManage.domain.DispatchRespdeptDO;
import com.bootdo.actionprogramManage.service.DispatchRespdeptService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 执行方案的预案响应部门归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-13 09:21:47
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchRespdept")
public class DispatchRespdeptController {
	@Autowired
	private DispatchRespdeptService dispatchRespdeptService;
	
	@GetMapping()
	@RequiresPermissions("actionprogramManage:dispatchRespdept:dispatchRespdept")
	String DispatchRespdept(){
	    return "actionprogramManage/dispatchRespdept/dispatchRespdept";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("actionprogramManage:dispatchRespdept:dispatchRespdept")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchRespdeptDO> dispatchRespdeptList = dispatchRespdeptService.list(query);
		int total = dispatchRespdeptService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchRespdeptList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("actionprogramManage:dispatchRespdept:add")
	String add(){
	    return "actionprogramManage/dispatchRespdept/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("actionprogramManage:dispatchRespdept:edit")
	String edit(@PathVariable("id") String id,Model model){
		DispatchRespdeptDO dispatchRespdept = dispatchRespdeptService.get(id);
		model.addAttribute("dispatchRespdept", dispatchRespdept);
	    return "actionprogramManage/dispatchRespdept/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("actionprogramManage:dispatchRespdept:add")
	public R save( DispatchRespdeptDO dispatchRespdept){
		if(dispatchRespdeptService.save(dispatchRespdept)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("actionprogramManage:dispatchRespdept:edit")
	public R update( DispatchRespdeptDO dispatchRespdept){
		dispatchRespdeptService.update(dispatchRespdept);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchRespdept:remove")
	public R remove( String id){
		if(dispatchRespdeptService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchRespdept:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchRespdeptService.batchRemove(ids);
		return R.ok();
	}
	
}
