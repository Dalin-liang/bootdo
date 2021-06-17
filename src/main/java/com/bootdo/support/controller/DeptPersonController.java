package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportDeptPersonDTO;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.support.service.DeptPersonService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/deptPerson")
public class DeptPersonController {

    @Autowired
    private DeptPersonService deptPersonService;

    /**
     * 查询
     *
     * @param supportDeptPersonDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportDeptPersonDTO supportDeptPersonDTO,
                            @RequestParam(value = "pageSize", required = false) String limit,
                            @RequestParam(value = "pageNumber", required = false) String offset) {

        JSONObject json = new JSONObject();
        int pageNum = StringUtils.isEmpty(offset) ? 1 : Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit) ? 10 : Integer.parseInt(limit);
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> rs = deptPersonService.get(supportDeptPersonDTO);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows", rs);
        json.put("total", (int) pageInfo.getTotal());
        return json;
    }

    @RequestMapping("/getEmergencyDept")
    public List<Map<String, Object>> getEmergencyDept() {
        List<Map<String, Object>> rs = deptPersonService.getEmergencyDept();
        return rs;
    }

    @RequestMapping("/getSysUser")
    public List<Map<String, Object>> getSysUser() {
        List<Map<String, Object>> rs = deptPersonService.getSysUser();
        return rs;
    }

    @RequestMapping("/getUnrelatedSysUser")
    public List<Map<String, Object>> getUnrelatedSysUser() {
        List<Map<String, Object>> rs = deptPersonService.getUnrelatedSysUser();
        return rs;
    }

    @RequestMapping("/getUnrelatedAndOneSysUser")
    public List<Map<String, Object>> getUnrelatedAndOneSysUser(@RequestParam("personId") String personId) {
        List<Map<String, Object>> rs = deptPersonService.getUnrelatedAndOneSysUser(personId);
        return rs;
    }

    /**
     * 插入
     *
     * @param supportDeptPerson
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportDeptPerson supportDeptPerson) {
        UserDO userDo = ShiroUtils.getUser();
        supportDeptPerson.setId(UUID.randomUUID().toString().replace("-", ""));
        supportDeptPerson.setCreate_by(userDo.getUsername());
        supportDeptPerson.setCreate_date(new Date());
        //supportDeptPerson.setIs_duty(1);
        int rs = deptPersonService.insert(supportDeptPerson);
        if (rs == 1 && StringUtils.isNotEmpty(supportDeptPerson.getUser_id())) {
            //更新对应的sys_user的记录，关联插入的这条记录
            deptPersonService.updateSysUserPersoinId(supportDeptPerson.getId(), supportDeptPerson.getUser_id());
        }
        return rs;

    }

    /**
     * 插入
     *
     * @param supportDeptPerson
     * @return
     */
    @RequestMapping("/insertDuty")
    public int insertDuty(SupportDeptPerson supportDeptPerson) {
        UserDO userDo = ShiroUtils.getUser();
        supportDeptPerson.setId(UUID.randomUUID().toString().replace("-", ""));
        supportDeptPerson.setCreate_by(userDo.getUsername());
        supportDeptPerson.setCreate_date(new Date());
        supportDeptPerson.setIs_duty(1);
        int rs = deptPersonService.insert(supportDeptPerson);
   
        return rs;

    }
    
    @RequestMapping("/updateDuty")
    public int updateDuty(SupportDeptPerson supportDeptPerson){
        UserDO userDo=ShiroUtils.getUser();
        supportDeptPerson.setUpdate_by(userDo.getUsername());
        supportDeptPerson.setUpdate_date(new Date());
        return deptPersonService.update(supportDeptPerson);
    }

    /**
     * 更新/修改
     *
     * @param supportDeptPerson
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportDeptPerson supportDeptPerson) {
        UserDO userDo = ShiroUtils.getUser();
        supportDeptPerson.setUpdate_by(userDo.getUsername());
        supportDeptPerson.setUpdate_date(new Date());
        int rs = deptPersonService.update(supportDeptPerson);
        if (rs == 1) {
            //先清空sys_user关联的deptperson记录
            deptPersonService.clearSysUserPersonId(supportDeptPerson.getId());
            if (StringUtils.isNotEmpty(supportDeptPerson.getUser_id())) {
                //更新关联sys_user表的记录
                deptPersonService.updateSysUserPersoinId(supportDeptPerson.getId(), supportDeptPerson.getUser_id());
            }
        }
        return rs;
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id") String id) {
        int res;
        try {
            res = deptPersonService.delete(id);
        } catch (Exception e) {
            res = 0;
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id") String id) {
        int res = 0;
        try {
            res = deptPersonService.logicalDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 查找唯一记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String, Object> getUniqueById(@RequestParam("id") String id) {
        return deptPersonService.getUniqueById(id);
    }

    @RequestMapping("/getAll")
    public List<Map<String, Object>> getAll() {
        SupportDeptPersonDTO supportDeptPersonDTO = new SupportDeptPersonDTO();
        return deptPersonService.get(supportDeptPersonDTO);
    }
    @RequestMapping("/getAll2")
    public Map<String, Object> getAll2() {
    	Map<String,Object>res=new HashMap<String, Object>(2);
    	UserDO userDo= ShiroUtils.getUser();
        SupportDeptPersonDTO supportDeptPersonDTO = new SupportDeptPersonDTO();
        List<Map<String,Object>>list= deptPersonService.get(supportDeptPersonDTO);
        UserDO resUserDo=new UserDO();
        if(userDo != null){
            resUserDo.setName(userDo.getName());
            resUserDo.setMobile(userDo.getMobile());
            resUserDo.setSex(userDo.getSex());
        }
        res.put("data", list);
        res.put("user", resUserDo);
        return res;
    }

    @RequestMapping("/getSysUserCountById")
    public int etSysUserCountById(@RequestParam("id") String id) {
        int count = deptPersonService.getSysUserCountById(id);
        return count;
    }

    @RequestMapping("/getDeptpersonByDeptId")
    public List<Map<String, Object>> getDeptpersonByDeptId(@RequestParam("id") String id) {

        List<Map<String, Object>> list = deptPersonService.getDeptpersonByDeptId(id);
        return list;
    }

    @RequestMapping("/getDeptByDeptPersonId")
    public Map<String, Object> getDeptByDeptPersonId(@RequestParam("id") String id){
        Map<String, Object> map = new HashMap<String, Object>();
        SupportDeptDO entity = deptPersonService.getDeptByDeptPersonId(id);
        map.put("dept", entity);
        return map;
    }
    @RequestMapping("/getDeptPersonByTeam")
    public List<SupportDeptPerson> getDeptPersonByTeam(@RequestParam("teamId") String teamId){
    	List<SupportDeptPerson> entity = deptPersonService.getDeptPersonByTeam(teamId);
        return entity;
    }

    @RequestMapping("/getCurrentDeptPersonInfo")
    public Map<String, Object> getCurrentDeptPersonInfo(){
        UserDO userDo = ShiroUtils.getUser();
        Map<String, Object> map = null;
        if(userDo != null && userDo.getUserId() !=null){
            map = deptPersonService.getDeptPersonInfoByUserId(userDo.getUserId());
        }
        return map;
    }
    
}
