package com.bootdo.dispatch.center.res.group;

import com.bootdo.dispatch.center.base.DispatchResGroup;
import com.bootdo.dispatch.center.res.EmergencyEquipment;
import com.bootdo.dispatch.center.res.EmergencyVehiclesRes;
import com.bootdo.support.dao.EquipmentMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/27
 */
@Service
public class EmergencyVehiclesGroup implements DispatchResGroup<EmergencyVehiclesRes> {


    @Autowired
    private EquipmentMapper equipmentMapper;


    @Override
    public List<EmergencyVehiclesRes> getAllRes() {
        List<EmergencyVehiclesRes> data = equipmentMapper.getAllEmergencyEquipment(2);//非应急车辆的
        if(CollectionUtils.isEmpty(data))   return data;
        for (EmergencyEquipment datum : data) {
            datum.ready();
        }
        return data;
    }
}
