package com.bootdo.support.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.support.entity.SourceDO;
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

import com.bootdo.support.entity.SourceMenuDO;
import com.bootdo.support.service.SourceMenuService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 接报来源详细类目表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:12:25
 */
 
@Controller
@RequestMapping("/support/sourceMenu")
public class SourceMenuController {
	@Autowired
	private SourceMenuService sourceMenuService;
	

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SourceMenuDO> sourceMenuList = sourceMenuService.list(query);
		int total = sourceMenuService.count(query);
		PageUtils pageUtils = new PageUtils(sourceMenuList, total);
		return pageUtils;
	}

	@ResponseBody
	@PostMapping("/getWatchSource")
	public Map<String,Object> getDpetList(){
		Map<String,Object> map =new HashMap<String, Object>();
		List<SourceDO> watchSource = sourceMenuService.getWatchSource();
		map.put("watchSource", watchSource);
		map.put("msg", "true");
		return map;
	}

	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String enabled){
		if(sourceMenuService.changeStatus(id ,enabled)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}
	

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( SourceMenuDO sourceMenu){
		if(sourceMenuService.save(sourceMenu)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( SourceMenuDO sourceMenu){
		sourceMenuService.update(sourceMenu);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( Long id){
		if(sourceMenuService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Long[] ids){
		sourceMenuService.batchRemove(ids);
		return R.ok();
	}
	
}
