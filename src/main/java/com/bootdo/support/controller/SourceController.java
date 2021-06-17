package com.bootdo.support.controller;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.entity.SourceDO;
import com.bootdo.support.service.SourceService;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 接报途径信息来源表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-25 08:50:54
 */
 
@Controller
@RequestMapping("/system/source")
public class SourceController {
	@Autowired
	private SourceService sourceService;
	
	@GetMapping()
	String Source(){
	    return "system/source/source";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SourceDO> sourceList = sourceService.list(query);
		int total = sourceService.count(query);
		PageUtils pageUtils = new PageUtils(sourceList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/getSourceALL")
	public Map<String, Object> getSourceALL(){
		//查询列表数据
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("sort", "sort_no");
		parm.put("order", "asc");
		List<SourceDO> sourceList = sourceService.list(parm);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceList",sourceList);
		return map;
	}
	
	@ResponseBody
	@GetMapping("/getSourceByType")
	public List<SourceDO> getSourceByType(){
		//查询列表数据
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("sort", "sort_no");
		parm.put("order", "asc");
		List<SourceDO> sourceList = sourceService.getSourceByType(new HashMap<String, Object>());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceList",sourceList);
		return sourceList;
	}
	
	@GetMapping("/add")
	String add(){
	    return "system/source/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		SourceDO source = sourceService.get(id);
		model.addAttribute("source", source);
	    return "system/source/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( SourceDO source){
		UserDO userDo = ShiroUtils.getUser();
		source.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		source.setCreateBy(userDo.getUsername());
		source.setCreateDate(new Date());
		source.setIsStart(1);
		if(sourceService.save(source)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( SourceDO source){
		UserDO userDo = ShiroUtils.getUser();
		source.setUpdateBy(userDo.getUsername());
		source.setUpdateDate(new Date());
		sourceService.update(source);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(sourceService.remove(id)>0){
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
		sourceService.batchRemove(ids);
		return R.ok();
	}
	
}
