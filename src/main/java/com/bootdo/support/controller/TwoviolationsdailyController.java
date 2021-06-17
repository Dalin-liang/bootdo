package com.bootdo.support.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.support.dto.TwoviolationsdailyFileDO;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.support.service.TwoviolationsdailyFileService;
import com.bootdo.system.domain.UserDO;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

import com.bootdo.support.dto.TwoviolationsdailyDO;
import com.bootdo.support.service.TwoviolationsdailyService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 两违日志表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 14:13:34
 */
 
@Controller
@RequestMapping("/support/twoviolationsdaily")
public class TwoviolationsdailyController {
	public final static String UPLOAD_FILE_PATH = "D:\\bootdo\\twoviolationsdailyUploadFile\\";
	@Value("${bootdo.mappingPath}")
	private String mappingUrl;

	@Autowired
	private TwoviolationsdailyService twoviolationsdailyService;
	@Autowired
	private SupportDeptService supportDeptService;
	@Autowired
	private TwoviolationsdailyFileService twoviolationsdailyFileService;


	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TwoviolationsdailyDO> twoviolationsdailyList = twoviolationsdailyService.list(query);
		int total = twoviolationsdailyService.count(query);
		PageUtils pageUtils = new PageUtils(twoviolationsdailyList, total);
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
		List<Map<String,Object>> dailyList = twoviolationsdailyService.getUser(query);
		int total = twoviolationsdailyService.getUserCount(query);
		PageUtils pageUtils = new PageUtils(dailyList, total);
		return pageUtils;
	}
	

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( TwoviolationsdailyDO twoviolationsdaily){
		UserDO userDo= ShiroUtils.getUser();
		twoviolationsdaily.setId(UUID.randomUUID().toString().replace("-", ""));
		twoviolationsdaily.setCreateBy(userDo.getUsername());
		twoviolationsdaily.setCreateDate(new Date());
		if(twoviolationsdailyService.save(twoviolationsdaily)>0){
			String fileList = twoviolationsdaily.getFileList();
			if(StringUtils.isNotEmpty(fileList)){
				JSONArray jsonArray = JSON.parseArray(fileList);
				if(jsonArray.size()>0){
					TwoviolationsdailyFileDO twoviolationsdailyFileDO = new TwoviolationsdailyFileDO();
					Date now = new Date();
					for (int i = 0; i < jsonArray.size(); i++){
						JSONObject jsonObject = JSON.parseObject(jsonArray.getString(i));
						twoviolationsdailyFileDO.setId(UUID.randomUUID().toString().replace("-", ""));
						twoviolationsdailyFileDO.setTwoviolationsdailyId(twoviolationsdaily.getId());
						twoviolationsdailyFileDO.setType(jsonObject.getInteger("type"));
						twoviolationsdailyFileDO.setUrl(jsonObject.getString("url"));
						String sort=jsonObject.getString("sort");
						twoviolationsdailyFileDO.setSort(sort.indexOf("-")>-1?Integer.parseInt(sort.substring(sort.length()-1, sort.length())):Integer.parseInt(sort));
						twoviolationsdailyFileDO.setPhysicalAddress(UPLOAD_FILE_PATH+jsonObject.getString("url"));
						twoviolationsdailyFileDO.setCreateDate(now);
						twoviolationsdailyFileService.save(twoviolationsdailyFileDO);
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
	public R update( TwoviolationsdailyDO twoviolationsdaily){
		UserDO userDo= ShiroUtils.getUser();
		twoviolationsdaily.setUpdateBy(userDo.getUsername());
		twoviolationsdaily.setUpdateDate(new Date());
		if(twoviolationsdailyService.update(twoviolationsdaily)>0){
			String fileList = twoviolationsdaily.getFileList();
			if(StringUtils.isNotEmpty(fileList)){
				JSONArray jsonArray = JSON.parseArray(fileList);
				if(jsonArray.size()>0){
					TwoviolationsdailyFileDO twoviolationsdailyFileDO = new TwoviolationsdailyFileDO();
					Date now = new Date();
					for (int i = 0; i < jsonArray.size(); i++){
						JSONObject jsonObject = JSON.parseObject(jsonArray.getString(i));
						twoviolationsdailyFileDO.setId(UUID.randomUUID().toString().replace("-", ""));
						twoviolationsdailyFileDO.setTwoviolationsdailyId(twoviolationsdaily.getId());
						twoviolationsdailyFileDO.setType(jsonObject.getInteger("type"));
						twoviolationsdailyFileDO.setUrl(jsonObject.getString("url"));
						String sort=jsonObject.getString("sort");
						twoviolationsdailyFileDO.setSort(sort.indexOf("-")>-1?Integer.parseInt(sort.substring(sort.length()-1, sort.length())):Integer.parseInt(sort));
						twoviolationsdailyFileDO.setPhysicalAddress(UPLOAD_FILE_PATH+jsonObject.getString("url"));
						twoviolationsdailyFileDO.setCreateDate(now);
						twoviolationsdailyFileService.save(twoviolationsdailyFileDO);
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
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(twoviolationsdailyService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("support:twoviolationsdaily:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		twoviolationsdailyService.batchRemove(ids);
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
	public List<TwoviolationsdailyFileDO> getFileList(String twoviolationsdailyId){
		List<TwoviolationsdailyFileDO> deptIdsList = twoviolationsdailyFileService.getByTwoviolationsdailyId(twoviolationsdailyId);
		for(TwoviolationsdailyFileDO file :deptIdsList) {
			file.setUrl(mappingUrl+"/twoviolationsdailyUploadFile/"+file.getUrl());
		}
		return deptIdsList;
	}

	@RequestMapping("/downloadFile")
	@ResponseBody
	public void downloadFile(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		TwoviolationsdailyFileDO file = twoviolationsdailyFileService.get(id);
		String fileName=file.getUrl().substring(file.getUrl().lastIndexOf("\\")+1);
		UploadUtils.downFile(response, fileName, file.getPhysicalAddress());
	}

	@RequestMapping("/delFile")
	@ResponseBody
	public R  delFile(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		TwoviolationsdailyFileDO file = twoviolationsdailyFileService.get(id);
		if( twoviolationsdailyFileService.remove(id)>0) {
			UploadUtils.delFile(file.getPhysicalAddress());
			return R.ok();
		};
		return R.error();

	}

	/**
	 * 导出excel
	 */
	@GetMapping(value = "/expExcel")
	@ResponseBody
	public String  exportFile(@RequestParam Map<String, Object> params,
							  HttpServletRequest request,
							  HttpServletResponse response) {
		try {
			String fileName = "两违日志信息.xlsx";
			SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(500);
			/**
			 * 两违日志信息
			 */
			String userId = ShiroUtils.getUser().getUserId().toString();
			params.put("userId",userId);

			//查询当前用户作为部门负责人所在的部门id
			List<String> deptIdsList = supportDeptService.getDeptIdsByUserId(userId);
			if(deptIdsList !=null && deptIdsList.size()>0){
				params.put("deptIdsList",deptIdsList);
			}else{
				params.put("deptIdsList",null);
			}

			List<TwoviolationsdailyDO.TwoviolationsdailyExpDO> twoviolationsdailyExpDOList =
					twoviolationsdailyService.exportExcel(params);
			new ExportExcel(sxssfWorkbook,"两违日志信息", TwoviolationsdailyDO.TwoviolationsdailyExpDO.class)
					.setDataList(twoviolationsdailyExpDOList).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
