package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportGeoInfo;
import com.bootdo.support.service.GeotypeService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 地理信息类别表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-13 11:04:28
 */
 
@Controller
@RequestMapping("/geotype")
public class GeotypeController {
	@Autowired
	private GeotypeService geotypeService;

	@ResponseBody
	@GetMapping("/query")
	public JSONObject list(GeotypeDO geotypeDO,
						  @RequestParam(value = "pageSize",required = false) String limit,
						  @RequestParam(value = "pageNumber",required = false) String offset){
		JSONObject json=new JSONObject();
		int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
		int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
		PageHelper.startPage(pageNum,pageSize);
		List<Map<String,Object>> rs = geotypeService.get(geotypeDO);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
		json.put("rows",rs);
		json.put("total",(int)pageInfo.getTotal());
		return json;
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/insert")
	public int insert( GeotypeDO geotype){
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		geotype.setId(id);
		UserDO userDo= ShiroUtils.getUser();
		geotype.setCreateBy(userDo.getUsername());
		geotype.setCreateDate(new Date());
		int res = 0;
		try{
			res = geotypeService.insert(geotype);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public int update( GeotypeDO geotype){
		int res = 0;
		try{
			UserDO userDo=ShiroUtils.getUser();
			geotype.setUpdateBy(userDo.getUsername());
			geotype.setUpdateDate(new Date());
			res = geotypeService.update(geotype);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public int delete( @RequestParam("id")String id){
		return geotypeService.delete(id);
	}

	/**
	 * 根据geoTypeId获取geoINfo
	 * @param typeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGeoInfoByTypeId")
	public List<SupportGeoInfo> getGeoInfoByTypeId(@RequestParam("typeId")String typeId){
		List<SupportGeoInfo> list = geotypeService.getGeoInfoByTypeId(typeId);
		return list;
	}
	
}
