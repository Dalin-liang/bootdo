package com.bootdo.support.controller;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.support.dto.KnowledgeDO;
import com.bootdo.support.service.KnowledgeService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.service.CommonFileService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.UploadUtils;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.planManage.service.PlanEarlywarnTypeService;

/**
 * 知识库
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-13 10:21:50
 */
 
@Controller
@RequestMapping("/support/knowledge")
public class KnowledgeController {
	@Autowired
	private KnowledgeService knowledgeService;
	@Autowired
	private PlanEarlywarnTypeService planEarlywarnTypeService;
	@Autowired
	private CommonFileService commonFileService;
	@Value("${bootdo.uploadPath}")
	private String Uploadpath;
	
	@GetMapping()
	@RequiresPermissions("support:knowledge:knowledge")
	String Knowledge(){
	    return "support/knowledge/knowledge";
	}
	
	@ResponseBody
	@GetMapping("/list")
/*	@RequiresPermissions("support:knowledge:knowledge")
*/	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        List<Map<String,Object>> knowledgeList = knowledgeService.list(query);
		int total = knowledgeService.count(query);
		PageUtils pageUtils = new PageUtils(knowledgeList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getWarnType")
	public List<PlanEarlywarnTypeDO> getWarnType(){
		Map<String,Object>paramsMap=new HashMap<String, Object>();
		List<PlanEarlywarnTypeDO> planEarlywarnType = planEarlywarnTypeService.list(paramsMap);

		return planEarlywarnType;
	}
	

	
	@GetMapping("/add")
/*	@RequiresPermissions("support:knowledge:add")
*/	String add(){
	    return "support/knowledge/add";
	}

	@GetMapping("/edit/{id}")
/*	@RequiresPermissions("support:knowledge:edit")
*/	String edit(@PathVariable("id") String id,Model model){
		KnowledgeDO knowledge = knowledgeService.get(id);
		model.addAttribute("knowledge", knowledge);
	    return "support/knowledge/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
/*	@RequiresPermissions("support:knowledge:add")
*/	public R save( KnowledgeDO knowledge){
        UserDO userDo= ShiroUtils.getUser();
        String id=UUID.randomUUID().toString().replace("-", "");
        knowledge.setId(id);
		knowledge.setCreateBy(userDo.getUsername());
		knowledge.setCreateDate(new Date());
		if(knowledgeService.save(knowledge)>0){
			R r = new R();
			r.put("code", 0);
			r.put("msg", "操作成功");
			r.put("relationId", id);
			return r;
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
/*	@RequiresPermissions("support:knowledge:edit")
*/	public R update( KnowledgeDO knowledge){
		knowledgeService.update(knowledge);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
/*	@RequiresPermissions("support:knowledge:remove")
*/	public R remove( String id){
		if(knowledgeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("support:knowledge:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		knowledgeService.batchRemove(ids);
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping("/upload")
	public Map<String,Object>upload(MultipartFile file,HttpServletRequest request) throws IOException{
		Map<String,Object>resultMap=new HashMap<>();
		String relationId=request.getParameter("relationId")+"";
		UserDO userDo= ShiroUtils.getUser();
		try {
			String path=Uploadpath+"knowledge/";
			String fileName=UploadUtils.uploadFile(file, path);
			if(StringUtils.isNotBlank(fileName)) {
				if(StringUtils.isBlank(relationId)) {
					 relationId=UUID.randomUUID().toString().replace("-", "");
				}
				resultMap.put("relationId", relationId);
				CommonFileDO commonFileDO=new CommonFileDO();
				commonFileDO.setId(UUID.randomUUID().toString().replace("-", ""));
				commonFileDO.setCreateBy(userDo.getUsername());
				commonFileDO.setCreateDate(new Date());
				commonFileDO.setRelationId(relationId);
				commonFileDO.setFileType(1);
				commonFileDO.setFilePhysicalAddress(path+fileName);
				commonFileDO.setFileUrl("/knowledge/"+fileName);
				commonFileDO.setFromTableanme("safeguard_knowledge");
				commonFileDO.setFileName(file.getOriginalFilename());
				commonFileService.save(commonFileDO);
			}
			resultMap.put("code", 0);
		} catch (Exception e) {
			resultMap.put("code", 1);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
		
	}
	
	@ResponseBody
	@RequestMapping("/delFile")
	public Map<String,Object>delFile(String fileId) throws IOException{
		Map<String,Object>resultMap=new HashMap<>();
		UserDO userDo= ShiroUtils.getUser();
		int code=0;
		try {
	
			CommonFileDO commonFileDO=	commonFileService.get(fileId);
			if(commonFileDO!=null) {
				 code=UploadUtils.delFile(commonFileDO.getFilePhysicalAddress());
				if(code==1) {
					commonFileService.remove(commonFileDO.getId());
				}
				
			}
			resultMap.put("code", code);
		} catch (Exception e) {
			resultMap.put("code", code);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
		
	}
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletResponse response,String fileId) throws IOException{
		Map<String,Object>map=new HashMap<>();
		map.put("relationId", fileId);
		List<CommonFileDO> fileDO=commonFileService.list(map);
		if(fileDO!=null&&fileDO.size()>0) {
			 UploadUtils.downFile(response, fileDO.get(0).getFileName(), fileDO.get(0).getFilePhysicalAddress());
		}
	}

	
	
}
