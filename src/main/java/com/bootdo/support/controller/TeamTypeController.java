package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportTeamTypeDTO;
import com.bootdo.support.entity.SupportTeamType;
import com.bootdo.support.service.TeamTypeService;
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
@RequestMapping("/teamType")
public class TeamTypeController {

    @Autowired
    private TeamTypeService teamTypeService;

    /**
     * 查询
     * @param supportTeamTypeDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportTeamTypeDTO supportTeamTypeDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = teamTypeService.get(supportTeamTypeDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportTeamType
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportTeamType supportTeamType){
        String uuid= UUID.randomUUID().toString();
        UserDO userDo= ShiroUtils.getUser();
        supportTeamType.setId(uuid);
        supportTeamType.setCreate_by(userDo.getUsername());
        supportTeamType.setCreate_date(new Date());
        return teamTypeService.insert(supportTeamType);
    }

    /**
     *更新/修改
     * @param supportTeamType
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportTeamType supportTeamType){
        UserDO userDo=ShiroUtils.getUser();
        supportTeamType.setUpdate_by(userDo.getUsername());
        supportTeamType.setUpdate_date(new Date());
        return teamTypeService.update(supportTeamType);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return teamTypeService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return teamTypeService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return teamTypeService.getUniqueById(id);
    }
}
