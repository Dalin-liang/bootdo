package com.bootdo.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.domain.DispatchActionprogramMainDO;
import com.bootdo.actionprogramManage.service.DispatchActionprogramMainService;
import com.bootdo.planManage.domain.PlanMainDO;
import com.bootdo.planManage.service.PlanMainService;
import com.bootdo.support.entity.SourceDO;
import com.bootdo.support.entity.SourceMenuDO;
import com.bootdo.support.service.SourceMenuService;
import com.bootdo.support.service.SourceService;
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

import com.bootdo.sms.domain.SmsSendConfigDO;
import com.bootdo.sms.service.SmsSendConfigService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 短信发送配置表
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:51:01
 */
 
@Controller
@RequestMapping("/sms/smsSendConfig")
public class SmsSendConfigController {
	@Autowired
	private SmsSendConfigService smsSendConfigService;
	@Autowired
	private SourceService sourceService;
	@Autowired
	private SourceMenuService sourceMenuService;
	@Autowired
	private PlanMainService planMainService;
	@Autowired
	private DispatchActionprogramMainService dispatchActionprogramMainService;


	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SmsSendConfigDO> smsSendConfigList = smsSendConfigService.list(query);
		int total = smsSendConfigService.count(query);
		PageUtils pageUtils = new PageUtils(smsSendConfigList, total);
		return pageUtils;
	}

	@ResponseBody
	@PostMapping("/getTargetByType")
	public Map<String,Object> getTargetByType(int type){
		Map<String,Object> map =new HashMap<String, Object>();

		if(type == 0){
			List<SourceDO> SourceList =  sourceService.getAllSource();
			if(SourceList!=null && SourceList.size()>0){
				List<SourceMenuDO> SourceMenuList = null;
				for (SourceDO sourceDO : SourceList) {
					SourceMenuList =  sourceMenuService.getBySourceId(sourceDO.getId());
					if(SourceMenuList!=null && SourceMenuList.size()>0){
						sourceDO.setSourceMenuList(SourceMenuList);
					}
				}
			}
			map.put("SourceList",SourceList);
		}else if(type == 1){
			List<Map<String,Object>> planminList = planMainService.getAllName();
			map.put("planminList",planminList);
		}else if(type == 2){
			List<Map<String,Object>> actionprogramMainList = dispatchActionprogramMainService.getAllName();
			map.put("actionprogramMainList",actionprogramMainList);
		}else if(type == 3){
			List<SourceDO> SourceList =  sourceService.getAllSource();
			map.put("SourceList",SourceList);
		}
		map.put("msg", "true");
		return map;
	}

	@ResponseBody
	@PostMapping("/getLastTargetByType")
	public Map<String,Object> getLastTargetByType(int type){
		Map<String,Object> map =new HashMap<String, Object>();

		if(type == 0){
			List<SourceDO> SourceList =  sourceService.getSourceInMenu();
			if(SourceList!=null && SourceList.size()>0){
				List<SourceMenuDO> SourceMenuList = null;
				for (SourceDO sourceDO : SourceList) {
					SourceMenuList =  sourceMenuService.getLastBySourceIdForSms(sourceDO.getId());
					if(SourceMenuList!=null && SourceMenuList.size()>0){
						sourceDO.setSourceMenuList(SourceMenuList);
					}
				}
			}
			map.put("SourceList",SourceList);
		}else if(type == 1){
			List<Map<String,Object>> planminList = planMainService.getLastNameForSms();
			map.put("planminList",planminList);
		}else if(type == 2){
			List<Map<String,Object>> actionprogramMainList = dispatchActionprogramMainService.getLastNameForSms();
			map.put("actionprogramMainList",actionprogramMainList);
		}else if(type == 3){
			List<SourceDO> SourceList =  sourceService.getSourceNotInSendConfig();
			map.put("SourceList",SourceList);
		}
		map.put("msg", "true");
		return map;
	}



	@ResponseBody
	@PostMapping("/changeStatus")
	public R changeSataus( String id ,String type ,String targetid , String issend){
		if(smsSendConfigService.changeStatus(id ,type,targetid,issend)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}

	@ResponseBody
	@PostMapping("/changeStatusByType")
	public R changeStatusByType( String type , String issend){
		if(smsSendConfigService.changeStatusByType(type ,issend)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}

	@ResponseBody
	@PostMapping("/changeStatusByParam")
	public R changeStatusByParam( String type ,String targetid,String Sourceid, String issend){
		if(smsSendConfigService.changeStatusByParam(type ,targetid,Sourceid,issend)>0){
			return R.ok("修改成功");
		}
		return R.error("修改失败");
	}


	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( SmsSendConfigDO smsSendConfig){
		if(smsSendConfigService.save(smsSendConfig)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( SmsSendConfigDO smsSendConfig){
		smsSendConfigService.update(smsSendConfig);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( Long id){
		if(smsSendConfigService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Long[] ids){
		smsSendConfigService.batchRemove(ids);
		return R.ok();
	}
	
}
