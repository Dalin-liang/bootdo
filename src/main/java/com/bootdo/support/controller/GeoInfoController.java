package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportGeoInfoDTO;
import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportGeoInfo;
import com.bootdo.support.service.GeoInfoService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/geoInfo")
public class GeoInfoController {

    @Autowired
    private GeoInfoService geoInfoService;

    /**
     * 查询
     * @param supportGeoInfoDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportGeoInfoDTO supportGeoInfoDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = geoInfoService.get(supportGeoInfoDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportGeoInfo
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportGeoInfo supportGeoInfo){
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        supportGeoInfo.setId(id);
        UserDO userDo= ShiroUtils.getUser();
        supportGeoInfo.setCreate_by(userDo.getUsername());
        supportGeoInfo.setCreate_date(new Date());
        int res = 0;
        try{
            res = geoInfoService.insert(supportGeoInfo);
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     *更新/修改
     * @param supportGeoInfo
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportGeoInfo supportGeoInfo){
        UserDO userDo=ShiroUtils.getUser();
        supportGeoInfo.setUpdate_by(userDo.getUsername());
        supportGeoInfo.setUpdate_date(new Date());
        return geoInfoService.update(supportGeoInfo);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return geoInfoService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return geoInfoService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return geoInfoService.getUniqueById(id);
    }

    /**
     * 获取地理信息类别
     * @return
     */
    @ResponseBody
    @RequestMapping("/getGeoTypeList")
    public Map<String, Object>  getGeoTypeList(){
        Map<String, Object> map = new HashMap<String,Object>();
        try{
            List<GeotypeDO> list = geoInfoService.getGeoTypeList();
            map.put("geoTypeList", list);
            map.put("status", true);
        }catch(Exception e) {
            map.put("status", false);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取所有地理信息和类别信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/getGeoInfos")
    public Map<String, Object> getGeoInfo(SupportGeoInfo supportGeoInfo){
        Map<String, Object> map = new HashMap<String,Object>();
        try{
            List<Map<String, Object>> list = geoInfoService.getGeoInfo(supportGeoInfo.getName());
            map.put("status", true);
            map.put("list", list);
        }catch(Exception e){
            map.put("status", false);
            e.printStackTrace();
        }
        return  map;
    }

    /**
     * 获取地理周边信息
     * @return
     */
    @RequestMapping("/getArround")
    public List<Map<String, Object>> getArroundInfoData(){
        try {
            List<Map<String, Object>> list = geoInfoService.getArroundInfoData();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/getTypeChart")
    public JSONObject getTypeChart(){

        try {
            JSONObject result = new JSONObject();
            List<Map<String,Object>> list = geoInfoService.getTypeChart();

            result.put("data", list);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
