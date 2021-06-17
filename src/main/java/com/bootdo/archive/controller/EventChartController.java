package com.bootdo.archive.controller;


import com.alibaba.fastjson.JSONObject;
import com.bootdo.archive.service.ArchiveActionprogramMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/eventChartTJController")
public class EventChartController {

    @Autowired
    private ArchiveActionprogramMainService archiveActionprogramMainService;



    @GetMapping("/getEventType")
    public JSONObject getEventType(@RequestParam("flag") String flag){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = archiveActionprogramMainService.getEventType(flag);
        result.put("data", list);
        return result;
    }

    @GetMapping("/getEventLevel")
    public JSONObject getEventLevel(@RequestParam("flag") String flag){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = archiveActionprogramMainService.getEventLevel(flag);
        result.put("data", list);
        return result;
    }

    @GetMapping("/getEventImport")
    public JSONObject getEventImport(@RequestParam("flag") String flag){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = archiveActionprogramMainService.getEventImport(flag);
        result.put("data", list);
        return result;
    }



    @GetMapping("/getEventCompared")
    public JSONObject getEventCompared(@RequestParam("flag") String flag){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = archiveActionprogramMainService.getEventDataCompared(flag);
        if(list !=null && list.size()>0){
            Object[][] data = new Object[list.size()+1][4];
            data[0][0] = "事件类型";
            data[0][1] = "本月";
            data[0][2] = "同比";
            data[0][3] = "环比";

            for (int i=0; i<list.size();i++) {
                String eventType = list.get(i).get("name").toString();
                Integer yoyCount = archiveActionprogramMainService.getEventYoYCompared(eventType);
                Integer momCount = archiveActionprogramMainService.getEventMoMCompared(eventType);
                data[i+1][0] = eventType;
                data[i+1][1] = Integer.parseInt(list.get(i).get("value").toString());
                data[i+1][2] = yoyCount!=null?yoyCount:0;
                data[i+1][3] = momCount!=null?momCount:0;
            }
            result.put("data",data);
        }else{
            result.put("data",null);
        }

        return result;
    }

    @GetMapping("/getEventDayData")
    public JSONObject getEventDayData(@RequestParam("flag") String flag){
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = null;
        if("year".equals(flag)){
            list = archiveActionprogramMainService.getEventMonthsData();
            result.put("data", changMonthsData(list));
        }else if("month".equals(flag)){
            list = archiveActionprogramMainService.getEventDayData();
            result.put("data", changDayData(list));
        }else{
            result.put("data", null);
        }
        return result;
    }

    private JSONObject changMonthsData(List<Map<String,Object>> list){
        JSONObject dataObject = new JSONObject();
        int [] dataValue =new int []{0,0,0,0,0,0,0,0,0,0,0,0};
        if(list !=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                int mon = Integer.parseInt(list.get(i).get("mon").toString());
                dataValue[mon-1] = Integer.parseInt(list.get(i).get("value").toString());
            }
        }
        dataObject.put("value",dataValue);
        dataObject.put("dataType","mon");
        return dataObject;
    }

    private JSONObject changDayData(List<Map<String,Object>> list){
        JSONObject dataObject = new JSONObject();
        List<String> dateList = getDateList();

        int len = dateList.size();
        String [] day =new String [len];
        int [] dataValue =new int [len];
        if(dateList !=null && dateList.size()>0){
            for (int i=0;i<len ;i++) {
                day[i] = dateList.get(i).substring(5,7)+"月"+dateList.get(i).substring(8,10)+"日";
                if(list !=null && list.size()>0){
                    for (Map<String,Object> map:list) {
                        String date = dateList.get(i);
                        String dataDay = map.get("day").toString();
                        if(date.equals(dataDay)){
                            dataValue[i] = Integer.parseInt(map.get("value").toString());
                            break;
                        }else{
                            dataValue[i] = 0;
                        }
                    }
                }else{
                    dataValue[i] = 0;
                }

            }
        }

        dataObject.put("day",day);
        dataObject.put("value",dataValue);
        dataObject.put("dataType","day");

        return dataObject;
    }



    private List<String> getDateList(){
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance();
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate=null;
            if(month<10&&i<10){
                aDate = String.valueOf(year)+"-0"+month+"-0"+i;
            }
            if(month<10&&i>=10){
                aDate = String.valueOf(year)+"-0"+month+"-"+i;
            }
            if(month>=10&&i<10){
                aDate = String.valueOf(year)+"-"+month+"-0"+i;
            }
            if(month>=10&&i>=10){
                aDate = String.valueOf(year)+"-"+month+"-"+i;
            }

            list.add(aDate);
        }
        return list;
    }

}
