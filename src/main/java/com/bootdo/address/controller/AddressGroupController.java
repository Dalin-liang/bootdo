package com.bootdo.address.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.address.service.AddressGroupBookService;
import com.bootdo.support.service.DeptPersonService;
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

import com.bootdo.address.domain.AddressGroupDO;
import com.bootdo.address.service.AddressGroupService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 通讯录组表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-05-22 11:16:47
 */
 
@Controller
@RequestMapping("/address/addressGroup")
public class AddressGroupController {
	@Autowired
	private AddressGroupService addressGroupService;
	@Autowired
	private DeptPersonService deptPersonService;
	@Autowired
	private AddressGroupBookService addressGroupBookService;


	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AddressGroupDO> addressGroupList = addressGroupService.list(query);
		int total = addressGroupService.count(query);
		PageUtils pageUtils = new PageUtils(addressGroupList, total);
		return pageUtils;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( AddressGroupDO addressGroup){
		if(addressGroupService.save(addressGroup)>0){
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/saveGroupAndPerson")
	public R saveGroupAndPerson(String group,String personList){
		if(addressGroupService.saveGroupAndPerson(group,personList)>0){
			return R.ok("添加成功");
		}else{
			return R.error("添加失败");
		}

	}

	@ResponseBody
	@PostMapping("/updateGroupAndPerson")
	public R updateGroupAndPerson(String group,String personList){
		addressGroupService.updateGroupAndPerson(group,personList);
		return R.ok("修改成功");
	}

	@PostMapping( "/removeGroupAndPerson")
	@ResponseBody
	public R removeGroupAndPerson( String id){
		if(addressGroupService.removeGroupAndPerson(id)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( AddressGroupDO addressGroup){
		addressGroupService.update(addressGroup);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(addressGroupService.remove(id)>0){
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
		addressGroupService.batchRemove(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus(String id , String enabled){
		if(addressGroupService.changeStatus(id ,enabled)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}

	@ResponseBody
	@PostMapping("/getPersonList")
	public R getPersonList(){
		List<Map<String ,Object>> list = deptPersonService.getPersonList();
		Map<String, Object> map = new HashMap<>();
		map.put("personList",list);
		return R.ok(map);
	}

	@ResponseBody
	@PostMapping("/getPersonInfo")
	public R getPersonInfo(String id){
		List<Map<String ,Object>> list = addressGroupBookService.getBookByGroupId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("personInfo",list);
		return R.ok(map);
	}
	
}
