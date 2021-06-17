package com.bootdo.support.service.impl;

import com.bootdo.support.dao.EquipmentMapper;
import com.bootdo.support.dto.SupportEquipmentDTO;
import com.bootdo.support.entity.SupportEquipment;
import com.bootdo.support.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportEquipment supportEquipment) {
        return equipmentMapper.insert(supportEquipment);
    }

    @Override
    public List<Map<String, Object>> get(SupportEquipmentDTO supportEquipmentDTO) {
        return equipmentMapper.get(supportEquipmentDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportEquipment supportEquipment) {
        return equipmentMapper.update(supportEquipment);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return equipmentMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return equipmentMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return equipmentMapper.getUniqueById(id);
    }
}
