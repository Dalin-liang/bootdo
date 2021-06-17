package com.bootdo.support.controller;

import java.util.Date;
import java.util.List;

import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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

import com.bootdo.support.dto.EquipstorehouseDO;
import com.bootdo.support.service.EquipstorehouseService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 装备存储库
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-08 11:31:42
 */
 
@Controller
@RequestMapping("/support/equipstorehouse")
public class EquipstorehouseController {
	@Autowired
	private EquipstorehouseService equipstorehouseService;
	
	@GetMapping()
	String Equipstorehouse(){
	    return "support/equipstorehouse/equipstorehouse";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EquipstorehouseDO> equipstorehouseList = equipstorehouseService.list(query);
		int total = equipstorehouseService.count(query);
		PageUtils pageUtils = new PageUtils(equipstorehouseList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getAll")
	public List<Map<String,Object>> getAll(){
		//查询列表数据
        List<Map<String,Object>>equipstorehouseList = equipstorehouseService.getAll();

		return equipstorehouseList;
	}
	
	@GetMapping("/add")
	String add(){
	    return "support/equipstorehouse/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		EquipstorehouseDO equipstorehouse = equipstorehouseService.get(id);
		model.addAttribute("equipstorehouse", equipstorehouse);
	    return "support/equipstorehouse/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( EquipstorehouseDO equipstorehouse){
		String uuid= UUID.randomUUID().toString();
		UserDO userDo= ShiroUtils.getUser();
		equipstorehouse.setId(uuid);
		equipstorehouse.setEnabled(1);
		equipstorehouse.setCreateBy(userDo.getUsername());
		equipstorehouse.setCreateDate(new Date());
		if(equipstorehouseService.save(equipstorehouse)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( EquipstorehouseDO equipstorehouse){
		UserDO userDo= ShiroUtils.getUser();
		equipstorehouse.setUpdateBy(userDo.getUsername());
		equipstorehouse.setUpdateDate(new Date());
		equipstorehouseService.update(equipstorehouse);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove(@RequestParam("id")String id){
		if(equipstorehouseService.remove(id)>0){
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
		equipstorehouseService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/logicalDelete")
	@ResponseBody
	public R logicalDelete(@RequestParam("id") String id){
		if(equipstorehouseService.logicalDelete(id)>0){
			return R.ok();
		}
		return R.error();
	}
	
}
