package com.bootdo.support.service.impl;

import com.bootdo.support.dao.StoragePlanMapper;
import com.bootdo.support.dto.SupportStoragePlanDTO;
import com.bootdo.support.entity.SupportStoragePlan;
import com.bootdo.support.service.StoragePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class StoragePlanServiceImpl implements StoragePlanService {

    @Autowired
    private StoragePlanMapper storagePlanMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportStoragePlan supportStoragePlan) {
        return storagePlanMapper.insert(supportStoragePlan);
    }

    @Override
    public List<Map<String, Object>> get(SupportStoragePlanDTO supportStoragePlanDTO) {
        return storagePlanMapper.get(supportStoragePlanDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportStoragePlan supportStoragePlan) {
        return storagePlanMapper.update(supportStoragePlan);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return storagePlanMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return storagePlanMapper.logicalDelete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> getUniqueById(String id) {
        return storagePlanMapper.getUniqueById(id);
    }

    @Override
    public List<Map<String, Object>> getStoreHouse() {
        return storagePlanMapper.getStoreHouse();
    }

    @Override
    public List<Map<String, Object>> getGoods() {
        return storagePlanMapper.getGoods();
    }


}
