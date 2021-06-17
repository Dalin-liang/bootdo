package com.bootdo.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.app.dao.APPMapper;
import com.bootdo.app.service.impl.APPEventServiceImpl;
import com.bootdo.app.util.HttpUtils;
import com.bootdo.common.domain.CommonFileDO;
import com.bootdo.common.service.CommonFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * app巡河事件
 */
@Service
public class RiverCruiseService {

    private static final Logger logger = LoggerFactory.getLogger(RiverCruiseService.class);

    @Autowired
    APPEventServiceImpl aPPEventServiceImpl;

    @Autowired
    CommonFileService commonFileService;

    @Autowired
    public APPMapper appMapper;


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    private String cruiseUrl = "http://113.108.176.67:6012/business/multipleServer/multipleTableServer";

    private String eventServiceId = "GetEvermInfo";

    private String eventDetailServiceId = "GetEVERInfo";

    private String eventResultServiceId = "GetEVERYInfos";

    private String STAFF_CD = "";

    private String EVE_TH = "";

    private String HDL_ST = "('1','2','3','4')";

    private String EVE_CH = "('3')";

    private String TO_ORG = "GCD440118000000";

    private String APP_IMAGE_PATH= "http://113.108.176.67:6011/zgappServer/newPic/";

    public static HashMap<String, Object> hashMap = new HashMap<>();

    public void getRiverCruiseEvent() {
        try {
            JSONObject json = new JSONObject();
            json.put("STAFF_CD", STAFF_CD);
            json.put("EVE_TH", EVE_TH);
            json.put("HDL_ST", HDL_ST);
            json.put("EVE_CH", EVE_CH);
            json.put("TO_ORG", TO_ORG);
            //当前时间
            Calendar calendar = Calendar.getInstance();
            //Date startDate = sdf2.parse("2019-08-01");
            //结束时间
            String endTime = sdf2.format(calendar.getTime());
           // calendar.add(Calendar.DAY_OF_MONTH, -2);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            //开始时间
            String startTime = sdf2.format(calendar.getTime());
            json.put("ST", startTime);
            json.put("ET", endTime);
            System.out.println("json=="+json.toJSONString());


            Map<String,Object> map = new HashMap<>();
            map.put("serviceID", eventServiceId);
            map.put("conQuery", json.toJSONString());

           // cruiseurl+=?
            //解析获取所欲设备的接口
            String result = null;
            result = HttpUtils.net(cruiseUrl, map, "GET");
            System.out.println("result=" + result);
            //接口数据转为jsonobject
            JSONObject jsonObject = JSONObject.parseObject(result);
            String jsonResult = jsonObject.get("result").toString();
            if ("0".equals(jsonResult)) {//成功返回数据
                logger.info("APP 查询到巡河上报事件。。。");
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray rows = data.getJSONArray("rows");
                for (int i = 0; i < rows.size(); i++) {
                    JSONObject obj = rows.getJSONObject(i);
                    //事件代码
                    String proCD = obj.get("PRO_CD") != null ? obj.get("PRO_CD").toString() : "";
                    if (!hashMap.containsKey(proCD)){
                        hashMap.put(proCD, "procd");
                        //举报人
                        String STAFFNM = obj.getString("STAFF_NM");
                        //河段名称
                        String CS_NM = obj.getString("CS_NM");
                        //事件地址
                        String eventAddr = obj.getString("ADDR");
                        //事件描述
                        String DIR = obj.getString("DIR");
                        String eventDesc = CS_NM+DIR;

                        JSONObject paramJson = new JSONObject();
                        paramJson.put("PRO_CD", proCD);
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("serviceID", eventDetailServiceId);
                        params.put("conQuery", paramJson.toJSONString());
                        String eventDetailResult = HttpUtils.net(cruiseUrl, params, "GET");
                        JSONObject eventDetailObje = JSONObject.parseObject(eventDetailResult);
                        String eventResult = eventDetailObje.get("result").toString();
                        if ("0".equals(eventResult)) {
                            JSONObject eventDetailData = eventDetailObje.getJSONObject("data");
                            JSONArray eventDetailRows = eventDetailData.getJSONArray("rows");
                            System.out.println("eventDetailRows=="+eventDetailRows);
                            for (int j = 0; j < eventDetailRows.size(); j++) {
                                JSONObject eventDetailRow = eventDetailRows.getJSONObject(j);
                                System.out.println("eventRow=="+eventDetailRow);
                                //事件经纬度
                                String lat = eventDetailRow.get("LOT").toString();
                                String lon = eventDetailRow.get("LON").toString();
                                String MN_TYCD = eventDetailRow.getString("MN_TYCD");
                                String fileName = eventDetailRow.get("PATH").toString();
                                String sourceType = "";
                                if ("河面保洁".equals(MN_TYCD)){
                                   sourceType = "35";
                                }else if ("排污管理".equals(MN_TYCD)){
                                    sourceType = "36";
                                }else if ("河湖管理".equals(MN_TYCD)){
                                    sourceType = "37";
                                }else if ("公众牌设置".equals(MN_TYCD)){
                                    sourceType = "38";
                                }else if ("问题处理".equals(MN_TYCD)){
                                    sourceType = "39";
                                }
                                //插入commonfile表
                                CommonFileDO commonFileDO = new CommonFileDO();
                                String relationId = UUID.randomUUID().toString().replaceAll("-","");
                                commonFileDO.setId(UUID.randomUUID().toString().replaceAll("-",""));
                                commonFileDO.setRelationId(relationId);
                                commonFileDO.setFileType(0);
                                commonFileDO.setFileUrl(APP_IMAGE_PATH + fileName);
                                commonFileDO.setFilePhysicalAddress(APP_IMAGE_PATH + fileName);
                                commonFileDO.setCreateBy("admin");
                                commonFileDO.setCreateDate(new Date());
                                commonFileDO.setFromTableanme("t_app_sign_report");
                                commonFileDO.setFileName(fileName);
                                commonFileService.save(commonFileDO);
                                //插入t_app_sign_report
                                HashMap<String, Object> report = new HashMap<>();
                                report.put("id", relationId);
                                report.put("lat", lat);
                                report.put("lon", lon);
                                report.put("create_by", "admin");
                                report.put("repname", STAFFNM);
                                report.put("eventaddr", eventAddr);
                                report.put("eventdesc", eventDesc);
                                report.put("repdate", new Date());
                                report.put("create_date", new Date());
                                //event_type改为watch_source表中的app上报类型的ID,event_type也是改为从watch_source途径表中获取的
                                report.put("event_type", sourceType);
                                appMapper.dailyreport(report);

                                Map<String, Object> watchInfo = new HashMap<>();
                                //watchInfo.put("id", request.getParameter("id"));
                                watchInfo.put("id", relationId);
                                watchInfo.put("lat", lat);
                                watchInfo.put("lon", lon);
                                watchInfo.put("create_by", "admin");
                                watchInfo.put("repname", STAFFNM);
                                watchInfo.put("eventaddr", eventAddr);
                                watchInfo.put("eventdesc", eventDesc);
                                watchInfo.put("repdate", new Date());
                                watchInfo.put("create_date", new Date());
                                watchInfo.put("event_type", sourceType);//app上报

                                aPPEventServiceImpl.appWarnEventReport(watchInfo);
                                logger.info("APP 巡河上报事件成功。。。");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
