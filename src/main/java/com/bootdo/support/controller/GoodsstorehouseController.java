package com.bootdo.support.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.GoodsstorehouseDO;
import com.bootdo.support.service.GoodsstorehouseService;
import com.bootdo.system.domain.UserDO;
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

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 保障库(储备库)
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-15 15:08:27
 */
 
@Controller
@RequestMapping("/support/goodsstorehouse")
public class GoodsstorehouseController {
	@Autowired
	private GoodsstorehouseService goodsstorehouseService;
	
	@GetMapping()
	String Goodsstorehouse(){
	    return "system/goodsstorehouse/goodsstorehouse";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GoodsstorehouseDO> goodsstorehouseList = goodsstorehouseService.list(query);
		int total = goodsstorehouseService.count(query);
		PageUtils pageUtils = new PageUtils(goodsstorehouseList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "system/goodsstorehouse/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id,Model model){
		GoodsstorehouseDO goodsstorehouse = goodsstorehouseService.get(id);
		model.addAttribute("goodsstorehouse", goodsstorehouse);
	    return "system/goodsstorehouse/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( GoodsstorehouseDO goodsstorehouse){
		String uuid= UUID.randomUUID().toString().replace("-", "");
		UserDO userDo= ShiroUtils.getUser();
		goodsstorehouse.setId(uuid);
		goodsstorehouse.setEnabled(1);
		goodsstorehouse.setCreateBy(userDo.getUsername());
		goodsstorehouse.setCreateDate(new Date());
		if(goodsstorehouseService.save(goodsstorehouse)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( GoodsstorehouseDO goodsstorehouse){
		UserDO userDo= ShiroUtils.getUser();
		goodsstorehouse.setUpdateBy(userDo.getUsername());
		goodsstorehouse.setUpdateDate(new Date());
		goodsstorehouseService.update(goodsstorehouse);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		try {
			if(goodsstorehouseService.remove(id)>0){
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
		goodsstorehouseService.batchRemove(ids);
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
		if(goodsstorehouseService.logicalDelete(id)>0){
			return R.ok();
		}
		return R.error();
	}
	
}
