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

import com.bootdo.support.entity.TwoleveltypeDO;
import com.bootdo.support.service.TwoleveltypeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 物资二级分类表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 12:00:52
 */
 
@Controller
@RequestMapping("/support/twoleveltype")
public class TwoleveltypeController {
	@Autowired
	private TwoleveltypeService twoleveltypeService;
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TwoleveltypeDO> twoleveltypeList = twoleveltypeService.list(query);
		int total = twoleveltypeService.count(query);
		PageUtils pageUtils = new PageUtils(twoleveltypeList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getAll")
	public List<TwoleveltypeDO> getAll(){
		return twoleveltypeService.getAll();
	}
	
	@GetMapping("/add")
	String add(){
	    return "support/twoleveltype/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		TwoleveltypeDO twoleveltype = twoleveltypeService.get(id);
		model.addAttribute("twoleveltype", twoleveltype);
	    return "support/twoleveltype/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( TwoleveltypeDO twoleveltype){
		if(twoleveltypeService.save(twoleveltype)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( TwoleveltypeDO twoleveltype){
		twoleveltypeService.update(twoleveltype);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(twoleveltypeService.remove(id)>0){
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
		twoleveltypeService.batchRemove(ids);
		return R.ok();
	}
	
}
