package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.SupportStockTakingDTO;
import com.bootdo.support.entity.SupportStockTaking;
import com.bootdo.support.service.StockTakingService;
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
@RequestMapping("/stockTaking")
public class StockTakingController {

    @Autowired
    private StockTakingService stockTakingService;

    /**
     * 查询
     * @param supportStockTakingDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportStockTakingDTO supportStockTakingDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = stockTakingService.get(supportStockTakingDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportStockTaking
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportStockTaking supportStockTaking){
        String uuid= UUID.randomUUID().toString();
        UserDO userDo= ShiroUtils.getUser();
        supportStockTaking.setId(uuid);
        supportStockTaking.setCreate_by(userDo.getUsername());
        supportStockTaking.setCreate_date(new Date());
        return stockTakingService.insert(supportStockTaking);
    }

    /**
     *更新/修改
     * @param supportStockTaking
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportStockTaking supportStockTaking){
        UserDO userDo=ShiroUtils.getUser();
        supportStockTaking.setUpdate_by(userDo.getUsername());
        supportStockTaking.setUpdate_date(new Date());
        return stockTakingService.update(supportStockTaking);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return stockTakingService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return stockTakingService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return stockTakingService.getUniqueById(id);
    }
}
