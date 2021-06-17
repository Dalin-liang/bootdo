package com.bootdo.actionprogramManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.actionprogramManage.dao.DispatchRespdeptDao;
import com.bootdo.actionprogramManage.dao.DispatchTaskDao;
import com.bootdo.actionprogramManage.domain.DispatchRespdeptDO;
import com.bootdo.actionprogramManage.domain.DispatchTaskDO;
import com.bootdo.actionprogramManage.service.DispatchTaskService;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.dao.ExpertInfoMapper;
import com.bootdo.support.dao.SupportDeptDao;
import com.bootdo.support.dao.TeamMapper;



@Service
public class DispatchTaskServiceImpl implements DispatchTaskService {
	@Autowired
	private DispatchTaskDao dispatchTaskDao;
	@Autowired
	private DeptPersonMapper deptPersonDao;
	@Autowired
	private TeamMapper teamDao;
	@Autowired
	private ExpertInfoMapper expertInfoDao;
	@Autowired
	private SupportDeptDao supportDeptDao;
	@Autowired
	private DispatchRespdeptDao respdeptDao;
	@Override
	public DispatchTaskDO get(String id){
		return dispatchTaskDao.get(id);
	}
	
	@Override
	public List<DispatchTaskDO> list(Map<String, Object> map){
		return dispatchTaskDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dispatchTaskDao.count(map);
	}
	
	@Override
	public int save(DispatchTaskDO dispatchTask){
		return dispatchTaskDao.save(dispatchTask);
	}
	
	@Override
	public int update(DispatchTaskDO dispatchTask){
		return dispatchTaskDao.update(dispatchTask);
	}
	
