package com.bootdo.dispatch.center.res;

import com.bootdo.dispatch.center.base.*;

/**
 * 应急车辆
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/23
 */
public class EmergencyVehiclesRes extends EmergencyEquipment implements ReLocatable {

    @Override
    public ResType getResTypeEnum() {
        return ResType.EMERGENCY_VEHICLES;
    }

    @Override
    public void setAddress(String address) {

    }
}
