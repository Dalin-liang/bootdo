package com.bootdo.support.service.impl;

import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.planManage.domain.PlanEarlywarnTypeDO;
import com.bootdo.support.dao.ExpertInfoMapper;
import com.bootdo.support.dto.SupportExpertInfoDTO;
import com.bootdo.support.entity.SupportExpertInfo;
import com.bootdo.support.service.ExpertInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ExpertInfoServiceImpl implements ExpertInfoService {

    @Autowired
    private ExpertInfoMapper expertInfoMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportExpertInfo supportExpertInfo) {
        return expertInfoMapper.insert(supportExpertInfo);
    }

    @Override
    public List<Map<String, Object>> get(SupportExpertInfoDTO supportExpertInfoDTO) {
        return expertInfoMapper.get(supportExpertInfoDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportExpertInfo supportExpertInfo) {
        return expertInfoMapper.update(supportExpertInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return expertInfoMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return expertInfoMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return expertInfoMapper.getUniqueById(id);
    }

    @Override
    public List<PlanEarlywarnTypeDO> getAllEarlyWarningType() {
        return expertInfoMapper.getAllEarlyWarningType();
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteExpertPlantype(String id) {
        return expertInfoMapper.deleteExpertPlantype(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int insertExpertPlantype(String id, String expertInfoId, String earlwarnTypeId) {
        return expertInfoMapper.insertExpertPlantype(id, expertInfoId, earlwarnTypeId);
    }

	@Override
	public List<Map<String, Object>> getExpertInfoByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return expertInfoMapper.getExpertInfoByParams(params);
	}

    @Override
    public List<ExpertRes> getAllExpertByWarnTypeId(String warnTypeId) {
        return expertInfoMapper.getAllExpertByWarnTypeId(warnTypeId);
    }
}
