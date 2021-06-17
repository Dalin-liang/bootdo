package com.bootdo.weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/weather")
public class weatherController {

    @RequestMapping("")
    public String weather(){
        return "weather/weather";
    }

    @ResponseBody
    @RequestMapping("/getReport")
    public List<Map<String,Object>> getReport() throws IOException {
        try {
            List list=new ArrayList<Map<String, Object>>();
            String url="http://www.weather.com.cn/weather/101280104.shtml";
            Document doc=null;
            doc = Jsoup.connect(url).timeout(10000)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .cookie("auth", "token").get();
            Elements elements=doc.select("#7d").select(".t").eq(0).select("li");
            ListIterator<Element> li=elements.listIterator();
            while (li.hasNext()){
                Element element=(Element) li.next();
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("天气",element.select(".wea").eq(0).text());
                map.put("气温",element.select(".tem").eq(0).select("span").eq(0).text()+"~"+elements.select(".tem").eq(0).select("i").eq(0).text());
                map.put("风力",element.select(".win").eq(0).select("i").eq(0).text());
                list.add(map);
            }
            return list;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
