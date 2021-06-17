package com.bootdo.support.controller;

import java.util.HashMap;
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

import com.bootdo.support.dto.KnowledgeDangertypeDO;
import com.bootdo.support.service.KnowledgeDangertypeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author oking
 * @email 1992lcg@163.com
 * @date 2019-11-04 16:46:24
 */
 
@Controller
@RequestMapping("/support/knowledgeDangertype")
public class KnowledgeDangertypeController {
	@Autowired
	private KnowledgeDangertypeService knowledgeDangertypeService;
	
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<KnowledgeDangertypeDO> knowledgeDangertypeList = knowledgeDangertypeService.list(query);
		int total = knowledgeDangertypeService.count(query);
		PageUtils pageUtils = new PageUtils(knowledgeDangertypeList, total);
		return pageUtils;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( KnowledgeDangertypeDO knowledgeDangertype){
		if(knowledgeDangertypeService.save(knowledgeDangertype)>0){
			return R.ok("保存成功");
		}
		return R.error("保存失败");
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( KnowledgeDangertypeDO knowledgeDangertype){
		knowledgeDangertypeService.update(knowledgeDangertype);
		return R.ok();
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String enabled){
		if(knowledgeDangertypeService.changeStatus(id ,enabled)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(knowledgeDangertypeService.remove(id)>0){
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
		knowledgeDangertypeService.batchRemove(ids);
		return R.ok();
	}
	
}
