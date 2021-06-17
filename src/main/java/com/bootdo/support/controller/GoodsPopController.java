package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.support.dto.StorehouseGoodsDO;
import com.bootdo.support.dto.SupportGoodsPopDTO;
import com.bootdo.support.entity.SupportGoodsPop;
import com.bootdo.support.service.GoodsPopService;
import com.bootdo.support.service.StorehouseGoodsService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/goodsPop")
public class GoodsPopController {

    @Autowired
    private GoodsPopService goodsPopService;
    @Autowired
    private StorehouseGoodsService storehouseGoodsService;

    /**
     * 查询
     * @param supportGoodsPopDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportGoodsPopDTO supportGoodsPopDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = goodsPopService.get(supportGoodsPopDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }

    /**
     * 插入
     * @param supportGoodsPop
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportGoodsPop supportGoodsPop){
        String uuid= UUID.randomUUID().toString();
        UserDO userDo= ShiroUtils.getUser();
        supportGoodsPop.setId(uuid);
        supportGoodsPop.setCreate_by(userDo.getUsername());
        supportGoodsPop.setCreate_date(new Date());
        return goodsPopService.insert(supportGoodsPop);
    }

    /**
     *更新/修改
     * @param supportGoodsPop
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportGoodsPop supportGoodsPop){
        UserDO userDo=ShiroUtils.getUser();
        supportGoodsPop.setUpdate_by(userDo.getUsername());
        supportGoodsPop.setUpdate_date(new Date());
        return goodsPopService.update(supportGoodsPop);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return goodsPopService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return goodsPopService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return goodsPopService.getUniqueById(id);
    }
    
    @RequestMapping("/getStoreNum")
    public Map<String,Object> getStoreNum(@RequestParam Map<String,Object> params){
    	
    	List<StorehouseGoodsDO> list=	storehouseGoodsService.list(params);
    	Map<String,Object>resultMap=new HashMap<String, Object>();
    	if(list!=null&&list.size()>0) {
    		resultMap.put("storeNum", list.get(0).getInventorynum());
    	}else {
    		resultMap.put("storeNum", 0);
    	}
        return resultMap;
    }
    
}
