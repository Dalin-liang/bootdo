package com.bootdo.support.controller;

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

import com.bootdo.support.dto.EquipstatusDO;
import com.bootdo.support.service.EquipstatusService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 装备状态管理
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-08 10:59:20
 */
 
@Controller
@RequestMapping("/support/equipstatus")
public class EquipstatusController {
	@Autowired
	private EquipstatusService equipstatusService;
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EquipstatusDO> equipstatusList = equipstatusService.list(query);
		int total = equipstatusService.count(query);
		PageUtils pageUtils = new PageUtils(equipstatusList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getAll")
	public List<Map<String,Object>> getAll(){
		//查询列表数据
		 List<Map<String,Object>> equipstatusList = equipstatusService.getAll();
		return equipstatusList;
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String enabled){
		if(equipstatusService.changeStatus(id ,enabled)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}
	

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( EquipstatusDO equipstatus){
		if(equipstatusService.save(equipstatus)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( EquipstatusDO equipstatus){
		equipstatusService.update(equipstatus);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(equipstatusService.remove(id)>0){
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
		equipstatusService.batchRemove(ids);
		return R.ok();
	}
	
}
