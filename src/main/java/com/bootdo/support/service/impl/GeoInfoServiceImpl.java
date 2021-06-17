package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GeoInfoMapper;
import com.bootdo.support.dto.SupportGeoInfoDTO;
import com.bootdo.support.entity.GeotypeDO;
import com.bootdo.support.entity.SupportArroundInfo;
import com.bootdo.support.entity.SupportGeoInfo;
import com.bootdo.support.service.GeoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GeoInfoServiceImpl implements GeoInfoService {

    @Autowired
    private GeoInfoMapper geoInfoMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportGeoInfo supportGeoInfo) {
        return geoInfoMapper.insert(supportGeoInfo);
    }

    @Override
    public List<Map<String, Object>> get(SupportGeoInfoDTO supportGeoInfoDTO) {
        return geoInfoMapper.get(supportGeoInfoDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportGeoInfo supportGeoInfo) {
        return geoInfoMapper.update(supportGeoInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return geoInfoMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return geoInfoMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return geoInfoMapper.getUniqueById(id);
    }

    @Override
    public List<GeotypeDO> getGeoTypeList() {
        return geoInfoMapper.getGeoTypeList();
    }

    @Override
    public List<Map<String, Object>> getGeoInfo(String name) {
        return geoInfoMapper.getGeoInfo(name);
    }

    @Override
    public List<Map<String, Object>> getArroundInfoData() {
        return geoInfoMapper.getArroundInfoData();
    }

    @Override
    public List<SupportArroundInfo> getArroundGEOInfoData() {
        return geoInfoMapper.getArroundGEOInfoData();
    }

    @Override
    public List<Map<String, Object>> getGeoTypeInfo() {
        return geoInfoMapper.getGeoTypeInfo();
    }

    @Override
    public List<Map<String, Object>> getByGeotypeId(String geotypeId) {
        return geoInfoMapper.getByGeotypeId(geotypeId);
    }

    @Override
    public List<Map<String, Object>> getTypeChart() {
        return geoInfoMapper.getTypeChart();
    }


}
