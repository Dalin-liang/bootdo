package com.bootdo.support.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.planManage.domain.PlanAccidentTypeDO;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.support.dto.SupportGoodsInfoDTO;
import com.bootdo.support.entity.OneleveltypeDO;
import com.bootdo.support.entity.SupportGoodsInfo;
import com.bootdo.support.entity.TwoleveltypeDO;
import com.bootdo.support.service.GoodsInfoService;
import com.bootdo.support.service.OneleveltypeService;
import com.bootdo.support.service.TwoleveltypeService;
import com.bootdo.system.controller.UserController;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/goodsInfo")
public class GoodsInfoController {

    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private OneleveltypeService oneleveltypeService;
    @Autowired
    private TwoleveltypeService twoleveltypeService;
    
    
    /**
     * 查询
     * @param supportGoodsInfoDTO
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping("/query")
    public JSONObject query(SupportGoodsInfoDTO supportGoodsInfoDTO,
                            @RequestParam(value = "pageSize",required = false) String limit,
                            @RequestParam(value = "pageNumber",required = false) String offset){

        JSONObject json=new JSONObject();
        int pageNum = StringUtils.isEmpty(offset)?1:Integer.parseInt(offset);
        int pageSize = StringUtils.isEmpty(limit)?10:Integer.parseInt(limit);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> rs = goodsInfoService.get(supportGoodsInfoDTO);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String, Object>>(rs);
        json.put("rows",rs);
        json.put("total",(int)pageInfo.getTotal());
        return json;
    }
    
    /**
     * 获取下拉数据
     * @return
     */
    @ResponseBody
	@PostMapping("/getSelectData")
	public Map<String,Object> getSelectData(){
		Map<String,Object> map =new HashMap<String, Object>();
		List<OneleveltypeDO> oneleveltypeList = oneleveltypeService.getAll();
		List<TwoleveltypeDO> twoleveltypeList = twoleveltypeService.getAll();
		map.put("oneleveltypeList", oneleveltypeList);
		map.put("twoleveltypeList", twoleveltypeList);
		map.put("msg", "true");
		return map;
	}

    /**
     * 插入
     * @param supportGoodsInfo
     * @return
     */
    @RequestMapping("/insert")
    public int insert(SupportGoodsInfo supportGoodsInfo){
        String id= UUID.randomUUID().toString().replace("-", "");
        UserDO userDo=ShiroUtils.getUser();
        supportGoodsInfo.setId(id);
        supportGoodsInfo.setCreate_by(userDo.getUsername());
        supportGoodsInfo.setCreate_date(new Date());
        return goodsInfoService.insert(supportGoodsInfo);
    }

    /**
     *更新/修改
     * @param supportGoodsInfo
     * @return
     */
    @RequestMapping("/update")
    public int update(SupportGoodsInfo supportGoodsInfo){
        UserDO userDo=ShiroUtils.getUser();
        supportGoodsInfo.setUpdate_by(userDo.getUsername());
        supportGoodsInfo.setUpdate_date(new Date());
        return goodsInfoService.update(supportGoodsInfo);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id")String id){
        return goodsInfoService.delete(id);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/logicalDelete")
    public int logicalDelete(@RequestParam("id")String id){
        return goodsInfoService.logicalDelete(id);
    }

    /**
     * 查找唯一记录
     * @param id
     * @return
     */
    @RequestMapping("/getUniqueById")
    public Map<String,Object> getUniqueById(@RequestParam("id")String id){
        return goodsInfoService.getUniqueById(id);
    }
}
