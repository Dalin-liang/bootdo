package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportStoragePlanDTO;
import com.bootdo.support.entity.SupportStoragePlan;
import com.bootdo.support.service.StoragePlanService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/storagePlan")
public class StoragePlanController {

    @Autowired
    private StoragePlanService storagePlanService;

    /**
     * 查询
     * @param supportStoragePlanDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportStoragePlanDTO supportStoragePlanDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = storagePlanService.get(supportStoragePlanDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportStoragePlan
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportStoragePlan supportStoragePlan){
        String uuid=UUID.randomUUID().toString().replace("-", "");
        UserDO userDo= ShiroUtils.getUser();
        supportStoragePlan.setId(uuid);
        supportStoragePlan.setUpdate_date(new Date());
        return storagePlanService.insert(supportStoragePlan);
    }

    /**
     *更新/修改
     * @param supportStoragePlan
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportStoragePlan supportStoragePlan){
        UserDO userDo=ShiroUtils.getUser();
        supportStoragePlan.setUpdate_date(new Date());
        return storagePlanService.update(supportStoragePlan);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return storagePlanService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return storagePlanService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return storagePlanService.getUniqueById(id);
    }

    /**
     * 获取储备库
     * @return
     */
    @RequestMapping("/getStoreHouse")
    public List<Map<String,Object>> getStoreHouse(){
        return storagePlanService.getStoreHouse();
    }

    /**
     * 获取货物列表
     * @return
     */
    @RequestMapping("/getGoods")
    public List<Map<String,Object>> getGoods(){
        return storagePlanService.getGoods();
    }

}
