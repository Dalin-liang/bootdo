package com.bootdo.support.controller;


import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportGoodsPushDTO;
import com.bootdo.support.entity.SupportGoodsPush;
import com.bootdo.support.service.GoodsPushService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/goodsPush")
public class GoodsPushController {

    @Autowired
    private GoodsPushService goodsPushService;

    /**
     * 查询
     * @param supportGoodsPushDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportGoodsPushDTO supportGoodsPushDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = goodsPushService.get(supportGoodsPushDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportGoodsPush
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportGoodsPush supportGoodsPush){
        String uuid= UUID.randomUUID().toString().replace("-", "");
        UserDO userDo= ShiroUtils.getUser();
        supportGoodsPush.setId(uuid);
        supportGoodsPush.setCreate_by(userDo.getUsername());
        supportGoodsPush.setCreate_date(new Date());
        return goodsPushService.insert(supportGoodsPush);
    }

    /**
     *更新/修改
     * @param supportGoodsPush
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportGoodsPush supportGoodsPush){
        UserDO userDo=ShiroUtils.getUser();
        supportGoodsPush.setUpdate_by(userDo.getUsername());
        supportGoodsPush.setUpdate_date(new Date());
        return goodsPushService.update(supportGoodsPush);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return goodsPushService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return goodsPushService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return goodsPushService.getUniqueById(id);
    }

    /**
     * 获取储备库
     * @return
     */
    @RequestMapping("/getStoreHouse")
    public List<Map<String,Object>> getStoreHouse(){
        return goodsPushService.getStoreHouse();
    }

    /**
     * 获取货物列表
     * @return
     */
    @RequestMapping("/getGoods")
    public List<Map<String,Object>> getGoods(){
        return goodsPushService.getGoods();
    }
    
    /**
     * 获取储备库、货物列表所有数据，用于下拉查询
     * @return
     */
    @RequestMapping("/getAllStoreHouseAndGoods")
    public Map<String,Object> getAllStoreHouseAndGoods(){
    	Map<String,Object> map = new HashMap<String, Object>();
    	List<Map<String,Object>> storeHouseList = goodsPushService.getAllStoreHouse();
    	List<Map<String,Object>> goodsList = goodsPushService.getAllGoods();
    	if(storeHouseList !=null && storeHouseList.size()>0){
    		map.put("storeHouseList", storeHouseList);
    	}
    	if(goodsList !=null && goodsList.size()>0){
    		map.put("goodsList", goodsList);
    	}
    	return map;
    }
}
