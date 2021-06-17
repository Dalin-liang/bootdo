package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.planManage.service.PlanEarlywarnTypeService;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.support.entity.SupportExpertInfo;
import com.bootdo.support.service.SupportDeptService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 应急部门表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 11:34:54
 */


@RestController
@RequestMapping("/supportDept")
public class SupportDeptController {

    @Autowired
    private SupportDeptService supportDeptService;

    @Autowired
    private PlanEarlywarnTypeService planEarlywarnTypeService;

    /**
     * 查询
     * @param supportDeptDO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportDeptDO supportDeptDO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = supportDeptService.get(supportDeptDO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportDeptDO
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportDeptDO supportDeptDO){
        UserDO userDo= ShiroUtils.getUser();
        supportDeptDO.setId(UUID.randomUUID().toString().replace("-", ""));
        supportDeptDO.setCreateBy(userDo.getUsername());
        supportDeptDO.setCreateDate(new Date());
        return supportDeptService.insert(supportDeptDO);
    }





    /**
     *更新/修改
     * @param supportDeptDO
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportDeptDO supportDeptDO){
        UserDO userDo=ShiroUtils.getUser();
        supportDeptDO.setUpdateBy(userDo.getUsername());
        supportDeptDO.setUpdateDate(new Date());
        return supportDeptService.update(supportDeptDO);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return supportDeptService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return supportDeptService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return supportDeptService.getUniqueById(id);
    }

    @RequestMapping("/getAll")
    public List<Map<String,Object>> getAll(){
        SupportDeptDO supportDeptDO=new SupportDeptDO();
        return supportDeptService.get(supportDeptDO);
    }

    @RequestMapping("/getDeptPersonCountById")
    public int getDeptPersonCountById(@RequestParam("id") String id){
        int count = supportDeptService.getDeptPersonCountById(id);
        return count;
    }

    /**
     * 部门和人员数据
     */
    @RequestMapping("/getDeptAndPersons")
    public Map<String,Object> getDeptAndPersons(){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            //应急部门和应急人员
            List<SupportDeptDO> depts = supportDeptService.getDeptAndPersons();
            List<Map<String,Object>> nodeList = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(depts)) {
                for (SupportDeptDO dept : depts) {
                    Map<String, Object> father = new HashMap<>();
                    father.put("name", dept.getName());
                    father.put("id", dept.getId());
                    if (CollectionUtils.isNotEmpty(dept.getDeptperson())) {
                        List<Map<String, Object>> childrenNode = new ArrayList<>();
                        for (SupportDeptPerson person : dept.getDeptperson()) {
                            Map<String, Object> children = new HashMap<>();
                            children.put("name", person.getName());
                            children.put("id", person.getId());
                            children.put("mobile", person.getMobile());
                            children.put("position", person.getPosition());
                            childrenNode.add(children);
                        }
                        father.put("children", childrenNode);
                    }
                    nodeList.add(father);
                }
            }
            //应急专家
            List<PlanEarlywarnTypeDO> earlyWarnType = planEarlywarnTypeService.getEarlyWarnTypeAndExpertInfo();
            if(CollectionUtils.isNotEmpty(earlyWarnType)){
                for (PlanEarlywarnTypeDO warnType : earlyWarnType){
                    Map<String, Object> expertFather = new HashMap<>();
                    expertFather.put("name", warnType.getName());
                    expertFather.put("id", warnType.getId());
                    if(CollectionUtils.isNotEmpty(warnType.getExperInfo())){
                        List<Map<String, Object>> expertListNode = new ArrayList<>();
                        for (SupportExpertInfo expert : warnType.getExperInfo()){
                            Map<String, Object> expertChildren = new HashMap<>();
                            expertChildren.put("name", expert.getName());
                            expertChildren.put("id", expert.getId());
                            expertChildren.put("mobile", expert.getMobile());
                            expertChildren.put("position", expert.getPosition());
                            expertListNode.add(expertChildren);
                        }
                        expertFather.put("children", expertListNode);
                    }
                    nodeList.add(expertFather);
                }
            }
            returnMap.put("nodes", nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }



}
