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

import com.bootdo.archive.domain.ArchiveTaskFeedbackDetailDO;
import com.bootdo.archive.service.ArchiveTaskFeedbackDetailService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 任务反馈归档明细表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveTaskFeedbackDetail")
public class ArchiveTaskFeedbackDetailController {
	@Autowired
	private ArchiveTaskFeedbackDetailService archiveTaskFeedbackDetailService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:archiveTaskFeedbackDetail")
	String ArchiveTaskFeedbackDetail(){
	    return "archive/archiveTaskFeedbackDetail/archiveTaskFeedbackDetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:archiveTaskFeedbackDetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveTaskFeedbackDetailDO> archiveTaskFeedbackDetailList = archiveTaskFeedbackDetailService.list(query);
		int total = archiveTaskFeedbackDetailService.count(query);
		PageUtils pageUtils = new PageUtils(archiveTaskFeedbackDetailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:add")
	String add(){
	    return "archive/archiveTaskFeedbackDetail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveTaskFeedbackDetailDO archiveTaskFeedbackDetail = archiveTaskFeedbackDetailService.get(id);
		model.addAttribute("archiveTaskFeedbackDetail", archiveTaskFeedbackDetail);
	    return "archive/archiveTaskFeedbackDetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:add")
	public R save( ArchiveTaskFeedbackDetailDO archiveTaskFeedbackDetail){
		if(archiveTaskFeedbackDetailService.save(archiveTaskFeedbackDetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:edit")
	public R update( ArchiveTaskFeedbackDetailDO archiveTaskFeedbackDetail){
		archiveTaskFeedbackDetailService.update(archiveTaskFeedbackDetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:remove")
	public R remove( String id){
		if(archiveTaskFeedbackDetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveTaskFeedbackDetail:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveTaskFeedbackDetailService.batchRemove(ids);
		return R.ok();
	}
	
}
