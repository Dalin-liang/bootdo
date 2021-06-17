package com.bootdo.actionprogramManage.controller;

import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.actionprogramManage.service.DispatchLogService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 应急调度日志表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-08-19 17:06:16
 */
 
@Controller
@RequestMapping("/actionprogramManage/dispatchLog")
public class DispatchLogController {
	@Autowired
	private DispatchLogService dispatchLogService;
	

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DispatchLogDO> dispatchLogList = dispatchLogService.list(query);
		int total = dispatchLogService.count(query);
		PageUtils pageUtils = new PageUtils(dispatchLogList, total);
		return pageUtils;
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( DispatchLogDO dispatchLog){
		UserDO userDo= ShiroUtils.getUser();
		dispatchLog.setId(UUID.randomUUID().toString().replace("-", ""));
		dispatchLog.setCreateBy(userDo.getUsername());
		dispatchLog.setCreateDate(new Date());
		if(dispatchLogService.save(dispatchLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( DispatchLogDO dispatchLog){
		UserDO userDo=ShiroUtils.getUser();
		dispatchLog.setUpdateBy(userDo.getUsername());
		dispatchLog.setUpdateDate(new Date());
		dispatchLogService.update(dispatchLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String id){
		if(dispatchLogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] ids){
		dispatchLogService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/logicalDelete")
	public R logicalDelete(@RequestParam("id")String id){
		if(dispatchLogService.logicalDelete(id) > 0){
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@RequestMapping("/getDispatchActionprogram")
	public List<Map<String,Object>> getDispatchActionprogram(){
		List<Map<String,Object>> list = dispatchLogService.getDispatchActionprogram();
		return list;
	}

	
}
