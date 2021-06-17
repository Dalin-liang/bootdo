package com.bootdo.sms.controller;

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

import com.bootdo.sms.domain.SmsSendRecordDO;
import com.bootdo.sms.service.SmsSendRecordService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-27 09:56:18
 */
 
@Controller
@RequestMapping("/sms/smsSendRecord")
public class SmsSendRecordController {
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@GetMapping()
	@RequiresPermissions("sms:smsSendRecord:smsSendRecord")
	String SmsSendRecord(){
	    return "sms/smsSendRecord/smsSendRecord";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sms:smsSendRecord:smsSendRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SmsSendRecordDO> smsSendRecordList = smsSendRecordService.list(query);
		int total = smsSendRecordService.count(query);
		PageUtils pageUtils = new PageUtils(smsSendRecordList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("sms:smsSendRecord:add")
	String add(){
	    return "sms/smsSendRecord/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("sms:smsSendRecord:edit")
	String edit(@PathVariable("id") String id,Model model){
		SmsSendRecordDO smsSendRecord = smsSendRecordService.get(id);
		model.addAttribute("smsSendRecord", smsSendRecord);
	    return "sms/smsSendRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sms:smsSendRecord:add")
	public R save( SmsSendRecordDO smsSendRecord){
		if(smsSendRecordService.save(smsSendRecord)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sms:smsSendRecord:edit")
	public R update( SmsSendRecordDO smsSendRecord){
		smsSendRecordService.update(smsSendRecord);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("sms:smsSendRecord:remove")
	public R remove( String id){
		if(smsSendRecordService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("sms:smsSendRecord:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		smsSendRecordService.batchRemove(ids);
		return R.ok();
	}
	
}
