package com.bootdo.dispatch.center.vo;

import com.bootdo.common.utils.StringUtils;
import com.bootdo.dispatch.center.base.CenterEvent;
import com.bootdo.dispatch.center.base.Location;
import com.bootdo.dispatch.center.base.Location.SimpleLocation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public class BaseEventVO implements CenterEvent,Serializable {

    private static final long serialVersionUID = 6281990658148494503L;

    /**
     * 事件Id
     */
    private String eventId;

    /**
     * 辐射半径
     */
    private int radius = 500;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 事件摘要
     */
    private String eventDesc;

    /**
     * 事件类型Id
     */
    private String eventTypeId;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 上报时间
     */
    private Date repTime;

    /**
     * 上报电话
     */
    private String repPhone;

    /**
     * 上报人
     */
    private String repName;

    /**
     * 上报部门ID
     */
    private String repDeptId;

    /**
     * 上报部门
     */
    private String repDept;

    /**
     * 预警级别ID
     */
    private String warnLevelId;

    /**
     * 预警级别名称
     */
    private String warnLevel;

    /**
     * 预警类别Id
     */
    private String warnTypeId;

    /**
     * 预警类别
     */
    private String warnType;

    /**
     * 执行方案
     */
    private String actionProgramId;

    /**
     * 事件状态
     */
    private int eventState;

    /**
     * 经纬度字符串
     */
    private String latLon;

    /**
     * 坐标
     */
    private Location location;
    /**
     * 推送时间
     */
    private Date pushTime;
    /**
     * 推送后的处理时间
     */
    private Date handleTime;

    private String sourceType;
    
    private String eventdescAudio;
    
    private String dispatchPlanmainId;
    
    private String sourceName;

    private String isFirstData;

    @Override
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }


    @Override
    public Date getRepTime() {
        return repTime;
    }

    public void setRepTime(Date repTime) {
        this.repTime = repTime;
    }

    @Override
    public String getRepPhone() {
        return repPhone;
    }

    public void setRepPhone(String repPhone) {
        this.repPhone = repPhone;
    }

    @Override
    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepDeptId() {
        return repDeptId;
    }

    public void setRepDeptId(String repDeptId) {
        this.repDeptId = repDeptId;
    }

    @Override
    public String getRepDept() {
        return repDept;
    }

    public void setRepDept(String repDept) {
        this.repDept = repDept;
    }



    public String getWarnTypeId() {
        return warnTypeId;
    }

    public void setWarnTypeId(String warnTypeId) {
        this.warnTypeId = warnTypeId;
    }

    @Override
    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    @Override
    public String getActionProgramId() {
        return actionProgramId;
    }

    public void setActionProgramId(String actionProgramId) {
        this.actionProgramId = actionProgramId;
    }

    @Override
    public int getEventState() {
        return eventState;
    }

    public void setEventState(int eventState) {
        this.eventState = eventState;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }


    @Override
    public String getWarnLevelId() {
        return warnLevelId;
    }

    public void setWarnLevelId(String warnLevelId) {
        this.warnLevelId = warnLevelId;
    }

    @Override
    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }

    
    public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	@Override
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	@Override
	public void setEventdescAudio(String eventdescAudio) {
		this.eventdescAudio = eventdescAudio;
	}
	@Override
	public String getEventdescAudio() {
		// TODO Auto-generated method stub
		return eventdescAudio;
	}
	@Override
	public String getDispatchPlanmainId() {
		// TODO Auto-generated method stub
		return dispatchPlanmainId;
	}


	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

    public String getIsFirstData() {
        return isFirstData;
    }

    public void setIsFirstData(String isFirstData) {
        this.isFirstData = isFirstData;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseEventVO{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append(", radius=").append(radius);
        sb.append(", address='").append(address).append('\'');
        sb.append(", eventDesc='").append(eventDesc).append('\'');
        sb.append(", eventTypeId='").append(eventTypeId).append('\'');
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", repTime=").append(repTime);
        sb.append(", repPhone='").append(repPhone).append('\'');
        sb.append(", repName='").append(repName).append('\'');
        sb.append(", repDeptId='").append(repDeptId).append('\'');
        sb.append(", repDept='").append(repDept).append('\'');
        sb.append(", warnLevelId='").append(warnLevelId).append('\'');
        sb.append(", warnLevel='").append(warnLevel).append('\'');
        sb.append(", warnTypeId='").append(warnTypeId).append('\'');
        sb.append(", warnType='").append(warnType).append('\'');
        sb.append(", actionProgramId='").append(actionProgramId).append('\'');
        sb.append(", eventState=").append(eventState);
        sb.append(", latLon='").append(latLon).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }

    public void readyLocation() {

        //去除经纬度中的换行
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(this.latLon);
        this.latLon = m.replaceAll("");
        if(StringUtils.trimToNull(this.latLon)==null){
            this.location=null;
            return;
        }
        String[] data = this.latLon.split(",");
        if(data.length!=2) {
            this.location=null;
            return;
        }
        this.location = new SimpleLocation(data[1],data[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseEventVO) {
            BaseEventVO u = (BaseEventVO) o;
            return this.eventId.equals(u.eventId);
        }
        return super.equals(o);
    }


    @Override
    public int hashCode() {
        return Objects.hash(eventId, radius, address, eventDesc, eventTypeId, eventType, repTime, repPhone, repName, repDeptId, repDept, warnLevelId, warnLevel, warnTypeId, warnType, actionProgramId, eventState, latLon, location);
    }

	





	

	
}
