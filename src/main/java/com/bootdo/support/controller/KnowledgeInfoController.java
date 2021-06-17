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

import com.bootdo.support.dto.KnowledgeInfoDO;
import com.bootdo.support.service.KnowledgeDangertypeService;
import com.bootdo.support.service.KnowledgeInfoService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 知识库名单表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
 
@Controller
@RequestMapping("/support/knowledgeInfo")
public class KnowledgeInfoController {
	@Autowired
	private KnowledgeInfoService knowledgeInfoService;
	@Autowired
	private KnowledgeDangertypeService knowledgeDangertypeService;
	
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<KnowledgeInfoDO> knowledgeInfoList = knowledgeInfoService.list(query);
		int total = knowledgeInfoService.count(query);
		PageUtils pageUtils = new PageUtils(knowledgeInfoList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@PostMapping("/getDangerType")
	public Map<String,Object> getDangerType(){
		Map<String,Object> map =new HashMap<String, Object>();
		List<Map<String,Object>> dangerTypeList = knowledgeDangertypeService.getDangerType();
		map.put("dangerTypeList", dangerTypeList);
		map.put("msg", "true");
		return map;
	}
	

	/**
	 * 获取
	 */
	@ResponseBody
	@PostMapping("/getknowledgeDetails")
	public Map<String,Object> getknowledgeDetails( String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map = knowledgeInfoService.getknowledgeDetails(id);
		map.put("msg", "true");
		return map;
	}
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( String info,String detail,String danger,String emergency,String physical,String protect){
		if(knowledgeInfoService.addKnowledge(info,detail,danger,emergency,physical,protect)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( String info,String detail,String danger,String emergency,String physical,String protect){
		knowledgeInfoService.updateKnowledge(info,detail,danger,emergency,physical,protect);
		return R.ok();
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String enabled){
		if(knowledgeInfoService.changeStatus(id ,enabled)>0){
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
			if(knowledgeInfoService.deleteKnowledge(id)>0){
				return R.ok("true");
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
		knowledgeInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
