package com.bootdo.dispatch.center.ws.msg;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/22
 */
public enum WsMsgType {
    //初始化事件,新事件发布,开启方案,取消方案,事件结束,二次定位,新日志
    EVENT_INIT,NEW_EVENT,START_ACTION,CANCEL_ACTION,END_EVENT,EVENT_RE_LOCATION,NEW_LOG,
    //处理事件           //app任务反馈					//未处理事件
    START_PROCESS_EVENT,APP_TASK_FEEDBACK,UN_TREATEDEVENT

}
