package com.bootdo.dispatch.center.base;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public interface ReLocatable extends Locatable {
    /**
     * 更新定位
     * @param location
     */
    void setLocation(Location location);


    void setAddress(String address);
}
