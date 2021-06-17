package com.bootdo.newData.controller;

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

import com.bootdo.newData.domain.ProtectPersonDO;
import com.bootdo.newData.service.ProtectPersonService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 09:53:35
 */
 
@Controller
@RequestMapping("/newData/protectPerson")
public class ProtectPersonController {
	@Autowired
	private ProtectPersonService protectPersonService;
	
	@GetMapping()
	String ProtectPerson(){
	    return "newData/protectPerson/protectPerson";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProtectPersonDO> protectPersonList = protectPersonService.list(query);
		int total = protectPersonService.count(query);
		PageUtils pageUtils = new PageUtils(protectPersonList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "newData/protectPerson/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("newData:protectPerson:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ProtectPersonDO protectPerson = protectPersonService.get(id);
		model.addAttribute("protectPerson", protectPerson);
	    return "newData/protectPerson/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("newData:protectPerson:add")
	public R save( ProtectPersonDO protectPerson){
		if(protectPersonService.save(protectPerson)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("newData:protectPerson:edit")
	public R update( ProtectPersonDO protectPerson){
		protectPersonService.update(protectPerson);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("newData:protectPerson:remove")
	public R remove( Integer id){
		if(protectPersonService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("newData:protectPerson:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		protectPersonService.batchRemove(ids);
		return R.ok();
	}
	
}
