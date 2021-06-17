package com.bootdo.dispatch.center.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.app.util.LocationUtils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.res.EmergencyEquipment;
import com.bootdo.dispatch.center.res.EmergencyTeamRes;
import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.dispatch.center.res.WatchmanRes;
import com.bootdo.dispatch.center.res.group.*;
import com.bootdo.support.entity.SupportArroundInfo;
import com.bootdo.support.entity.SupportGeoInfo;
import com.bootdo.support.service.ExpertInfoService;
import com.bootdo.support.service.GeoInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
@RestController
@RequestMapping("/dispatch/res")
public class DispatchResController {

    private static final Logger logger = LoggerFactory.getLogger(DispatchResController.class);

    @Autowired
    private EmergencyTeamResGroup emergencyTeamResGroup;
    @Autowired
    private CameraResGroup cameraResGroup;
    @Autowired
    private ExpertResGroup expertResGroup;
    @Autowired
    private WatchmanResGroup watchmanResGroup;
    @Autowired
    private EmergencyEquipmentGroup emergencyEquipmentGroup;
    @Autowired
    private EmergencyVehiclesGroup emergencyVehiclesGroup;

    @Autowired
    private ExpertInfoService expertInfoService;

    @Autowired
    private GoodResGroup goodResGroup;
    @Autowired
    private GeoInfoService geoInfoService;

    //监控视频文件
    @Value("classpath:static/js/camera.json")
    private Resource areaRes;


