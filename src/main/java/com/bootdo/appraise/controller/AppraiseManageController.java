package com.bootdo.appraise.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
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

import com.bootdo.appraise.domain.AppraiseManageDO;
import com.bootdo.appraise.service.AppraiseManageService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-04-02 10:15:36
 */
 
@Controller
@RequestMapping("/appraise/appraiseManage")
public class AppraiseManageController {
	@Autowired
	private AppraiseManageService appraiseManageService;

	
	@ResponseBody
	@GetMapping("/list")
	public JSONObject list(AppraiseManageDO appraiseManageDO,@RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){
		JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = appraiseManageService.list(appraiseManageDO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
	}
	
	@GetMapping("/add")
	String add(){
	    return "appraise/appraiseManageAdd";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		AppraiseManageDO appraiseManage = appraiseManageService.get(id);
		model.addAttribute("appraiseManage", appraiseManage);
	    return "appraise/appraiseManageAdd";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( AppraiseManageDO appraiseManage){
		String uuid=UUID.randomUUID().toString().replace("-","");
		appraiseManage.setId(uuid);
		appraiseManage.setTime(new Date());
		if(appraiseManageService.save(appraiseManage)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( AppraiseManageDO appraiseManage){
		appraiseManageService.update(appraiseManage);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(appraiseManageService.remove(id)>0){
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
		appraiseManageService.batchRemove(ids);
		return R.ok();
	}
	
}
