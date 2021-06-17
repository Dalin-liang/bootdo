package com.bootdo.dispatch.center.res;

import com.bootdo.dispatch.center.base.DispatchRes;
import com.bootdo.dispatch.center.base.Locatable;
import com.bootdo.dispatch.center.base.Location;

import java.math.BigDecimal;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/23
 */
public class CameraRes implements DispatchRes,Locatable {


    private String resId;

    private String name;

    private BigDecimal lat;

    private BigDecimal lon;

    private String url;

    private Location location;

    public CameraRes() {
    }

    public CameraRes(String resId, String name, String url, Location location) {
        this.resId = resId;
        this.name = name;
        this.url = url;
        this.location = location;
    }

    @Override
    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public ResType getResTypeEnum() {
        return ResType.CAMERA;
    }
}
