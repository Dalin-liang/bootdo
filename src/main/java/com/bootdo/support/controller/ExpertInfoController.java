package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.support.dto.SupportExpertInfoDTO;
import com.bootdo.support.entity.SupportExpertInfo;
import com.bootdo.support.entity.SupportGeoInfo;
import com.bootdo.support.service.ExpertInfoService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/expertInfo")
public class ExpertInfoController {

    @Autowired
    private ExpertInfoService expertInfoService;

    /**
     * 查询
     * @param supportExpertInfoDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportExpertInfoDTO supportExpertInfoDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = expertInfoService.get(supportExpertInfoDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 获取预警类别
     * @return
     */
     @ResponseBody
     @RequestMapping("/getAllEarlywarnType")
    public Map<String,Object> getEarlyWarning(){
        Map<String, Object> map = new HashMap<>();
        try{
            List<PlanEarlywarnTypeDO> planEarlyWarnTypeList = expertInfoService.getAllEarlyWarningType();
            map.put("earlyWarn", planEarlyWarnTypeList);
            map.put("status", true);
        }catch(Exception e){
            e.printStackTrace();
            map.put("status", false);
        }
        return map;

    }
    /**
     * 插入
     * @param supportExpertInfo
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportExpertInfo supportExpertInfo){
        int res = 0;
        try{
            String id= UUID.randomUUID().toString().replaceAll("-", "");
            supportExpertInfo.setId(id);
            UserDO userDo= ShiroUtils.getUser();
            supportExpertInfo.setCreate_by(userDo.getUsername());
            supportExpertInfo.setCreate_date(new Date());
            res = expertInfoService.insert(supportExpertInfo);
            if(res == 1){
                String earlyWarnTypeids = supportExpertInfo.getEarlywarnIds();
                List<String> earlyWarnTypeList = Arrays.asList(earlyWarnTypeids.split(","));
                //删除与预警类型关联表记录
                expertInfoService.deleteExpertPlantype(id);
                if(CollectionUtils.isNotEmpty(earlyWarnTypeList)){
                    //添加到预警类型关联表记录
                    for(int i = 0; i < earlyWarnTypeList.size(); i++){
                        String ide= UUID.randomUUID().toString().replaceAll("-", "");
                        expertInfoService. insertExpertPlantype(ide, id, earlyWarnTypeList.get(i));
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     *更新/修改
     * @param supportExpertInfo
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportExpertInfo supportExpertInfo){
       int res = 0;
        try{
            UserDO userDo=ShiroUtils.getUser();
            supportExpertInfo.setUpdate_by(userDo.getUsername());
            supportExpertInfo.setUpdate_date(new Date());
            String earlyWarnTypeids = supportExpertInfo.getEarlywarnIds();
            List<String> earlyWarnTypeList = Arrays.asList(earlyWarnTypeids.split(","));
            res = expertInfoService.update(supportExpertInfo);
            if(res == 1){
                //删除与预警类型关联表记录
                expertInfoService.deleteExpertPlantype(supportExpertInfo.getId());
                if(CollectionUtils.isNotEmpty(earlyWarnTypeList)){
                    //添加到预警类型关联表记录
                    for(int i = 0; i < earlyWarnTypeList.size(); i++){
                        String id= UUID.randomUUID().toString().replaceAll("-", "");
                        expertInfoService. insertExpertPlantype(id, supportExpertInfo.getId(), earlyWarnTypeList.get(i));
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        expertInfoService.deleteExpertPlantype(id);
        return expertInfoService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return expertInfoService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return expertInfoService.getUniqueById(id);
    }

}
