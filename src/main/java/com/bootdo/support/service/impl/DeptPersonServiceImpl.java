package com.bootdo.support.service.impl;

import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.dto.SupportDeptPersonDTO;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportDeptPerson;
import com.bootdo.support.service.DeptPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DeptPersonServiceImpl implements DeptPersonService {

    @Autowired
    private DeptPersonMapper deptPersonMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportDeptPerson supportDeptPerson) {
        return deptPersonMapper.insert(supportDeptPerson);
    }

    @Override
    public List<Map<String, Object>> get(SupportDeptPersonDTO supportDeptPersonDTO) {
        return deptPersonMapper.get(supportDeptPersonDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportDeptPerson supportDeptPerson) {
        return deptPersonMapper.update(supportDeptPerson);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return deptPersonMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return deptPersonMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return deptPersonMapper.getUniqueById(id);
    }

	@Override
	public List<Map<String, Object>> getEmergencyDept() {
		// TODO Auto-generated method stub
		return deptPersonMapper.getEmergencyDept();
	}

    @Override
    public List<Map<String, Object>> getSysUser() {
        return deptPersonMapper.getSysUser();
    }

    @Override
    public List<Map<String, Object>> getUnrelatedSysUser() {
        return deptPersonMapper.getUnrelatedSysUser();
    }

    @Override
    public List<Map<String, Object>> getUnrelatedAndOneSysUser(String personId) {
        return deptPersonMapper.getUnrelatedAndOneSysUser(personId);
    }

    @Override
    public int getSysUserCountById(String id) {
        return deptPersonMapper.getSysUserCountById(id);
    }


    @Override
    @Transactional(readOnly = false)
    public int updateSysUserPersoinId(String deptpersonId, String userId) {
        return deptPersonMapper.updateSysUserPersoinId(deptpersonId, userId);
    }

    @Override
    @Transactional(readOnly = false)
    public int clearSysUserPersonId(String id) {
        return deptPersonMapper.clearSysUserPersonId(id);
    }

    @Override
    public List<Map<String, Object>> getDeptpersonByDeptId(String id) {
        return deptPersonMapper.getDeptpersonByDeptId(id);
    }

    @Override
    public SupportDeptDO getDeptByDeptPersonId(String id) {
        return deptPersonMapper.getDeptByDeptPersonId(id);
    }

	@Override
	public List<SupportDeptPerson> getDeptPersonByTeam(String teamId) {
		// TODO Auto-generated method stub
		return deptPersonMapper.getDeptPersonByTeam(teamId);
	}

    @Override
    public Map<String, Object> getDeptPersonInfoByUserId(Long userId) {
        return deptPersonMapper.getDeptPersonInfoByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> getPersonList() {
        return deptPersonMapper.getPersonList();
    }
}
