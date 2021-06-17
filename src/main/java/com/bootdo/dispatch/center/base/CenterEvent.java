package com.bootdo.dispatch.center.base;

import java.util.Date;

/**
 * 上大屏的事件
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public interface CenterEvent extends ReLocatable {

    enum EventState{
        WAIT_HANDLE(0),PROCESSING(1),ACTION_PROG(5),END(30);
        int value;
        EventState(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    /**
     * 事件Id
     */
    String getEventId();

    /**
     * 事件辐射半径
     * @return
     */
    default int getRadius(){
        return 500;
    }

    /**
     * 地址
     * @return
     */
    String getAddress();

    /**
     * 事件描述
     * @return
     */
    String getEventDesc();

    /**
     * 事件类型
     * @return
     */
    String getEventType();

    /**
     * 上报时间
     * @return
     */
    Date getRepTime();

    /**
     * 上报人
     * @return
     */
    String getRepName();

    /**
     * 上报人联系方式
     * @return
     */
    String getRepPhone();

    /**
     * 上报单位
     * @return
     */
    String getRepDept();

    /**
     * 上报事件经纬度
     * @return
     */
    String getLatLon();

    /**
     * 预警级别
     * @return
     */
    String getWarnLevel();

    /**
     * 预警级别Id
     * @return
     */
    String getWarnLevelId();

    /**
     * 预警类别
     * @return
     */
    String getWarnType();

    /**
     * 执行方案Id
     * @return
     */
    String getActionProgramId();
    /**
     * 推送后的处理时间
     * @return
     */
    Date getHandleTime();
    /**
     * 事件来源
     * @return
     */
    String getSourceType();
   
    
    String getDispatchPlanmainId();
    

    /**
     * 事件描述语音
     * @return
     */
    void setEventdescAudio(String eventDescAudio);
    
    String getEventdescAudio();
    /**
     * 更新执行方法
     * @param ctionProgramId
     */
    void setActionProgramId(String ctionProgramId);

    
    /**
     * 事件状态,1待启预案,5预案执行,30结束
     * @return
     */
    int getEventState();



}
