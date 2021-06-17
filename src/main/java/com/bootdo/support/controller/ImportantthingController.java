package com.bootdo.support.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.service.SchedulingService;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.support.dto.ImportantthingDO;
import com.bootdo.support.service.ImportantthingService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 要情表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-24 09:27:55
 */
 
@Controller
@RequestMapping("/support/importantthing")
public class ImportantthingController {
	@Autowired
	private ImportantthingService importantthingService;
	@Autowired
	private SupportDeptService supportDeptService;
	@Autowired
	private SchedulingService schedulingService;
	

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ImportantthingDO> importantthingList = importantthingService.list(query);
		int total = importantthingService.count(query);
		PageUtils pageUtils = new PageUtils(importantthingList, total);
		return pageUtils;
	}

	@RequestMapping("/getUser")
	@ResponseBody
	public PageUtils query(@RequestParam Map<String, Object> params){
		String userId = ShiroUtils.getUser().getUserId().toString();
		params.put("userId",userId);

		//查询当前用户作为部门负责人所在的部门id
		List<String> deptIdsList = supportDeptService.getDeptIdsByUserId(userId);
		if(deptIdsList !=null && deptIdsList.size()>0){
			params.put("deptIdsList",deptIdsList);
		}else{
			params.put("deptIdsList",null);
		}

		//查询列表数据
		Query query = new Query(params);
		List<Map<String,Object>> importantthingList = importantthingService.getUser(query);
		int total = importantthingService.getUserCount(query);
		PageUtils pageUtils = new PageUtils(importantthingList, total);
		return pageUtils;
	}
	

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( ImportantthingDO importantthing){
		UserDO userDo= ShiroUtils.getUser();
		importantthing.setId(UUID.randomUUID().toString().replace("-", ""));
		importantthing.setCreateBy(userDo.getUsername());
		importantthing.setCreateDate(new Date());
		if(importantthingService.save(importantthing)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( ImportantthingDO importantthing){
		UserDO userDo= ShiroUtils.getUser();
		importantthing.setUpdateBy(userDo.getUsername());
		importantthing.setUpdateDate(new Date());
		importantthingService.update(importantthing);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(importantthingService.remove(id)>0){
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
		importantthingService.batchRemove(ids);
		return R.ok();
	}
	
}
