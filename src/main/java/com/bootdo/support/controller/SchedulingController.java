package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.EquipstatusDO;
import com.bootdo.support.dto.SupportSchedulingDTO;
import com.bootdo.support.entity.SupportScheduling;
import com.bootdo.support.service.SchedulingService;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;
    @Autowired
    private SupportDeptService supportDeptService;

    /**
     * 查询
     * @param supportSchedulingDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportSchedulingDTO supportSchedulingDTO,
                            @RequestParam(value = "limit",required = false) String limit,
                            @RequestParam(value = "offset",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = schedulingService.get(supportSchedulingDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }
    

	@ResponseBody
	@GetMapping("/getUserByDeptId")
	public PageUtils getUserByDeptId(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map<String,Object>> equipstatusList = schedulingService.getUserByDeptId(query);
		int total = schedulingService.count(query);
		PageUtils pageUtils = new PageUtils(equipstatusList, total);
		return pageUtils;
	}

    @ResponseBody
    @PostMapping("/getUserSchedulingDate")
    public List<String> getUserSchedulingDate(){
        String userId = ShiroUtils.getUser().getUserId().toString();
        //查询列表数据
        List<String> equipstatusList = schedulingService.getSchedulingsDateByUserId(userId);
        return equipstatusList;
    }

    @ResponseBody
    @PostMapping("/getUserViewSchedulingDate")
    public List<String> getUserViewSchedulingDate(){
        Map<String, Object> params = new HashMap<String, Object>();
        String userId = ShiroUtils.getUser().getUserId().toString();
        params.put("userId",userId);

        //查询当前用户作为部门负责人所在的部门id
        List<String> deptIdsList = supportDeptService.getDeptIdsByUserId(userId);
        if(deptIdsList !=null && deptIdsList.size()>0){
            params.put("deptIdsList",deptIdsList);
        }else{
            params.put("deptIdsList",null);
        }
        //查询列表数据
        List<String> equipstatusList = schedulingService.getUserViewSchedulingDate(params);
        return equipstatusList;
    }
	
//    @RequestMapping("/countByUser")
//    public JSONObject countByUser(@RequestParam Map<String, Object> params,
//                            @RequestParam(value = "limit",required = false) String limit,
//                            @RequestParam(value = "offset",required = false) String offset){
//
//        JSONObject json=new JSONObject();
//        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
//        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
//        PageHelper.startPage(pageNum,pageSize);
//        List<Map<String,Object>> rs = schedulingService.countByUser(params);
//        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
//        json.put("rows",rs);
//        json.put("total",(int)pageInfo.getTotal());
//        return json;
//    }
    @RequestMapping("/countByUser")
    public PageUtils countByUser(@RequestParam Map<String, Object> params){

        //查询列表数据
        Query query = new Query(params);
        List<Map<String,Object>> countByUserList = schedulingService.countByUser(query);
        int total = schedulingService.countByUserCount(query);
        PageUtils pageUtils = new PageUtils(countByUserList, total);
        return pageUtils;
    }
    
    @RequestMapping("/countByDept")
    public PageUtils countByDept(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<Map<String,Object>> countByUserList = schedulingService.countByDept(query);
        int total = schedulingService.countByDeptCount(query);
        PageUtils pageUtils = new PageUtils(countByUserList, total);
        return pageUtils;
    }
    
    /**
     * 插入
     * @param supportScheduling
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/insert")
    public int insert(@RequestParam Map<String, Object> params) throws ParseException{
    	int f=0;
		try {
			String ids=params.get("data")+"";
			String idArray[]=ids.split(",");
			String date=params.get("f_date")+"";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
			for(int i=0;i<idArray.length;i++) {
				SupportScheduling supportScheduling=new SupportScheduling();
				supportScheduling.setId(UUID.randomUUID().toString().replace("-", ""));
				supportScheduling.setScheduling_date(sdf.parse(date));
				supportScheduling.setDeptperson_id(idArray[i]);
				schedulingService.insert(supportScheduling);
				f++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return f;
    }

    /**
     *更新/修改
     * @param supportScheduling
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportScheduling supportScheduling){
        UserDO userDo=ShiroUtils.getUser();
        supportScheduling.setDeptperson_id(userDo.getUserId().toString());
        return schedulingService.update(supportScheduling);
    }

    /**
     *更新/修改工作事项
     * @param supportScheduling
     * @return
     */
    @RequestMapping("/workUpdate")
    public R workUpdate(SupportScheduling supportScheduling){
        try {
            int row = schedulingService.workUpdate(supportScheduling);
            return R.ok("更新工作事项成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error("更新工作事项失败");
        }
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return schedulingService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return schedulingService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return schedulingService.getUniqueById(id);
    }
    
    /**
	 * 删除
	 */
	@PostMapping( "/deleteBatch")
	@ResponseBody
	@Transactional(readOnly=false)
/*	@RequiresPermissions("system:equipment:batchRemove")
*/	public int remove(@RequestParam("ids[]") String[] ids){
	
		return 	schedulingService.batchRemove(ids);
	}
}
