package com.bootdo.support.service.impl;

import com.bootdo.support.dao.TeamTypeMapper;
import com.bootdo.support.dto.SupportTeamTypeDTO;
import com.bootdo.support.entity.SupportTeamType;
import com.bootdo.support.service.TeamTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class TeamTypeServiceImpl implements TeamTypeService {

    @Autowired
    private TeamTypeMapper teamTypeMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportTeamType supportTeamType) {
        return teamTypeMapper.insert(supportTeamType);
    }

    @Override
    public List<Map<String, Object>> get(SupportTeamTypeDTO supportTeamTypeDTO) {
        return teamTypeMapper.get(supportTeamTypeDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportTeamType supportTeamType) {
        return teamTypeMapper.update(supportTeamType);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return teamTypeMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return teamTypeMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return teamTypeMapper.getUniqueById(id);
    }
}