    @ApiOperation(value="获取所有应急队伍", notes="")
    @GetMapping("/emergencyTeam")
    public ResponseEntity getAllTeam(){
        try {
            return ResponseEntity.ok(R.ok().put("data",emergencyTeamResGroup.getAllRes()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="获取所有摄像头", notes="")
    @GetMapping("/camera")
    public ResponseEntity getAllCamera(){
        try {
            return ResponseEntity.ok(R.ok().put("data",cameraResGroup.getAllRes()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="获取所有专家", notes="")
    @GetMapping("/expert")
    public ResponseEntity getAllExpert(@RequestParam(value = "warnTypeId", required = false, defaultValue = "")String warnTypeId){
        try {
            return ResponseEntity.ok(R.ok().put("data",expertInfoService.getAllExpertByWarnTypeId(warnTypeId)));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="获取当天值守人员", notes="")
    @GetMapping("/watchman")
    public ResponseEntity getAllWatchman(){
        try {
            return ResponseEntity.ok(R.ok().put("data",watchmanResGroup.getAllRes()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="所有装备(不包含车辆)", notes="")
    @GetMapping("/equipment")
    public ResponseEntity getAllEquipment(){
        try {
            return ResponseEntity.ok(R.ok().put("data",emergencyEquipmentGroup.getAllRes()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="所有车辆", notes="")
    @GetMapping("/vehicles")
    public ResponseEntity getAllVehicles(){
        try {
            return ResponseEntity.ok(R.ok().put("data",emergencyVehiclesGroup.getAllRes()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="所有物资", notes="")
    @GetMapping("/goods")
    public ResponseEntity getAllGoods(){
        try {
            return ResponseEntity.ok(R.ok().put("data",goodResGroup.bulidGoodTree()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }


    @ApiOperation(value="所有物资", notes="")
    @GetMapping("/groupGoods")
    @ResponseBody
    public ResponseEntity getAllGoodsGroupByHouse(){
        try {
            return ResponseEntity.ok(R.ok().put("data",goodResGroup.bulidGoodHouseTree()));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    @ApiOperation(value="关注对象", notes="")
    @GetMapping("/getAttentionObject")
    @ResponseBody
    public ResponseEntity getAttentionObject(){
        try {
            JSONObject jsonObj=new JSONObject();
            List<Map<String, Object>> geotypeList =  geoInfoService.getGeoTypeInfo();
            if(geotypeList !=null && geotypeList.size()>0){
                for (Map<String, Object> geotype:geotypeList) {
                    String geotypeId = (String) geotype.get("id");
                    String type = (String) geotype.get("TYPE");
                    List<Map<String, Object>> geoInfoList = geoInfoService.getByGeotypeId(geotypeId);

                    jsonObj.put(type,geoInfoList);
                }
            }
            return ResponseEntity.ok(R.ok().put("data",jsonObj));
        } catch (Exception e) {
            logger.error("get res error",e);
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }


    @ApiOperation(value="APP应急资源", notes="")
    @GetMapping("/geAPPEmergencyGoods")
    @ResponseBody
    public ResponseEntity geAPPEmergencyGoods(@RequestParam(required = false)String centerLat,
                                              @RequestParam(required = false)String centerLon,
                                              @RequestParam(required = false)String warnTypeId){
        if (StringUtils.isEmpty(centerLat) || StringUtils.isEmpty(centerLon)) return null;
        try {
            //关注人群和建筑
            List<JSONObject> crowd = new ArrayList<>();
            List<JSONObject> build = new ArrayList<>();
            List<SupportArroundInfo> arroundInfoList = geoInfoService.getArroundGEOInfoData();
            if(CollectionUtils.isNotEmpty(arroundInfoList)){
                for (SupportArroundInfo arroundInfo : arroundInfoList){
                    String attentionType = arroundInfo.getAttention_type();//crowd or building
                        List<SupportGeoInfo> geoInfos = arroundInfo.getGeoInfos();
                        for (SupportGeoInfo geoInfo : geoInfos ){
                            String lat_lon = geoInfo.getLat_lon();
                            if (StringUtils.isEmpty(lat_lon)) continue;
                            double distance = getDistance(lat_lon, centerLat,centerLon);
                            if (distance>=0&&distance<=500){
                                //获取人群信息重新封装
                                JSONObject obj = new JSONObject();
                                obj.put("goodsType", arroundInfo.getName());
                                obj.put("goodsName", geoInfo.getName());
                                obj.put("goodsAddr", geoInfo.getAddr());
                                obj.put("goodsContact", geoInfo.getContact());
                                obj.put("goodsContactNum", geoInfo.getContact_number());
                                obj.put("goodsNumber", geoInfo.getNumber());
                                if ("crowd".equals(attentionType)) {
                                    crowd.add(obj);
                                }
                                if ("building".equals(attentionType)){
                                    build.add(obj);
                                }
                            }
                        }
                }
            }
            //队伍
            List<JSONObject> team = new ArrayList<>();
            List<EmergencyTeamRes> teamList = emergencyTeamResGroup.getAllRes();
            if(CollectionUtils.isNotEmpty(teamList)) {
                for (EmergencyTeamRes teamRes : teamList){
                    if(StringUtils.isEmpty(teamRes.getLat()) || StringUtils.isEmpty(teamRes.getLon())) continue;
                    double distance = getDistance(teamRes.getLat()+","+ teamRes.getLon(),centerLat,centerLon);
                    if(distance>=0&&distance<=500){
                        JSONObject obj = new JSONObject();
                        obj.put("goodsType", "应急队伍");
                        obj.put("goodsName", teamRes.getTeamName());
                        obj.put("goodsAddr", teamRes.getAddress());
                        obj.put("goodsContact", teamRes.getContact());
                        obj.put("goodsContactNum", teamRes.getMobile());
                        obj.put("goodsNumber", teamRes.getNumOfTeam());
                        team.add(obj);
                    }
                }
            }
            //专家
            List<JSONObject> expert = new ArrayList<>();
            List<ExpertRes> expertResList = expertInfoService.getAllExpertByWarnTypeId(warnTypeId);
            if (CollectionUtils.isNotEmpty(expertResList)){
                for (ExpertRes expertRes : expertResList){
                    JSONObject obj = new JSONObject();
                    obj.put("goodsType", "专家");
                    obj.put("goodsName", expertRes.getGoodat());
                    obj.put("goodsAddr", expertRes.getAddr());
                    obj.put("goodsContact", expertRes.getName());
                    obj.put("goodsContactNum", expertRes.getMobile());
                    obj.put("goodsNumber", 1);
                    expert.add(obj);
                }
            }
            //值守人员
            List<JSONObject> watchman = new ArrayList<>();
            List<WatchmanRes> watchmanList =  watchmanResGroup.getAllRes();
            if(CollectionUtils.isNotEmpty(watchmanList)){
                for (WatchmanRes watchmanRes : watchmanList){
                    JSONObject obj = new JSONObject();
                    obj.put("goodsType", "值守人员");
                    obj.put("goodsName", watchmanRes.getName());
                    obj.put("goodsAddr", "");
                    obj.put("goodsContact", watchmanRes.getName());
                    obj.put("goodsContactNum", watchmanRes.getMobile());
                    obj.put("goodsNumber", 1);
                    watchman.add(obj);
                }
            }
            //监控视频
            List<JSONObject> camera = new ArrayList<>();
            String cameraData =  IOUtils.toString(areaRes.getInputStream(), Charset.forName("UTF-8"));
            JSONObject cameraJsonobject = JSONObject.parseObject(cameraData);
            JSONArray cameraJsonArray = cameraJsonobject.getJSONArray("monitorInfoListJson");
            for (int i = 0 ; i < cameraJsonArray.size() ; i++){
                JSONObject jsonObject = cameraJsonArray.getJSONObject(i);
                //计算距离
                if (StringUtils.isEmpty(jsonObject.getString("latitude")) || StringUtils.isEmpty(jsonObject.getString("longitude"))) continue;
                String latLon = jsonObject.getString("latitude")+","+jsonObject.getString("longitude");
                double distance = getDistance(latLon,centerLat,centerLon);
                if (distance>=0&&distance<=500){
                    JSONObject obj = new JSONObject();
                    obj.put("goodsType", "监控视频");
                    obj.put("goodsName", jsonObject.getString("passageNmae"));
                    obj.put("goodsAddr", jsonObject.getString("village"));
                    obj.put("goodsContact", "");
                    obj.put("goodsContactNum", "");
                    obj.put("goodsNumber", jsonObject.getString("number"));
                    camera.add(obj);
                }
            }
            //应急装备
            List<JSONObject> equipment = new ArrayList<>();
            List<EmergencyEquipment> equipmentList = emergencyEquipmentGroup.getAllRes();
            if (CollectionUtils.isNotEmpty(equipmentList)){
                for (EmergencyEquipment equi : equipmentList){
                    if (StringUtils.isEmpty(equi.getLatLon())) continue;
                    double distance = getDistance(equi.getLatLon(),centerLat,centerLon);
                    if (distance>=0&&distance<=500){
                        JSONObject obj = new JSONObject();
                        obj.put("goodsType", "应急装备");
                        obj.put("goodsName", equi.getName());
                        obj.put("goodsAddr", equi.getAddr());
                        obj.put("goodsContact", equi.getContact());
                        obj.put("goodsContactNum", equi.getMobile());
                        obj.put("goodsNumber", "");
                        equipment.add(obj);
                    }
                }
            }

            JSONObject resultObj = new JSONObject();
            resultObj.put("attentionCrowd", crowd);
            resultObj.put("attentionBuild", build);
            resultObj.put("team", team);
            resultObj.put("expert", expert);
            resultObj.put("watchman", watchman);
            resultObj.put("camera", camera);
            resultObj.put("equipment", equipment);

            return ResponseEntity.ok(R.ok().put("data",resultObj));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(R.error().put("data",Collections.emptyList()));
        }
    }

    public double getDistance(String latLon, String centerLat,String centerLon){
        if (StringUtils.isEmpty(latLon)) return -0.;
        if (StringUtils.isEmpty(centerLat)) return -0.;
        if (StringUtils.isEmpty(centerLon)) return -0.;

        String[] latLonArr = latLon.split(",");
        double latd = Double.valueOf(latLonArr[0]);
        double lond = Double.valueOf(latLonArr[1]);
        double centerLatD = Double.valueOf(centerLat);
        double centerLonD = Double.valueOf(centerLon);
        return LocationUtils.getDistance(latd,lond,centerLatD,centerLonD);
    }


}
