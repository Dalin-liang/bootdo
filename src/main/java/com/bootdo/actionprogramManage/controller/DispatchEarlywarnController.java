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

import com.bootdo.actionprogramManage.domain.DispatchEarlywarnDO;
import com.bootdo.actionprogramManage.service.DispatchEarlywarnService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 执行方案的预警信息表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-22 19:36:57
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchEarlywarn")
public class DispatchEarlywarnController {
	@Autowired
	private DispatchEarlywarnService dispatchEarlywarnService;
	
	@GetMapping()
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:dispatchEarlywarn")
	String DispatchEarlywarn(){
	    return "actionprogramManage/dispatchEarlywarn/dispatchEarlywarn";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:dispatchEarlywarn")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchEarlywarnDO> dispatchEarlywarnList = dispatchEarlywarnService.list(query);
		int total = dispatchEarlywarnService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchEarlywarnList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:add")
	String add(){
	    return "actionprogramManage/dispatchEarlywarn/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:edit")
	String edit(@PathVariable("id") String id,Model model){
		DispatchEarlywarnDO dispatchEarlywarn = dispatchEarlywarnService.get(id);
		model.addAttribute("dispatchEarlywarn", dispatchEarlywarn);
	    return "actionprogramManage/dispatchEarlywarn/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:add")
	public R save( DispatchEarlywarnDO dispatchEarlywarn){
		if(dispatchEarlywarnService.save(dispatchEarlywarn)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:edit")
	public R update( DispatchEarlywarnDO dispatchEarlywarn){
		dispatchEarlywarnService.update(dispatchEarlywarn);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:remove")
	public R remove( String id){
		if(dispatchEarlywarnService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchEarlywarn:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchEarlywarnService.batchRemove(ids);
		return R.ok();
	}
	
}
