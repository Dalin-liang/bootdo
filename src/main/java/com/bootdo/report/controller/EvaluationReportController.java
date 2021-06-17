package com.bootdo.report.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.service.CommonFileService;
import com.bootdo.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.report.domain.EvaluationReportDO;
import com.bootdo.report.service.EvaluationReportService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-04-02 16:27:24
 */
 
@Controller
@RequestMapping("/report/evaluationReport")
public class EvaluationReportController {
	public final static String UPLOAD_FILE_PATH = "D:\\bootdo\\evaluationReportFile\\";

	@Autowired
	private EvaluationReportService evaluationReportService;
	@Autowired
	private CommonFileService commonFileService;
	@Value("${bootdo.mappingPath}")
	private String mappingUrl;

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EvaluationReportDO> evaluationReportList = evaluationReportService.list(query);
		int total = evaluationReportService.count(query);
		PageUtils pageUtils = new PageUtils(evaluationReportList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/reportList")
	public PageUtils evaluationReportList(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<EvaluationReportDO> evaluationReportList = evaluationReportService.evaluationReportList(query);
		int total = evaluationReportService.evaluationReportCount(query);
		PageUtils pageUtils = new PageUtils(evaluationReportList, total);
		return pageUtils;
	}



	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( EvaluationReportDO evaluationReport){
		String evaluationReportId = UUID.randomUUID().toString().replace("-","");
        evaluationReport.setId(evaluationReportId);
        evaluationReport.setReportTime(new Date());
		if(evaluationReportService.save(evaluationReport)>0){
			String fileList = evaluationReport.getFileList();
			if(StringUtils.isNotEmpty(fileList)){
				JSONArray jsonArray = JSON.parseArray(fileList);
				if(jsonArray.size()>0){
					Date now = new Date();
					for (int i = 0; i < jsonArray.size(); i++){
						JSONObject jsonObject = JSON.parseObject(jsonArray.getString(i));

						String url = jsonObject.getString("url");
						CommonFileDO fileDO = new CommonFileDO();
						fileDO.setId(UUID.randomUUID().toString().replace("-", ""));
						fileDO.setRelationId(evaluationReportId);
						fileDO.setFileType(jsonObject.getInteger("type"));
						fileDO.setFileUrl(url);
						fileDO.setFilePhysicalAddress(UPLOAD_FILE_PATH+url);
						fileDO.setFromTableanme("archive_evaluation_report");
						fileDO.setCreateBy(ShiroUtils.getUserId().toString());
						fileDO.setCreateDate(now);

						String fileName = url.substring(url.lastIndexOf("\\")+1);
						fileDO.setFileName(fileName);
						commonFileService.save(fileDO);

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
	public R update( EvaluationReportDO evaluationReport){
        evaluationReport.setReportTime(new Date());
		if(evaluationReportService.update(evaluationReport)>0){
			String fileList = evaluationReport.getFileList();
			if(StringUtils.isNotEmpty(fileList)){
				JSONArray jsonArray = JSON.parseArray(fileList);
				if(jsonArray.size()>0){
					Date now = new Date();
					for (int i = 0; i < jsonArray.size(); i++){
						JSONObject jsonObject = JSON.parseObject(jsonArray.getString(i));

						String url = jsonObject.getString("url");
						CommonFileDO fileDO = new CommonFileDO();
						fileDO.setId(UUID.randomUUID().toString().replace("-", ""));
						fileDO.setRelationId(evaluationReport.getId());
						fileDO.setFileType(jsonObject.getInteger("type"));
						fileDO.setFileUrl(url);
						fileDO.setFilePhysicalAddress(UPLOAD_FILE_PATH+url);
						fileDO.setFromTableanme("archive_evaluation_report");
						fileDO.setCreateBy(ShiroUtils.getUserId().toString());
						fileDO.setCreateDate(now);

						String fileName = url.substring(url.lastIndexOf("\\")+1);
						fileDO.setFileName(fileName);
						commonFileService.save(fileDO);
					}
				}

			}
			return R.ok();
		}
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
	public List<CommonFileDO> getFileList(String evaluationReportId){
		Map<String, Object> map = new HashMap<>();
		map.put("relationId",evaluationReportId);
		map.put("fromTableanme","archive_evaluation_report");
		List<CommonFileDO> fileList = commonFileService.list(map);
		for(CommonFileDO file :fileList) {
			file.setFileUrl(mappingUrl+"/evaluationReportFile/"+file.getFileUrl());
		}
		return fileList;
	}


	@RequestMapping("/downloadFile")
	@ResponseBody
	public void downloadFile(HttpServletRequest request, HttpServletResponse response){
		String fileId=request.getParameter("id");
		CommonFileDO file = commonFileService.get(fileId);
		String fileName=file.getFileUrl().substring(file.getFileUrl().lastIndexOf("\\")+1);
		UploadUtils.downFile(response, fileName, file.getFilePhysicalAddress());
	}

	@RequestMapping("/delFile")
	@ResponseBody
	public R  delFile(HttpServletRequest request,HttpServletResponse response){
		String fileId=request.getParameter("id");
		CommonFileDO file = commonFileService.get(fileId);
		if( commonFileService.remove(fileId)>0) {
			UploadUtils.delFile(file.getFilePhysicalAddress());
			return R.ok();
		};
		return R.error();

	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(evaluationReportService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	

}
