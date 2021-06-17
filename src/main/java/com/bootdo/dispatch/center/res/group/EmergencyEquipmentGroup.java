package com.bootdo.dispatch.center.res.group;

import com.bootdo.dispatch.center.base.DispatchResGroup;
import com.bootdo.dispatch.center.res.EmergencyEquipment;
import com.bootdo.dispatch.center.res.EmergencyVehiclesRes;
import com.bootdo.support.dao.EquipmentMapper;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/27
 */
@Service
public class EmergencyEquipmentGroup implements DispatchResGroup<EmergencyEquipment> {


    @Autowired
    private EquipmentMapper equipmentMapper;


    @Override
    public List<EmergencyEquipment> getAllRes() {
        List<EmergencyVehiclesRes> data = equipmentMapper.getAllEmergencyEquipment(1);//非应急车辆的
        if(CollectionUtils.isEmpty(data))   return Collections.emptyList();
        for (EmergencyEquipment datum : data) {
            datum.ready();
        }
        return Lists.newArrayList(data);
    }
}