	@Override
	public int remove(String id){
		return dispatchTaskDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return dispatchTaskDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> getByActionprogramIdAndPlanMainId(String actionprogramId, String planMainId) {
		return dispatchTaskDao.getByActionprogramIdAndPlanMainId(actionprogramId, planMainId);
	}

	@Override
	public List<Map<String, Object>> getDpetList() {
		return dispatchTaskDao.getDpetList();
	}

	@Override
	public List<Map<String, Object>> getPersonList() {
		return dispatchTaskDao.getPersonList();
	}

	@Override
	public List<Map<String, Object>> getExpertList() {
		return dispatchTaskDao.getExpertList();
	}

	@Override
	public List<Map<String, Object>> getTeamList() {
		return dispatchTaskDao.getTeamList();
	}

	@Override
	public int logicDelete(String id) {
		return dispatchTaskDao.logicDelete(id);
	}
	
	@Override
	public List<Map<String, Object>> getByActionprogramId(String actionProgramId) {
		return dispatchTaskDao.getByActionprogramId(actionProgramId);
	}

	@Override
	public Map<String, Object> getPersonContact(Map<String,Object>params) {
		String flag=params.get("flag")+"";
		String mobile =""; // 要发送的手机号码
		String name =""; // 存储名称
		Map<String,Object> deptPersonMap;
		Map<String,Object> teamMap;
		Map<String,Object> supportDeptMap ;
		Map<String,Object> expertInfoMap ;
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(StringUtils.isNotEmpty("flag")&&"task".equals(flag)){
			Integer type=Integer.parseInt(params.get("type").toString());
			String liabilityId=params.get("liabilityId")+"";
			switch (type) { // 接收任务的对象类型(个人、应急队伍、部门、专家)
				case 1: // 个人
					deptPersonMap = deptPersonDao.getUniqueById(liabilityId);
					if(deptPersonMap !=null){
						mobile = (String) deptPersonMap.get("mobile");
						name = (String) deptPersonMap.get("name");
					}
					break;
				case 2: // 应急队伍
					teamMap =  teamDao.getUniqueById(liabilityId);
					if(teamMap !=null){
						supportDeptMap =  supportDeptDao.getUniqueById((String)teamMap.get("dept_id"));
						if(supportDeptMap !=null){
							mobile = (String)supportDeptMap.get("mobile"); // 应急队伍负责人的手机号码
							name = (String)supportDeptMap.get("contact"); //单位联系人
						}
					}
					break;
				case 3: // 部门
					supportDeptMap =  supportDeptDao.getUniqueById(liabilityId);
					if(supportDeptMap !=null){
						mobile = (String)supportDeptMap.get("mobile");  // 部门负责人的手机号码
						name = (String)supportDeptMap.get("contact"); //单位联系人
					}
					break;
				case 4: // 专家
					expertInfoMap =  expertInfoDao.getUniqueById(liabilityId);
					if(expertInfoMap !=null){
						mobile = (String)expertInfoMap.get("mobile");  // 部门负责人的手机号码
						name = (String)expertInfoMap.get("name");
					}
					break;
				}
				resultMap.put("name", name);
				resultMap.put("mobile", mobile);
		}else if(StringUtils.isNotEmpty("flag")&&"respdept".equals(flag)) {//响应部门
				String respdeptId=params.get("respdeptId")+"";
		
				List<DispatchRespdeptDO> dispatchRespdeptDO=respdeptDao.list(params);
				if(dispatchRespdeptDO.size()>0) {
					resultMap.put("name", dispatchRespdeptDO.get(0).getLiabilityMan());
					resultMap.put("mobile", dispatchRespdeptDO.get(0).getMobile());
				}
		}
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getTaskByParams(String actionProgramId, String name, String content,String liabilityMan) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = dispatchTaskDao.getTaskByParams(actionProgramId, name, content);
		if(list !=null && list.size()>0 && StringUtils.isNotEmpty(liabilityMan)){
			Map<String,Object> deptPersonMap;
			Map<String,Object> teamMap;
			Map<String,Object> supportDeptMap ;
			Map<String,Object> expertInfoMap ;
			Integer type ;
			String liabilityId;
			Map<String, Object> map;
			for (int i =0 ; i<list.size(); i++) {
				map = list.get(i);
				type = (Integer) map.get("type");
				liabilityId = map.get("liability_id").toString();
				if(type !=null && StringUtils.isNotEmpty(liabilityId)){
					switch (type) { // 接收任务的对象类型(个人、应急队伍、部门)
						case 1: // 个人
							map.put("taskTypeName", "个人");
							deptPersonMap = deptPersonDao.getByIdAndName(liabilityId,liabilityMan);
							if(deptPersonMap != null){
								map.put("liabilityMan", deptPersonMap.get("name").toString());
								map.put("liabilityManName", deptPersonMap.get("name").toString());
							}else{
								list.remove(i);
							}
							break;
						case 2: // 应急队伍
							map.put("taskTypeName", "队伍");
							teamMap =  teamDao.getUniqueById(liabilityId);
							if(teamMap !=null){
								map.put("liabilityManName", teamMap.get("name").toString());
								supportDeptMap =  supportDeptDao.getByIdAndContact((String)teamMap.get("dept_id"),liabilityMan);
								if(supportDeptMap !=null){
									map.put("liabilityMan", supportDeptMap.get("contact").toString());
								}else{
									list.remove(i);
								}
							}
							break;
						case 3: // 部门
							map.put("taskTypeName", "部门");
							supportDeptMap =  supportDeptDao.getByIdAndContact(liabilityId,liabilityMan);
							if(supportDeptMap !=null){
								map.put("liabilityMan", supportDeptMap.get("contact").toString());
								map.put("liabilityManName", supportDeptMap.get("name").toString());
							}else{
								list.remove(i);
							}
							break;
						case 4: // 专家
							map.put("taskTypeName", "专家");
							expertInfoMap =  expertInfoDao.getByIdAndName(liabilityId,liabilityMan);
							if(expertInfoMap !=null){
								map.put("liabilityMan", expertInfoMap.get("name").toString());
								map.put("liabilityManName", expertInfoMap.get("name").toString());
							}else{
								list.remove(i);
							}
							break;
					}
				}
				
			}
		}
		
		return list;
	}

}
