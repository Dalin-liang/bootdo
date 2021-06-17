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

import com.bootdo.newData.domain.DutyPersonDO;
import com.bootdo.newData.service.DutyPersonService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-05 14:45:32
 */
 
@Controller
@RequestMapping("/newData/dutyPerson")
public class DutyPersonController {
	@Autowired
	private DutyPersonService dutyPersonService;
	
	@GetMapping()
	String DutyPerson(){
	    return "newData/dutyPerson/dutyPerson";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DutyPersonDO> dutyPersonList = dutyPersonService.list(query);
		int total = dutyPersonService.count(query);
		PageUtils pageUtils = new PageUtils(dutyPersonList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "newData/dutyPerson/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id,Model model){
		DutyPersonDO dutyPerson = dutyPersonService.get(id);
		model.addAttribute("dutyPerson", dutyPerson);
	    return "newData/dutyPerson/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( DutyPersonDO dutyPerson){
		if(dutyPersonService.save(dutyPerson)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( DutyPersonDO dutyPerson){
		dutyPersonService.update(dutyPerson);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( Integer id){
		if(dutyPersonService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Integer[] ids){
		dutyPersonService.batchRemove(ids);
		return R.ok();
	}
	
}
