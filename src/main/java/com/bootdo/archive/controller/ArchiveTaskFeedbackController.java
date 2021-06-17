package com.bootdo.archive.controller;

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

import com.bootdo.archive.domain.ArchiveTaskFeedbackDO;
import com.bootdo.archive.service.ArchiveTaskFeedbackService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 任务反馈表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveTaskFeedback")
public class ArchiveTaskFeedbackController {
	@Autowired
	private ArchiveTaskFeedbackService archiveTaskFeedbackService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveTaskFeedback:archiveTaskFeedback")
	String ArchiveTaskFeedback(){
	    return "archive/archiveTaskFeedback/archiveTaskFeedback";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveTaskFeedback:archiveTaskFeedback")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveTaskFeedbackDO> archiveTaskFeedbackList = archiveTaskFeedbackService.list(query);
		int total = archiveTaskFeedbackService.count(query);
		PageUtils pageUtils = new PageUtils(archiveTaskFeedbackList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveTaskFeedback:add")
	String add(){
	    return "archive/archiveTaskFeedback/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveTaskFeedback:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveTaskFeedbackDO archiveTaskFeedback = archiveTaskFeedbackService.get(id);
		model.addAttribute("archiveTaskFeedback", archiveTaskFeedback);
	    return "archive/archiveTaskFeedback/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveTaskFeedback:add")
	public R save( ArchiveTaskFeedbackDO archiveTaskFeedback){
		if(archiveTaskFeedbackService.save(archiveTaskFeedback)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveTaskFeedback:edit")
	public R update( ArchiveTaskFeedbackDO archiveTaskFeedback){
		archiveTaskFeedbackService.update(archiveTaskFeedback);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveTaskFeedback:remove")
	public R remove( String id){
		if(archiveTaskFeedbackService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveTaskFeedback:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveTaskFeedbackService.batchRemove(ids);
		return R.ok();
	}
	
}
