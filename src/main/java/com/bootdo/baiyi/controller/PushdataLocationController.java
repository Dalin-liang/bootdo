package com.bootdo.baiyi.controller;


import com.alibaba.fastjson.JSONObject;
import com.bootdo.baiyi.domain.PushdataLocationDO;
import com.bootdo.baiyi.service.PushdataLocationService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 位置数据
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-09-16 10:11:24
 */
 
@Controller
@RequestMapping("/baiyi/pushdataLocation")
public class PushdataLocationController {
	@Autowired
	private PushdataLocationService specialPopulationPositionService;
	

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PushdataLocationDO> specialPopulationPositionList = specialPopulationPositionService.list(query);
		int total = specialPopulationPositionService.count(query);
		PageUtils pageUtils = new PageUtils(specialPopulationPositionList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/getListSpecialPopulationPosition")
	public JSONObject getListSpecialPopulationPosition(@RequestParam Map<String, Object> params){
		//查询列表数据
		JSONObject result=new JSONObject();
		List<PushdataLocationDO> populationPositionList = specialPopulationPositionService.list(params);
		result.put("data", populationPositionList);
		return result;
	}
}
