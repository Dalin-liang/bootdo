package com.bootdo.planManage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.common.utils.PageQuery;
import com.bootdo.common.utils.PageResultMap;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.domain.PlanAccidentTypeDO;
import com.bootdo.planManage.service.PlanAccidentTypeService;
import com.bootdo.system.domain.UserDO;

/**
 * 事故类型管理
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:49
 */
 
@Controller
@RequestMapping("/planManage/planAccidentType")
public class PlanAccidentTypeController {
	@Autowired
	private PlanAccidentTypeService planAccidentTypeService;
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PlanAccidentTypeDO> planAccidentTypeList = planAccidentTypeService.list(query);
		int total = planAccidentTypeService.count(query);
		PageUtils pageUtils = new PageUtils(planAccidentTypeList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@PostMapping("/getDpetList")
	public Map<String,Object> getDpetList(){
		Map<String,Object> map =new HashMap<String, Object>();
		List<Map<String,Object>> dpetList = planAccidentTypeService.getDpetList();
		map.put("dpetList", dpetList);
		map.put("msg", "true");
		return map;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( PlanAccidentTypeDO planAccidentType){
		if(planAccidentTypeService.save(planAccidentType)>0){
			return R.ok("保存成功");
		}
		return R.error("保存失败");
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String status){
		if(planAccidentTypeService.changeStatus(id ,status)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}
	
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( PlanAccidentTypeDO planAccidentType){
		planAccidentTypeService.update(planAccidentType);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(planAccidentTypeService.remove(id)>0){
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
		planAccidentTypeService.batchRemove(ids);
		return R.ok();
	}
	
}
