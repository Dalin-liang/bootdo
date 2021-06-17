package com.bootdo.dispatch.center.service;

import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.dispatch.center.base.CenterEvent;

import java.math.BigDecimal;
import java.util.List;

/**
 * 大屏事件服务
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public interface BaseEventService {

    /**
     * 获取当前在大屏上的事件,用户订阅的时候主动推送
     * @return
     */
    List<? extends CenterEvent> getCurrEvent();

    /**
     * 获取当前在大屏上的未处理事件,用户订阅的时候主动推送
     * @return
     */
    List<? extends CenterEvent> getCurrUntreatedEvent();
    /**
     * 新未处理事件要推上大屏
     * @param event
     * @return
     */
    boolean newUntreatedEvent(CenterEvent event);
    
    /**
     * 新未处理事件要推上大屏
     * @param event
     * @return
     */
    boolean newEvent(CenterEvent event);

    /**
     * 综合态势事件推送添加事件
     * @param event
     * @return
     */
    boolean newSituationEvent(CenterEvent event);

    /**
     * 开始处理事件
     * @param eventId
     * @return
     */
    boolean startProcessEvent(String eventId);

    /**
     * 开始处理物联设备报警事件
     * @param eventId
     * @return
     */
    boolean startProcessEventByLot(String eventId);
    /**
     * 事件启用预案并生成方案
     * @return
     */
    boolean startActionProg(String eventId, String actionProgId);

    /**
     * 取消事件当前方案
     * @param eventId 事件Id
     * @return
     */
    boolean cancelActionProg(String eventId);

    /**
     * 结束事件
     * @param eventId 事件Id
     * @return
     */
    boolean endEvent(String eventId);

    /**
     * 删除已处理告警事件
     * @param eventId 事件Id
     * @return
     */
    boolean endWarnEvent(String eventId);

    /**
     * 根据Id获取事件
     * @param eventId
     * @return
     */
    CenterEvent getEventById(String eventId);

    /**
     *
     * @param eventId
     * @param lat
     * @param lon
     * @param address
     */
    boolean eventReLocation(String eventId, BigDecimal lat, BigDecimal lon, String address);


    /**
     * 往前端推送日志
     * @param log
     */
    void publishLog(DispatchLogDO log);

    /**
     * app任务反馈内容
     */
    void appTaskFeedBack(String actionprogramId);
    
    
}
