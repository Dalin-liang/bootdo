package com.bootdo.support.controller;

import java.io.*;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bootdo.common.utils.*;
import com.bootdo.support.dto.DailyFileDO;
import com.bootdo.support.service.DailyFileService;
import com.bootdo.support.service.SupportDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.support.dto.DailyDO;
import com.bootdo.support.dto.SupportDeptPersonDTO;
import com.bootdo.support.service.DailyService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * 值班日报/要情编辑
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-09 17:24:25
 */
 
@Controller
@RequestMapping("/support/daily")
public class DailyController {
	public final static String UPLOAD_FILE_PATH = "D:\\bootdo\\dailyUploadFile\\";

	@Autowired
	private DailyService dailyService;
	@Autowired
	private SupportDeptService supportDeptService;
	@Autowired
	private DailyFileService dailyFileService;
	@Value("${bootdo.mappingPath}")
	private String mappingUrl;
	@GetMapping()
	@RequiresPermissions("support:daily:daily")
	String Daily(){
	    return "support/daily/daily";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("support:daily:daily")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DailyDO> dailyList = dailyService.list(query);
		int total = dailyService.count(query);
		PageUtils pageUtils = new PageUtils(dailyList, total);
		return pageUtils;
	}
	
    @RequestMapping("/getUser")
    @ResponseBody
    public PageUtils query(@RequestParam Map<String, Object> params){
		String userId = ShiroUtils.getUser().getUserId().toString();
		params.put("userId",userId);

		//查询当前用户作为部门负责人所在的部门id
		List<String> deptIdsList = supportDeptService.getDeptIdsByUserId(userId);
		if(deptIdsList !=null && deptIdsList.size()>0){
			params.put("deptIdsList",deptIdsList);
		}else{
			params.put("deptIdsList",null);
		}

		Query query = new Query(params);
		List<Map<String,Object>> dailyList = dailyService.getUser(query);
		int total = dailyService.getUserCount(query);
		PageUtils pageUtils = new PageUtils(dailyList, total);
		return pageUtils;
    }
	
