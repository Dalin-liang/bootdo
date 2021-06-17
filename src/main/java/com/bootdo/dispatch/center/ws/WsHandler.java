package com.bootdo.dispatch.center.ws;

import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.dispatch.center.ws.msg.SimpWsMsg;
import com.bootdo.dispatch.center.ws.msg.WsMsg;
import com.bootdo.dispatch.center.ws.msg.WsMsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/21
 */
@Controller
public class WsHandler {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private BaseEventService baseEventService;

    @SubscribeMapping("/queue/dispatchCenter")
    public WsMsg test(){
        return new SimpWsMsg(WsMsgType.EVENT_INIT,baseEventService.getCurrEvent());
    }
    @SubscribeMapping("/unTreatedEvent/dispatchCenter")
    public WsMsg unTreatedEvent(){
        return new SimpWsMsg(WsMsgType.EVENT_INIT,baseEventService.getCurrUntreatedEvent());
    }

    @GetMapping("/testOPush")
    @ResponseBody
    public Map<String,Object> testPush(){
        Map<String,Object> result = new HashMap<>(2);
        result.put("key","测试返回数据");
        messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.NEW_EVENT,result));
        //messagingTemplate.convertAndSendToUser("testUser","/queue/dispatchCenter","测试一下推送1");
        return result;
    }




}
