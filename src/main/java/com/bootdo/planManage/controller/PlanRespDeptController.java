package com.bootdo.planManage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.planManage.domain.PlanRespDeptDO;
import com.bootdo.planManage.service.PlanRespDeptService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;

/**
 * 预案响应部门
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
 
@Controller
@RequestMapping("/planManage/planRespDept")
public class PlanRespDeptController {
	@Autowired
	private PlanRespDeptService planRespDeptService;
	
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PlanRespDeptDO> planRespDeptList = planRespDeptService.list(query);
		int total = planRespDeptService.count(query);
		PageUtils pageUtils = new PageUtils(planRespDeptList, total);
		return pageUtils;
	}
	

	@GetMapping("/edit/{id}")
	public Map<String, Object> edit(@PathVariable("id") String id,Model model){
		PlanRespDeptDO planRespDept = planRespDeptService.get(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planRespDept", planRespDept);
		return map;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( PlanRespDeptDO planRespDept){
		if(planRespDeptService.save(planRespDept)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( PlanRespDeptDO planRespDept){
		planRespDeptService.update(planRespDept);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(planRespDeptService.remove(id)>0){
				return R.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(500,"删除数据失败，请先删除相关的表记录！");
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] ids){
		planRespDeptService.batchRemove(ids);
		return R.ok();
	}
	
}