	@GetMapping("/add")
	@RequiresPermissions("support:daily:add")
	String add(){
	    return "support/daily/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("support:daily:edit")
	String edit(@PathVariable("id") String id,Model model){
		DailyDO daily = dailyService.get(id);
		model.addAttribute("daily", daily);
	    return "support/daily/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( DailyDO daily){
	     UserDO userDo= ShiroUtils.getUser();
		daily.setId(UUID.randomUUID().toString().replace("-", ""));
		daily.setCreateBy(userDo.getUsername());
		daily.setCreateDate(new Date());
		if(dailyService.save(daily)>0){
			String fileList = daily.getFileList();
			if(StringUtils.isNotEmpty(fileList)){
				JSONArray jsonArray = JSON.parseArray(fileList);
				if(jsonArray.size()>0){
					DailyFileDO dailyFileDO = new DailyFileDO();
					Date now = new Date();
					for (int i = 0; i < jsonArray.size(); i++){
						JSONObject jsonObject = JSON.parseObject(jsonArray.getString(i));
						dailyFileDO.setId(UUID.randomUUID().toString().replace("-", ""));
						dailyFileDO.setDailyId(daily.getId());
						dailyFileDO.setType(jsonObject.getInteger("type"));
						dailyFileDO.setUrl(jsonObject.getString("url"));
						String sort=jsonObject.getString("sort");
						dailyFileDO.setSort(sort.indexOf("-")>-1?Integer.parseInt(sort.substring(sort.length()-1, sort.length())):Integer.parseInt(sort));
						dailyFileDO.setPhysicalAddress(UPLOAD_FILE_PATH+jsonObject.getString("url"));
						dailyFileDO.setCreateDate(now);
						dailyFileService.save(dailyFileDO);
					}
				}

			}

			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( DailyDO daily){
		UserDO userDo= ShiroUtils.getUser();
		daily.setUpdateBy(userDo.getUsername());
		daily.setUpdateDate(new Date());
		if(dailyService.update(daily)>0){
			String fileList = daily.getFileList();
			if(StringUtils.isNotEmpty(fileList)){
				JSONArray jsonArray = JSON.parseArray(fileList);
				if(jsonArray.size()>0){
					DailyFileDO dailyFileDO = new DailyFileDO();
					Date now = new Date();
					for (int i = 0; i < jsonArray.size(); i++){
						JSONObject jsonObject = JSON.parseObject(jsonArray.getString(i));
						dailyFileDO.setId(UUID.randomUUID().toString().replace("-", ""));
						dailyFileDO.setDailyId(daily.getId());
						dailyFileDO.setType(jsonObject.getInteger("type"));
						dailyFileDO.setUrl(jsonObject.getString("url"));
						String sort=jsonObject.getString("sort");
						dailyFileDO.setSort(sort.indexOf("-")>-1?Integer.parseInt(sort.substring(sort.length()-1, sort.length())):Integer.parseInt(sort));
						dailyFileDO.setPhysicalAddress(UPLOAD_FILE_PATH+jsonObject.getString("url"));
						dailyFileDO.setCreateDate(now);
						dailyFileService.save(dailyFileDO);
					}
				}

			}
			return R.ok();
		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/removeUser")
	@ResponseBody
	public R removeUser( String id){
		dailyFileService.removeByDailyId(id);
		if(dailyService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}

	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("support:daily:remove")
	public R remove( String id){
		if(dailyService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("support:daily:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		dailyService.batchRemove(ids);
		return R.ok();
	}

	@RequestMapping(value = "uploadFile")
    @ResponseBody
	public R uploadImage(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String basePath = ShiroUtils.getUserId().toString()+ File.separator + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String retUrl = basePath+File.separator;
			String path = UPLOAD_FILE_PATH+retUrl;

            File myPath = new File(path);
			if ( !myPath.exists()){//若此目录不存在，则创建之
				myPath.mkdirs();
				System.out.println("创建文件夹路径为："+ path);
			}
			BufferedOutputStream out = null;
			try {
				retUrl += file.getOriginalFilename();
				out = new BufferedOutputStream(new FileOutputStream(new File(path, file.getOriginalFilename())));
				out.write(file.getBytes());
			} catch (Exception e) {
				return R.error(1,"error");
			}finally {
				if(out !=null){
					try {
						out.flush();
						out.close();
					}catch (IOException e1){
						e1.printStackTrace();
					}
				}
			}
			return R.ok(retUrl);
		} else {
			return R.error(1,"文件为空");
		}
	}

	@RequestMapping("/getFileList")
	@ResponseBody
	public List<DailyFileDO> getFileList(String dailyId){
		List<DailyFileDO> deptIdsList = dailyFileService.getByDailyId(dailyId);
		for(DailyFileDO file :deptIdsList) {
			file.setUrl(mappingUrl+"/dailyUploadFile/"+file.getUrl());
		}
		return deptIdsList;
	}
	
	@RequestMapping("/downloadFile")
	@ResponseBody
	public void downloadFile(HttpServletRequest request,HttpServletResponse response){
		String dailyId=request.getParameter("id");
		DailyFileDO file = dailyFileService.get(dailyId);
		String fileName=file.getUrl().substring(file.getUrl().lastIndexOf("\\")+1);
		UploadUtils.downFile(response, fileName, file.getPhysicalAddress());
	}	
	
	@RequestMapping("/delFile")
	@ResponseBody
	public R  delFile(HttpServletRequest request,HttpServletResponse response){
		String dailyId=request.getParameter("id");
		DailyFileDO file = dailyFileService.get(dailyId);
		if( dailyFileService.remove(dailyId)>0) {
			UploadUtils.delFile(file.getPhysicalAddress());
			return R.ok();
		};
		return R.error();
		
	}
	
}
