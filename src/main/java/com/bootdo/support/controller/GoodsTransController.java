package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportGoodsTransDTO;
import com.bootdo.support.entity.SupportGoodsPush;
import com.bootdo.support.entity.SupportGoodsTrans;
import com.bootdo.support.service.GoodsTransService;
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
@RequestMapping("/goodsTran")
public class GoodsTransController {

    @Autowired
    private GoodsTransService goodsTransService;

    /**
     * 查询
     * @param supportGoodsTransDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportGoodsTransDTO supportGoodsTransDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = goodsTransService.get(supportGoodsTransDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportGoodsTrans
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportGoodsTrans supportGoodsTrans){
        String uuid= UUID.randomUUID().toString();
        UserDO userDo= ShiroUtils.getUser();
        supportGoodsTrans.setId(uuid);
        supportGoodsTrans.setCreate_by(userDo.getUsername());
        supportGoodsTrans.setCreate_date(new Date());
        return goodsTransService.insert(supportGoodsTrans);
    }

    /**
     *更新/修改
     * @param supportGoodsTrans
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportGoodsTrans supportGoodsTrans){
        UserDO userDo=ShiroUtils.getUser();
        supportGoodsTrans.setUpdate_by(userDo.getUsername());
        supportGoodsTrans.setUpdate_date(new Date());
        return goodsTransService.update(supportGoodsTrans);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return goodsTransService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return goodsTransService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return goodsTransService.getUniqueById(id);
    }
}
