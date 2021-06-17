package com.bootdo.support.service.impl;

import com.bootdo.support.dao.TeamMapper;
import com.bootdo.support.dto.SupportTeamDTO;
import com.bootdo.support.entity.SupportDeptDO;
import com.bootdo.support.entity.SupportTeam;
import com.bootdo.support.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportTeam supportTeam) {
        return teamMapper.insert(supportTeam);
    }

    @Override
    public List<Map<String, Object>> get(SupportTeamDTO supportTeamDTO) {
        return teamMapper.get(supportTeamDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportTeam supportTeam) {
        return teamMapper.update(supportTeam);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return teamMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return teamMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return teamMapper.getUniqueById(id);
    }

    @Override
    public List<Map<String,Object>> getDept(){
        return teamMapper.getDept();
    }

    @Override
    public List<Map<String, Object>> getTeamMate(String id) {
        return teamMapper.getTeamMate(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int insertTeam(String team_id, String deptperson_id) {
        return teamMapper.insertTeam(team_id,deptperson_id);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteTeam(String team_id) {
        return teamMapper.deleteTeam(team_id);
    }

    @Override
    public SupportTeamDTO getByName(String name) {
        return teamMapper.getByName(name);
    }

    @Override
    public List<SupportTeam> getTeamAndPerson() {
        return teamMapper.getTeamAndPerson();
    }


}
