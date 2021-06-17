package com.bootdo.support.service.impl;

import com.bootdo.support.dao.SupportDeptDao;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.service.SupportDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SupportDeptServiceImpl implements SupportDeptService {


    @Autowired
    private SupportDeptDao supportDeptDao;
    @Override
    public int insert(SupportDeptDO supportDeptDO) {
        return supportDeptDao.insert(supportDeptDO);
    }

    @Override
    public List<Map<String, Object>> get(SupportDeptDO supportDeptDO) {
        return supportDeptDao.get(supportDeptDO);
    }

    @Override
    public int update(SupportDeptDO supportDeptDO) {
        return supportDeptDao.update(supportDeptDO);
    }

    @Override
    public int delete(String id) {
        return supportDeptDao.delete(id);
    }

    @Override
    public int logicalDelete(String id) {
        return supportDeptDao.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return supportDeptDao.getUniqueById(id);
    }

    @Override
    public int getDeptPersonCountById(String id) {
        return supportDeptDao.getDeptPersonCountById(id);
    }

    @Override
    public List<SupportDeptDO> getDeptAndPersons() {
        return supportDeptDao.getDeptAndPersons();
    }

    @Override
    public List<Map<String, Object>> getMaintenancePerson(String name) {
        return supportDeptDao.getMaintenancePerson(name);
    }

    @Override
    public List<String> getDeptIdsByUserId(String userId) {
        return supportDeptDao.getDeptIdsByUserId(userId);
    }

    @Override
    public String getIdByName(String name) {
        return supportDeptDao.getIdByName(name);
    }

}
