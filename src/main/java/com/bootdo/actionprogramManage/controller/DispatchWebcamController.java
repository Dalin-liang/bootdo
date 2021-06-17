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

import com.bootdo.actionprogramManage.domain.DispatchWebcamDO;
import com.bootdo.actionprogramManage.service.DispatchWebcamService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 摄像头调度表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:17
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchWebcam")
public class DispatchWebcamController {
	@Autowired
	private DispatchWebcamService dispatchWebcamService;
	
	@GetMapping()
	@RequiresPermissions("actionprogramManage:dispatchWebcam:dispatchWebcam")
	String DispatchWebcam(){
	    return "actionprogramManage/dispatchWebcam/dispatchWebcam";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("actionprogramManage:dispatchWebcam:dispatchWebcam")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchWebcamDO> dispatchWebcamList = dispatchWebcamService.list(query);
		int total = dispatchWebcamService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchWebcamList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("actionprogramManage:dispatchWebcam:add")
	String add(){
	    return "actionprogramManage/dispatchWebcam/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("actionprogramManage:dispatchWebcam:edit")
	String edit(@PathVariable("id") String id,Model model){
		DispatchWebcamDO dispatchWebcam = dispatchWebcamService.get(id);
		model.addAttribute("dispatchWebcam", dispatchWebcam);
	    return "actionprogramManage/dispatchWebcam/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("actionprogramManage:dispatchWebcam:add")
	public R save( DispatchWebcamDO dispatchWebcam){
		if(dispatchWebcamService.save(dispatchWebcam)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("actionprogramManage:dispatchWebcam:edit")
	public R update( DispatchWebcamDO dispatchWebcam){
		dispatchWebcamService.update(dispatchWebcam);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchWebcam:remove")
	public R remove( String id){
		if(dispatchWebcamService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("actionprogramManage:dispatchWebcam:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchWebcamService.batchRemove(ids);
		return R.ok();
	}
	
}
