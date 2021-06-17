package com.bootdo.api.receive.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.DateUtils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.base.Location;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.support.dto.ReceiveInfoDTO;
import com.bootdo.support.entity.ReceiveInfo;
import com.bootdo.support.service.ReceiveinfoService;
import com.bootdo.system.domain.UserDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 信息接报管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-08-14 09:07:54
 */
 
@RestController
@RequestMapping("/api/receiveInfo")
public class ApiReceiveInfoController {

	@Autowired
	private ReceiveinfoService receiveinfoService;

	@Autowired
    private BaseEventService baseEventService;




    /**
     * 插入
     * @param receiveInfo
     * @return
     */
    @PostMapping("/insert")
	@ApiOperation(value="保存信息接报")
    public ResponseEntity insert(@ApiParam(name = "巡河信息")@RequestParam(value="data",required = false)String data){
        Random random = new Random();
        if(StringUtils.isNotEmpty(data)) {
        	JSONObject json=JSONObject.parseObject(data);
        	JSONArray dataArray=(JSONArray) json.get("data");

        try {
        	for(int i=0;i<dataArray.size();i++) {
        	JSONObject dataJson=(JSONObject) dataArray.get(i);
        	ReceiveInfo receiveInfo=new ReceiveInfo();
            int ends = random.nextInt(100);//产生两位随机数
            String dateUUid= DateUtils.format(new Date(), "yyyyMMddHHmmss") + String.format("%02d",ends);
            receiveInfo.setId(dateUUid);
            receiveInfo.setRepname(dataJson.getString("PC_CD"));
            receiveInfo.setRepphone(dataJson.getString("PHONE"));
            receiveInfo.setEventaddr(dataJson.getString("ADDR"));
            receiveInfo.setEventdesc("问题描述："+dataJson.getString("DIR"));
            receiveInfo.setCreateDate(new Date());
            String lon=dataJson.getString("LON");
            String lot=dataJson.getString("LOT");
            receiveInfo.setSource_type("13");
            if(StringUtils.isNotEmpty(lon)&&StringUtils.isNotEmpty(lot)) {
                receiveInfo.setLat_lon(lot+","+lon);
            }
            receiveinfoService.insert(receiveInfo);
        	}
        } catch (Exception e) {     	
            e.printStackTrace();
            return ResponseEntity.ok(R.error());
        }   
        return ResponseEntity.ok(R.ok());
        
        }
		return  ResponseEntity.ok(R.error());
        }

   
	
}
