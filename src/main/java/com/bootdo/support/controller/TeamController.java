package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportTeamDTO;
import com.bootdo.support.entity.SupportTeam;
import com.bootdo.support.entity.SupportTeamType;
import com.bootdo.support.service.TeamService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;
    

    /**
     * 查询
     * @param supportTeamDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportTeamDTO supportTeamDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = teamService.get(supportTeamDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportTeam
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportTeam supportTeam, HttpServletRequest request){
        String uuid= UUID.randomUUID().toString();
        int k=0;
        UserDO userDo= ShiroUtils.getUser();
        supportTeam.setId(uuid);
        supportTeam.setCreate_by(userDo.getUsername());
        supportTeam.setCreate_date(new Date());
        k=teamService.insert(supportTeam);
        String[] teams=request.getParameter("team").split(",");
        for(int i=0;i<teams.length;i++){
            insertTeam(uuid,teams[i]);
        }

        return k;
    }

    /**
     * 插入
     * @param supportTeam
     * @return
     */
    @RequestMapping("/addTeam")
    public R addTeam(SupportTeam supportTeam, HttpServletRequest request){
        String name = supportTeam.getName();
        if(com.bootdo.common.utils.StringUtils.isEmpty(name)){
            return  R.error("名字不能为空");
        }
        SupportTeamDTO temp = teamService.getByName(name);
        if(temp!=null){
            return R.error(504,"该队伍已存在");
        }

        String uuid= UUID.randomUUID().toString();
        int k=0;
        UserDO userDo= ShiroUtils.getUser();
        supportTeam.setId(uuid);
        supportTeam.setCreate_by(userDo.getUsername());
        supportTeam.setCreate_date(new Date());
        k=teamService.insert(supportTeam);
        if(com.bootdo.common.utils.StringUtils.isNotEmpty(request.getParameter("team"))){
            String[] teams=request.getParameter("team").split(",");
            for(int i=0;i<teams.length;i++){
                insertTeam(uuid,teams[i]);
            }
        }

        if(k >0){
            return R.ok("添加成功");
        }
        return R.error("添加失败");
    }

    /**
     *更新/修改
     * @param supportTeam
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportTeam supportTeam,HttpServletRequest request){
        UserDO userDo=ShiroUtils.getUser();
        String id=request.getParameter("id");
        teamService.deleteTeam(supportTeam.getId());
        if(StringUtils.isNotBlank(request.getParameter("team"))){
            String[] teams=request.getParameter("team").split(",");
            for(int i=0;i<teams.length;i++){
                insertTeam(supportTeam.getId(),teams[i]);
            }
        }
        supportTeam.setUpdate_by(userDo.getUsername());
        supportTeam.setUpdate_date(new Date());
        return teamService.update(supportTeam);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        teamService.deleteTeam(id);
        return teamService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return teamService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return teamService.getUniqueById(id);
    }

    /**
     * 返回主管单位
     * @return
     */
    @RequestMapping("/getDept")
    public List<Map<String,Object>> getDept(){
        return teamService.getDept();
    }

    /**
     * 返回队伍人员列表
     * @param id
     * @return 
     *   */
    @RequestMapping("/getTeamMate")
    public List<Map<String,Object>> getTeamMate(@RequestParam("id")String id){
        return teamService.getTeamMate(id);
    }

    /**
     * 插入关系
     * @param team_id
     * @param deptperson_id
     * @return
     */
    @RequestMapping("/insertTeam")
    public int insertTeam(@RequestParam("team_id")String team_id,@RequestParam("deptperson_id")String deptperson_id){
        return teamService.insertTeam(team_id,deptperson_id);
    }
}
