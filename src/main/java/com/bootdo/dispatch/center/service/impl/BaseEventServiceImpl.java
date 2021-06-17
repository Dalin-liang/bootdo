package com.bootdo.dispatch.center.service.impl;

import com.bootdo.actionprogramManage.domain.DispatchLogDO;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.base.CenterEvent.EventState;
import com.bootdo.dispatch.center.base.Location.SimpleLocation;
import com.bootdo.dispatch.center.service.BaseEventService;
import com.bootdo.dispatch.center.vo.BaseEventVO;
import com.bootdo.dispatch.center.ws.msg.SimpWsMsg;
import com.bootdo.dispatch.center.ws.msg.WsMsgType;
import com.bootdo.support.dao.ReceiveInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/21
 */
@Service
public class BaseEventServiceImpl implements BaseEventService,InitializingBean {

    @Autowired
    private ReceiveInfoMapper receiveInfoMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private List<CenterEvent> eventList;
    
    private List<CenterEvent> UnTreatedEventList;
    
    private int count=499;


    @Override
    public List<CenterEvent> getCurrEvent() {
        readWriteLock.readLock().lock();
        try{
            return Collections.unmodifiableList(this.eventList);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public boolean newEvent(CenterEvent event) {
        readWriteLock.writeLock().lock();
        try{
            this.eventList.add(event);
            messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.NEW_EVENT,event));
            return true;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 综合态势事件推送时首先添加事件到eventList列表中
     * @param event
     * @return
     */
    @Override
    public boolean newSituationEvent(CenterEvent event) {
        readWriteLock.writeLock().lock();
        try{
            this.eventList.add(event);
            return true;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public boolean startProcessEvent(String eventId){
        readWriteLock.writeLock().lock();
        try{
            CenterEvent event = findEvent(eventId);
            if(event!=null){
                if(receiveInfoMapper.updateInHandle(eventId)==1){      
                	CenterEvent info= receiveInfoMapper.getEventById(eventId);
                    ((BaseEventVO)event).setEventState(EventState.PROCESSING.getValue());   
                    ((BaseEventVO)event).setHandleTime(info.getHandleTime());

                   //推送过去的事件如果还存在未处理的事件中则，删除
                   if(this.UnTreatedEventList !=null){
                       Iterator<CenterEvent> cet = this.UnTreatedEventList.iterator();
                       while(cet.hasNext()){
                            CenterEvent uncentEvent = cet.next();
                            if(uncentEvent.getEventId().equals(eventId)){
                                cet.remove();
                            }
                       }
                   }
                    messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.START_PROCESS_EVENT,event));
                    return true;
                }
            }
            return false;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    
    public boolean startProcessEventByLot(String eventId){
        readWriteLock.writeLock().lock();
        try{
            CenterEvent event = findEvent(eventId);
            if(event!=null){
                if(receiveInfoMapper.updateInHandle(eventId)==1){      
                	CenterEvent info= receiveInfoMapper.getEventById(eventId);
                    ((BaseEventVO)event).setEventState(EventState.ACTION_PROG.getValue());   
                    ((BaseEventVO)event).setHandleTime(info.getHandleTime());
                    String [] location=event.getLatLon().split(",");
                    if(location.length==2) {
                    	  event.setLocation(new SimpleLocation(location[1],location[0]));
                    }
                  
                   //推送过去的事件如果还存在未处理的事件中则，删除
                   if(this.UnTreatedEventList !=null){
                       Iterator<CenterEvent> cet = this.UnTreatedEventList.iterator();
                       while(cet.hasNext()){
                            CenterEvent uncentEvent = cet.next();
                            if(uncentEvent.getEventId().equals(eventId)){
                                cet.remove();
                            }
                       }
                   }
                    messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.START_PROCESS_EVENT,event));
                    return true;
                }
            }
            return false;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean startActionProg(String eventId, String actionProgId) {
        Assert.hasText(eventId,"eventId must has text");
        Assert.hasText(actionProgId,"actionProgId must has text");
        readWriteLock.writeLock().lock();
        CenterEvent event;
        try{
            event = findEvent(eventId);
            if(event!=null){
                event.setActionProgramId(actionProgId);
                ((BaseEventVO)event).setEventState(EventState.ACTION_PROG.getValue());
            }else {
                return false;
            }
        }finally {
            readWriteLock.writeLock().unlock();
        }
        messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.START_ACTION,event));
        return true;
    }

    @Override
    public boolean cancelActionProg(String eventId) {
        Assert.hasText(eventId,"eventId must has text");
        readWriteLock.writeLock().lock();
        try{
            CenterEvent event = findEvent(eventId);
            if(event!=null){
                event.setActionProgramId(null);
                ((BaseEventVO)event).setEventState(EventState.PROCESSING.getValue());
                messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.CANCEL_ACTION,event));
                return true;
            }
            return false;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean endEvent(String eventId) {
        Assert.hasText(eventId,"eventId must has text");
        readWriteLock.writeLock().lock();
        try{
            CenterEvent event = findEvent(eventId);
            if(event!=null){
                this.eventList.remove(event);
                ((BaseEventVO)event).setEventState(EventState.END.getValue());
                messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.END_EVENT,event));
                return true;
            }
            return false;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public CenterEvent getEventById(String eventId) {
        return receiveInfoMapper.getEventById(eventId);
    }

    @Override
    public boolean eventReLocation(String eventId, BigDecimal lat, BigDecimal lon, String address) {
        Assert.hasText(eventId,"eventId must has text");
        Assert.notNull(lat,"lat must has text");
        Assert.notNull(lon,"lon must has text");
        readWriteLock.writeLock().lock();
        try{
            CenterEvent event = findEvent(eventId);
            if(event!=null){
                event.setLocation(new SimpleLocation(lon,lat));
                event.setAddress(address);
                messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.EVENT_RE_LOCATION,event));
                return true;
            }
            return false;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }


    @Override
    public void publishLog(DispatchLogDO log) {
        if(log==null||log.getShowBigscreen()==null||log.getShowBigscreen()==0)   return;
        Assert.notNull(log,"log must has text");
        messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.NEW_LOG,log));
    }

    @Override
    public void appTaskFeedBack(String actionprogramId) {
        //提示app反馈任务后页面刷新
        Map<String, Object> map = new HashMap<>();
        map.put("actionprogramId", actionprogramId);
        messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.APP_TASK_FEEDBACK,map));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
        initUnEvenTreated();
    }

    protected void init(){
        readWriteLock.writeLock().lock();
        try{
            List<BaseEventVO> baseEventList = receiveInfoMapper.getProcessingEvent();
            if(CollectionUtils.isNotEmpty(baseEventList)){
                for (BaseEventVO baseEventVO : baseEventList) {
                    baseEventVO.readyLocation();//初始化经纬度数据
                    if(baseEventVO.getActionProgramId()!=null){
                        //已启动方案
                        baseEventVO.setEventState(EventState.ACTION_PROG.getValue());
                    }else{
                        //TODO:未启动方案的时候,区分处理/待处理
                    }
                }
            }
            System.out.println(baseEventList);
            this.eventList = new ArrayList<>(baseEventList);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    
    protected void initUnEvenTreated(){
        readWriteLock.writeLock().lock();
        try{
            List<BaseEventVO> baseEventList = receiveInfoMapper.getWarnByDevice();
   
            System.out.println(baseEventList);
            this.UnTreatedEventList = new ArrayList<>(baseEventList);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private CenterEvent findEvent(String eventId) {
        if(CollectionUtils.isEmpty(this.eventList)) return null;
        for (CenterEvent centerEvent : this.eventList) {
            if(eventId.equals(centerEvent.getEventId()))    return centerEvent;
        }
        return null;
    }
    
    private CenterEvent findWarnEvent(String eventId) {
        if(CollectionUtils.isEmpty(this.UnTreatedEventList)) return null;
        for (CenterEvent centerEvent : this.UnTreatedEventList) {
            if(eventId.equals(centerEvent.getEventId()))    return centerEvent;
        }
        return null;
    }

	@Override
	public List<? extends CenterEvent> getCurrUntreatedEvent() {
		// TODO Auto-generated method stub
        readWriteLock.readLock().lock();
        try{
            return Collections.unmodifiableList(this.UnTreatedEventList);
        }finally {
            readWriteLock.readLock().unlock();
        }
	}

	@Override
	public boolean newUntreatedEvent(CenterEvent event) {
		  readWriteLock.writeLock().lock();
	        try{
	        	if(UnTreatedEventList.size()>=500) {
	            this.UnTreatedEventList.remove(count);
	            if(count==0) {
	            	count=499;
	            }else {
	            	count--;
	            }
	        	}
	            this.UnTreatedEventList.add(event);
	          
	            
	            messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.UN_TREATEDEVENT,event));
	            return true;
	        }finally {
	            readWriteLock.writeLock().unlock();
	        }
	}

	@Override
	public boolean endWarnEvent(String eventId) {
	      Assert.hasText(eventId,"eventId must has text");
	        readWriteLock.writeLock().lock();
	        try{
	            CenterEvent event = findWarnEvent(eventId);
	            if(event!=null){
	                //this.UnTreatedEventList.remove(event);
                    if(this.UnTreatedEventList !=null){
                        Iterator<CenterEvent> cet = this.UnTreatedEventList.iterator();
                        while(cet.hasNext()){
                            CenterEvent uncentEvent = cet.next();
                            if(uncentEvent.getEventId().equals(eventId)){
                                cet.remove();
                            }
                        }
                    }
	                ((BaseEventVO)event).setEventState(EventState.END.getValue());
	                messagingTemplate.convertAndSend("/topic/dispatchCenter",new SimpWsMsg(WsMsgType.END_EVENT,event));
	                return true;
	            }
	            return false;
	        }finally {
	            readWriteLock.writeLock().unlock();
	        }
	}

}
