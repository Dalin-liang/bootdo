package com.bootdo.support.service;

import com.bootdo.support.dto.SupportEquipmentDTO;
import com.bootdo.support.entity.SupportEquipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EquipmentService {
    int insert(SupportEquipment supportEquipment);

    List<Map<String,Object>> get(SupportEquipmentDTO supportEquipmentDTO);

    int update(SupportEquipment supportEquipment);

    int delete(@Param("id")String id);

    int logicalDelete(@Param("id")String id);

    Map<String,Object> getUniqueById(@Param("id")String id);
}
