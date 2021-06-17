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

import com.bootdo.planManage.domain.PlanAccidentTypeDO;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.planManage.service.PlanAccidentTypeService;
import com.bootdo.planManage.service.PlanEarlywarnTypeService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.common.utils.PageQuery;
import com.bootdo.common.utils.PageResultMap;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;

/**
 * 预警类别
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-07-26 19:38:50
 */
 
@Controller
@RequestMapping("/planManage/planEarlywarnType")
public class PlanEarlywarnTypeController {
	@Autowired
	private PlanEarlywarnTypeService planEarlywarnTypeService;
	@Autowired
	private PlanAccidentTypeService planAccidentTypeService;
	
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PlanEarlywarnTypeDO> planEarlywarnTypeList = planEarlywarnTypeService.list(query);
		int total = planEarlywarnTypeService.count(query);
		PageUtils pageUtils = new PageUtils(planEarlywarnTypeList, total);
		return pageUtils;
	}
	
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( PlanEarlywarnTypeDO planEarlywarnType){
		if(planEarlywarnTypeService.save(planEarlywarnType)>0){
			return R.ok();
		}
		return R.error();
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String status){
		if(planEarlywarnTypeService.changeStatus(id ,status)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}
	
	@ResponseBody
	@PostMapping("/getSelectData")
	public Map<String,Object> getSelectData(){
		Map<String,Object> map =new HashMap<String, Object>();
		List<PlanAccidentTypeDO> accidentTypeList = planAccidentTypeService.getAccidentType();
		map.put("accidentTypeList", accidentTypeList);
		map.put("msg", "true");
		return map;
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( PlanEarlywarnTypeDO planEarlywarnType){
		planEarlywarnTypeService.update(planEarlywarnType);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(planEarlywarnTypeService.remove(id)>0){
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
		planEarlywarnTypeService.batchRemove(ids);
		return R.ok();
	}
	
}
