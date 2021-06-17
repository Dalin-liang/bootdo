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

import com.bootdo.archive.domain.ArchiveWebcamDO;
import com.bootdo.archive.service.ArchiveWebcamService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 摄像头调度表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:08
 */
 
@Controller
@RequestMapping("/archive/archiveWebcam")
public class ArchiveWebcamController {
	@Autowired
	private ArchiveWebcamService archiveWebcamService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveWebcam:archiveWebcam")
	String ArchiveWebcam(){
	    return "archive/archiveWebcam/archiveWebcam";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveWebcam:archiveWebcam")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveWebcamDO> archiveWebcamList = archiveWebcamService.list(query);
		int total = archiveWebcamService.count(query);
		PageUtils pageUtils = new PageUtils(archiveWebcamList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveWebcam:add")
	String add(){
	    return "archive/archiveWebcam/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveWebcam:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveWebcamDO archiveWebcam = archiveWebcamService.get(id);
		model.addAttribute("archiveWebcam", archiveWebcam);
	    return "archive/archiveWebcam/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveWebcam:add")
	public R save( ArchiveWebcamDO archiveWebcam){
		if(archiveWebcamService.save(archiveWebcam)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveWebcam:edit")
	public R update( ArchiveWebcamDO archiveWebcam){
		archiveWebcamService.update(archiveWebcam);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveWebcam:remove")
	public R remove( String id){
		if(archiveWebcamService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveWebcam:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveWebcamService.batchRemove(ids);
		return R.ok();
	}
	
}
