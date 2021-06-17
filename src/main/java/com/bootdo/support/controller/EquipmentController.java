package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportEquipmentDTO;
import com.bootdo.support.entity.SupportEquipment;
import com.bootdo.support.service.EquipmentService;
import com.bootdo.support.service.EquiptypeService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;
    
    @Autowired
    private EquiptypeService EquiptypeService;

    /**
     * 查询
     * @param supportEquipmentDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportEquipmentDTO supportEquipmentDTO,
                            @RequestParam(value = "limit",required = false) String limit,
                            @RequestParam(value = "offset",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = equipmentService.get(supportEquipmentDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportEquipment
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportEquipment supportEquipment){
        UserDO userDo= ShiroUtils.getUser();
        supportEquipment.setCreate_by(userDo.getUsername());
        supportEquipment.setId(UUID.randomUUID().toString().replace("-",""));

        supportEquipment.setCreate_date(new Date());
        return equipmentService.insert(supportEquipment);
    }

    /**
     *更新/修改
     * @param supportEquipment
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportEquipment supportEquipment){
        UserDO userDo=ShiroUtils.getUser();
        supportEquipment.setUpdate_by(userDo.getUsername());
        supportEquipment.setUpdate_date(new Date());
        return equipmentService.update(supportEquipment);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return equipmentService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return equipmentService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return equipmentService.getUniqueById(id);
    }
    

}
