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

import com.bootdo.archive.domain.ArchiveEarlywarnDO;
import com.bootdo.archive.service.ArchiveEarlywarnService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 执行方案的预警信息归档表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-24 11:57:07
 */
 
@Controller
@RequestMapping("/archive/archiveEarlywarn")
public class ArchiveEarlywarnController {
	@Autowired
	private ArchiveEarlywarnService archiveEarlywarnService;
	
	@GetMapping()
	@RequiresPermissions("archive:archiveEarlywarn:archiveEarlywarn")
	String ArchiveEarlywarn(){
	    return "archive/archiveEarlywarn/archiveEarlywarn";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("archive:archiveEarlywarn:archiveEarlywarn")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArchiveEarlywarnDO> archiveEarlywarnList = archiveEarlywarnService.list(query);
		int total = archiveEarlywarnService.count(query);
		PageUtils pageUtils = new PageUtils(archiveEarlywarnList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("archive:archiveEarlywarn:add")
	String add(){
	    return "archive/archiveEarlywarn/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("archive:archiveEarlywarn:edit")
	String edit(@PathVariable("id") String id,Model model){
		ArchiveEarlywarnDO archiveEarlywarn = archiveEarlywarnService.get(id);
		model.addAttribute("archiveEarlywarn", archiveEarlywarn);
	    return "archive/archiveEarlywarn/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("archive:archiveEarlywarn:add")
	public R save( ArchiveEarlywarnDO archiveEarlywarn){
		if(archiveEarlywarnService.save(archiveEarlywarn)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("archive:archiveEarlywarn:edit")
	public R update( ArchiveEarlywarnDO archiveEarlywarn){
		archiveEarlywarnService.update(archiveEarlywarn);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("archive:archiveEarlywarn:remove")
	public R remove( String id){
		if(archiveEarlywarnService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("archive:archiveEarlywarn:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		archiveEarlywarnService.batchRemove(ids);
		return R.ok();
	}
	
}
