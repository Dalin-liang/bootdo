package com.bootdo.dispatch.center.res.group;

import com.bootdo.common.utils.CommonUtils;
import com.bootdo.dispatch.center.base.DispatchResGroup;
import com.bootdo.dispatch.center.res.EmergencyTeamRes;
import com.bootdo.dispatch.center.vo.TeamMemberVO;
import com.bootdo.support.dao.DeptPersonMapper;
import com.bootdo.support.dao.TeamMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/27
 */
@Service
public class EmergencyTeamResGroup implements DispatchResGroup<EmergencyTeamRes> {


    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private DeptPersonMapper deptPersonMapper;

    @Override
    public List<EmergencyTeamRes> getAllRes() {
        List<EmergencyTeamRes> data = teamMapper.getAllTeam();
        if(CollectionUtils.isEmpty(data))   return data;
        Map<String,EmergencyTeamRes> mapping = new HashMap<>(data.size());
        for (EmergencyTeamRes datum : data) {
            datum.ready();
            mapping.put(datum.getResId(),datum);
        }
        List<TeamMemberVO> members = deptPersonMapper.getDeptPersonByTeamIds(mapping.keySet());
        if(CommonUtils.isEmpty(members))    return data;
        String teamId;
        EmergencyTeamRes team;
        for (TeamMemberVO member : members) {
            teamId = member.getTeamId();
            team = mapping.get(teamId);
            if(team.getMembers()==null){
                team.setMembers(new ArrayList<>(10));
            }
            team.getMembers().add(member);
        }
        return data;
    }
}
